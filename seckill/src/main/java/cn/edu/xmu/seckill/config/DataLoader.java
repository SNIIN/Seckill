package cn.edu.xmu.seckill.config;

import cn.edu.xmu.core.controller.vo.SeckillGoodsVo;
import cn.edu.xmu.core.utils.RedisUtil;
import cn.edu.xmu.seckill.service.feign.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import java.util.List;
import java.util.Set;

//@Component 
public class DataLoader implements CommandLineRunner {

    private RedisUtil redisUtil;


    private GoodsService goodsService;
    @Autowired
    public DataLoader(GoodsService goodsService, RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
        this.goodsService = goodsService;
    }


    @Override     
    public void run(String... args) throws Exception {
        Set<String> keys = redisUtil.keys("SG_STOCK:*");
        if (keys != null && !keys.isEmpty()) redisUtil.deleteKeys(keys);
        keys = redisUtil.keys("SG:*");
        if (keys != null && !keys.isEmpty()) redisUtil.deleteKeys(keys);
        keys = redisUtil.keys("UOG:*");
        if (keys != null && !keys.isEmpty()) redisUtil.deleteKeys(keys);
        List<SeckillGoodsVo> lst = goodsService.getOnePageGoodsList();
        lst.forEach(i -> {
            redisUtil.addAsKeyValue(i.RedisSeckillStockKey(), i.getSeckillStock(), false);
            redisUtil.addAsKeyValue(i.RedisKey(), i, false);
        });
    }
}
