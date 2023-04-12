package cn.edu.xmu.seckill.service;

import cn.edu.xmu.core.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.core.controller.vo.UserVo;
import cn.edu.xmu.core.exception.SeckillException;
import cn.edu.xmu.core.utils.RedisUtil;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.seckill.rabbitmq.RabbitSender;
import cn.edu.xmu.seckill.service.feign.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class SeckillService implements InitializingBean {

    private final GoodsService goodsService;
    private final RedisUtil redisUtil;
    private final RedisTemplate redisTemplate;
    private final RabbitSender rabbitSender;
    private Map<Long, Boolean> emptySeckillStock;
    @Autowired
    public SeckillService(RabbitSender rabbitSender, RedisUtil redisUtil, RedisTemplate redisTemplate, GoodsService goodsService) {
        this.goodsService = goodsService;
        this.rabbitSender = rabbitSender;
        this.redisUtil = redisUtil;
        this.redisTemplate = redisTemplate;
    }


    public Boolean doSeckill(UserVo user, Long seckillId) {
        // 重复抢购
        if (redisUtil.isSetMember(String.format("UOG:%d", user.getUserId()), seckillId))
            throw new SeckillException(ReturnNo.SECKILL_GOODS_USER_REPEAT);
        SeckillGoodsVo goods = (SeckillGoodsVo) redisUtil.getValueByKey(SeckillGoodsVo.RedisKey(seckillId));
        Date now = new Date();
        if (!(now.after(goods.getBeginTime())&&now.before(goods.getEndTime())))
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_EXIST);
        if (emptySeckillStock.get(seckillId))
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_REST);
        Long stock = redisTemplate.opsForValue().decrement(SeckillGoodsVo.RedisSeckillStockKey(seckillId));
        if (stock < 0) {
            redisTemplate.opsForValue().increment(SeckillGoodsVo.RedisSeckillStockKey(seckillId));
            emptySeckillStock.put(seckillId, true);
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_REST);
        }
        rabbitSender.send(user, seckillId);
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<String> keys = redisTemplate.keys("SG_STOCK:*");
        if (keys != null && !keys.isEmpty()) redisTemplate.delete(keys);
        keys = redisTemplate.keys("SG:*");
        if (keys != null && !keys.isEmpty()) redisTemplate.delete(keys);
        keys = redisTemplate.keys("UOG:*");
        if (keys != null && !keys.isEmpty()) redisTemplate.delete(keys);
        emptySeckillStock = new HashMap<>();
        List<SeckillGoodsVo> lst = goodsService.getOnePageGoodsList(1);
        lst.forEach(i -> {
            redisUtil.addAsKeyValue(i.RedisSeckillStockKey(), i.getSeckillStock(), false);
            redisUtil.addAsKeyValue(i.RedisKey(), i, false);
            emptySeckillStock.put(i.getSeckillId(), false);
        });
    }
}
