package com.kgc.kmall.kmallmanagerservice.service;

import com.alibaba.fastjson.JSON;
import com.kgc.kmall.bean.PmsSkuAttrValue;
import com.kgc.kmall.bean.PmsSkuImage;
import com.kgc.kmall.bean.PmsSkuInfo;
import com.kgc.kmall.bean.PmsSkuSaleAttrValue;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsSkuAttrValueMapper;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsSkuImageMapper;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsSkuInfoMapper;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsSkuSaleAttrValueMapper;
import com.kgc.kmall.service.SkuService;
import com.kgc.kmall.util.RedisUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class SkuServiceImpl implements SkuService {
    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;
    @Autowired
    private PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Autowired
    private PmsSkuImageMapper pmsSkuImageMapper;
    @Autowired
    private PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfoMapper.insert(pmsSkuInfo);
        Long skuInfoId = pmsSkuInfo.getId();
        pmsSkuAttrValueMapper.insertBatch(skuInfoId, pmsSkuInfo.getSkuAttrValueList());
        pmsSkuImageMapper.insertBatch(skuInfoId, pmsSkuInfo.getSkuImageList());
        pmsSkuSaleAttrValueMapper.insertBatch(skuInfoId, pmsSkuInfo.getSkuSaleAttrValueList());
        return "success";
    }

    @Override
    public PmsSkuInfo selectBySkuId(Long id) {
        Jedis jedis = redisUtil.getJedis();
        String skuKey = "sku:" + id + ":info";
        String skuInfoJson = jedis.get(skuKey);
        if (skuInfoJson != null) {
            if (skuInfoJson.equals("empty")) {
                return null;
            }
            PmsSkuInfo pmsSkuInfo = JSON.parseObject(skuInfoJson, PmsSkuInfo.class);
            jedis.close();
            return pmsSkuInfo;
        } else {
            String skuLockKey = "sku:" + id + ":lock";
            String skuLockValue= UUID.randomUUID().toString();
            System.out.println(skuLockValue);
            String lock = jedis.set(skuLockKey, skuLockValue, "NX", "PX",60*1000);

            if (lock.equals("OK")) {
                PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectByPrimaryKey(id);
                if (pmsSkuInfo == null) {
                    jedis.setex(skuKey, 5 * 60 * 1000, "empty");
                    return null;
                }
                //保存到redis
                String skuInfoJsonStr = JSON.toJSONString(pmsSkuInfo);
                Random random = new Random();
                jedis.setex(skuKey, random.nextInt(10) * 60 * 1000 + 10000, skuInfoJsonStr);
                String skuLockValue2 = jedis.get(skuLockKey);
                if (skuLockValue2!=null&&skuLockValue2.equals(skuLockValue)){
                    String script ="if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                    jedis.eval(script, Collections.singletonList(skuLockKey),Collections.singletonList(skuLockValue));

                }

                jedis.close();
                return pmsSkuInfo;
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    return selectBySkuId(id);
                }
            }

        }

    }

    @Override
    public List<PmsSkuInfo> selectBySpuId(Long spuId) {
        return pmsSkuInfoMapper.selectBySpuId(spuId);
    }


}
