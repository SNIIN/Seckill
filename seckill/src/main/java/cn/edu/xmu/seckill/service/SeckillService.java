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
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class SeckillService implements InitializingBean {

    private final GoodsService goodsService;
    private final RedisUtil redisUtil;
    private final RabbitSender rabbitSender;
    private Map<Long, Boolean> emptySeckillStock;
    @Autowired
    public SeckillService(RabbitSender rabbitSender, RedisUtil redisUtil, GoodsService goodsService) {
        this.goodsService = goodsService;
        this.rabbitSender = rabbitSender;
        this.redisUtil = redisUtil;
    }


    public void doSeckill(UserVo user, Long seckillId) {
        // 重复抢购
        if (redisUtil.isSetMember(String.format("UOG:%d", user.getUserId()), seckillId))
            throw new SeckillException(ReturnNo.SECKILL_GOODS_USER_REPEAT);
        SeckillGoodsVo goods = (SeckillGoodsVo) redisUtil.getValueByKey(SeckillGoodsVo.RedisKey(seckillId));
        Date now = new Date();
        if (!(now.after(goods.getBeginTime())&&now.before(goods.getEndTime())))
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_EXIST);
        if (emptySeckillStock.getOrDefault(seckillId, false))
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_REST);
        Long stock = redisUtil.decr(SeckillGoodsVo.RedisSeckillStockKey(seckillId));
        if (stock < 0) {
            redisUtil.incr(SeckillGoodsVo.RedisSeckillStockKey(seckillId));
            emptySeckillStock.put(seckillId, true);
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_REST);
        }
        rabbitSender.send(user, seckillId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<String> keys = redisUtil.keys("SG_STOCK:*");
        if (keys != null && !keys.isEmpty()) redisUtil.deleteKeys(keys);
        keys = redisUtil.keys("SG:*");
        if (keys != null && !keys.isEmpty()) redisUtil.deleteKeys(keys);
        keys = redisUtil.keys("UOG:*");
        if (keys != null && !keys.isEmpty()) redisUtil.deleteKeys(keys);
        emptySeckillStock = new HashMap<>();
        List<SeckillGoodsVo> lst = goodsService.getOnePageGoodsList();
        lst.forEach(i -> {
            redisUtil.addAsKeyValue(i.RedisSeckillStockKey(), i.getSeckillStock(), false);
            redisUtil.addAsKeyValue(i.RedisKey(), i, false);
            emptySeckillStock.put(i.getSeckillId(), false);
        });
    }
}
