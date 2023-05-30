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
        // 判断是否存在重复抢购
        if (redisUtil.isSetMember(String.format("UOG:%d", user.getUserId()), seckillId))
            throw new SeckillException(ReturnNo.SECKILL_GOODS_USER_REPEAT);
        // 检查秒杀商品是否存在
        SeckillGoodsVo goods = (SeckillGoodsVo) redisUtil.getValueByKey(SeckillGoodsVo.RedisKey(seckillId));
        if (null == goods)
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NON);
        // 检查该秒杀活动是否正在进行
        Date now = new Date();
        if (!(now.after(goods.getBeginTime())&&now.before(goods.getEndTime())))
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_EXIST);
        // 检查秒杀库存是否充足
        Long stock = redisUtil.decr(SeckillGoodsVo.RedisSeckillStockKey(seckillId));
        if (stock < 0) {
            redisUtil.incr(SeckillGoodsVo.RedisSeckillStockKey(seckillId));
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_REST);
        }
        // 发送消息到消息队列中
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
