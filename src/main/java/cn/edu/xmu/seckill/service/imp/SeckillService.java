package cn.edu.xmu.seckill.service.imp;

import cn.edu.xmu.seckill.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.seckill.controller.vo.SeckillOrderVo;
import cn.edu.xmu.seckill.entity.Order;
import cn.edu.xmu.seckill.entity.RabbitMqMessage;
import cn.edu.xmu.seckill.entity.User;
import cn.edu.xmu.seckill.exception.SeckillException;
import cn.edu.xmu.seckill.mapper.OrderMapper;
import cn.edu.xmu.seckill.mapper.SeckillGoodsMapper;
import cn.edu.xmu.seckill.rabbitMq.MQSender;
import cn.edu.xmu.seckill.service.IGoodsService;
import cn.edu.xmu.seckill.service.ISeckillService;
import cn.edu.xmu.seckill.utils.JsonUtil;
import cn.edu.xmu.seckill.utils.ReturnNo;
import cn.edu.xmu.seckill.utils.ReturnObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SeckillService implements ISeckillService, InitializingBean {

    private final IGoodsService goodsService;
    private final OrderMapper orderMapper;
    private final SeckillGoodsMapper seckillGoodsMapper;
    private final RedisTemplate redisTemplate;
    private final MQSender mqSender;
    private Map<Long,Boolean> EmptyStockMap = new HashMap<>();
    private Map<Long,Long> SeckillId2GoodsId = new HashMap<>();
    @Autowired
    public SeckillService(IGoodsService goodsService, OrderMapper orderMapper, SeckillGoodsMapper seckillGoodsMapper, RedisTemplate redisTemplate, MQSender mqSender) {
        this.goodsService = goodsService;
        this.orderMapper = orderMapper;
        this.seckillGoodsMapper = seckillGoodsMapper;
        this.redisTemplate = redisTemplate;
        this.mqSender = mqSender;
    }

    @Override
    @Transactional
    public ReturnObject doSeckill(User user, Long seckillId) {
        String status = (String)redisTemplate.opsForValue().get("orderTemp:" + user.getUserId() + ":" + SeckillId2GoodsId.get(seckillId));
        if (null!=status){//判断重复抢购
            throw new SeckillException(ReturnNo.SECKILL_GOODS_USER_REPEAT);
        }//TODO:需要写lua脚本
        //内存标记降低redis内存压力
        if (EmptyStockMap.get(seckillId))  throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_REST);
        //数量判断
        Long goodStock = redisTemplate.opsForValue().decrement("seckillStock:" + seckillId);
        if (goodStock<0){
            redisTemplate.opsForValue().increment("seckillStock:" + seckillId);
            EmptyStockMap.put(seckillId, true);
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_REST);
        }
        SeckillGoodsVo goods = (SeckillGoodsVo) redisTemplate.opsForValue().get("goods:"+SeckillId2GoodsId.get(seckillId));
        log.info("商品信息"+goods);
        Date now = new Date();
        if (!(now.after(goods.getBeginTime()) && now.before(goods.getEndTime()))) {
            throw new SeckillException(ReturnNo.SECKILL_GOODS_NOT_EXIST);
        }
        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage(goods,user);
        redisTemplate.opsForValue().set("orderTemp:" + user.getUserId() + ":" + SeckillId2GoodsId.get(seckillId), "已下单");
        mqSender.sendSeckillMsg(JsonUtil.object2JsonStr(rabbitMqMessage));
        log.info("测试结束");
        return new ReturnObject(ReturnNo.SECKILL_GOODS_WAITING);
    }

    @Override
    public SeckillOrderVo getSeckillOrder(Long orderId) {
        SeckillOrderVo order = orderMapper.selectSeckillOrderByOrderId(orderId);
        if (null == order) {
            throw new SeckillException(ReturnNo.SECKILL_ORDER_NON);
        }
        return order;
    }

    @Override
    public Long getOrderStatus(User user, Long seckillId) {
        Order order = orderMapper.selectByGoodsIdAndUserId(SeckillId2GoodsId.get(seckillId), user.getUserId());
        if (null != order) {
            return order.getOrderId();
        }else if (EmptyStockMap.get(seckillId)){
            return -1L;
        }else return 0L;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("SeckillService执行");
        List<SeckillGoodsVo> goodsList = goodsService.getOnePageGoodsList(1);
        if (CollectionUtils.isEmpty(goodsList)){
            return;
        }
        goodsList.forEach(vo->{
            redisTemplate.opsForValue().set("seckillStock:"+vo.getSeckillId(),vo.getSeckillStock());//添加数量
            redisTemplate.opsForValue().set("goods:"+vo.getGoodsId(),vo);//添加商品
            EmptyStockMap.put(vo.getSeckillId(), false);
            SeckillId2GoodsId.put(vo.getSeckillId(), vo.getGoodsId());
        });
    }

}
