package cn.edu.xmu.seckill.service;

import cn.edu.xmu.core.exception.SeckillException;
import cn.edu.xmu.core.mapper.entity.User;
import cn.edu.xmu.core.utils.RedisUtil;
import cn.edu.xmu.core.utils.ReturnNo;
import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.seckill.rabbitmq.RabbitSender;
import cn.edu.xmu.seckill.service.feign.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public Boolean doSeckill(User user, Long seckillId) {
        // 重复抢购
        if (redisUtil.isSetMember(user.getUserId().toString(), seckillId))
            throw new SeckillException(ReturnNo.SECKILL_GOODS_USER_REPEAT);
        log.info("无重复抢购");
        SeckillGoodsVo goods = (SeckillGoodsVo) redisUtil.getValueByKey(SeckillGoodsVo.RedisKey(seckillId));
        log.info("goods: {}", goods);
        Date now = new Date();
        if (!(now.after(goods.getBeginTime())&&now.before(goods.getEndTime())))
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_EXIST);
        log.info("日期合法");
        if (emptySeckillStock.get(seckillId))
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_REST);
        Long stock = redisTemplate.opsForValue().decrement(SeckillGoodsVo.RedisSeckillStockKey(seckillId));
        if (stock < 0) {
            redisTemplate.opsForValue().increment(SeckillGoodsVo.RedisSeckillStockKey(seckillId));
            emptySeckillStock.put(seckillId, true);
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_REST);
        }
        log.info("库存充足");
        rabbitSender.send(user, seckillId);
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        emptySeckillStock = new HashMap<>();
        List<SeckillGoodsVo> lst = goodsService.getOnePageGoodsList(1);
        lst.forEach(i -> {
            redisUtil.addAsKeyValue(i.RedisSeckillStockKey(), i.getSeckillStock(), false);
            redisUtil.addAsKeyValue(i.RedisKey(), i, false);
            emptySeckillStock.put(i.getSeckillId(), false);
        });
    }
}
