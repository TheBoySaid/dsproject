package com.kgc.kmall.kmallmanagerservice;

import com.kgc.kmall.bean.PmsBaseAttrInfo;
import com.kgc.kmall.bean.PmsBaseCatalog1;
import com.kgc.kmall.kmallmanagerservice.service.AttrServiceImpl;
import com.kgc.kmall.kmallmanagerservice.service.CatalogServiceImpl;
import com.kgc.kmall.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;

@SpringBootTest
class KmallManagerServiceApplicationTests {

    @Autowired
    private CatalogServiceImpl catalogServiceImpl;
    @Autowired
    private AttrServiceImpl attrService;

    @Test
    void contextLoads() {
        List<PmsBaseCatalog1> list = catalogServiceImpl.getCatalog1();
        for (PmsBaseCatalog1 item : list) {
            System.out.println(item);
        }
    }

    @Test
    void contextLoads2() {
        List<PmsBaseAttrInfo> list = attrService.queryAttrInfo(Long.parseLong("61"));
        for (PmsBaseAttrInfo item : list) {
            System.out.println(item);
        }
    }

    @Autowired
    RedisUtil redisUtil;
    @Test
    void contextLoadsRedis() {
        try {
            Jedis jedis = redisUtil.getJedis();
            String name = jedis.get("name");
            System.out.println(name);
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }
    }


}
