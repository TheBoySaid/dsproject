package com.kgc.kmall.kmallmanagerservice.service;

import com.kgc.kmall.bean.PmsSkuAttrValue;
import com.kgc.kmall.bean.PmsSkuImage;
import com.kgc.kmall.bean.PmsSkuInfo;
import com.kgc.kmall.bean.PmsSkuSaleAttrValue;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsSkuAttrValueMapper;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsSkuImageMapper;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsSkuInfoMapper;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsSkuSaleAttrValueMapper;
import com.kgc.kmall.service.SkuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Override
    public String saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        pmsSkuInfoMapper.insert(pmsSkuInfo);
        Long skuInfoId = pmsSkuInfo.getId();
        pmsSkuAttrValueMapper.insertBatch(skuInfoId, pmsSkuInfo.getSkuAttrValueList());
        pmsSkuImageMapper.insertBatch(skuInfoId, pmsSkuInfo.getSkuImageList());
        pmsSkuSaleAttrValueMapper.insertBatch(skuInfoId, pmsSkuInfo.getSkuSaleAttrValueList());
        return "success";
    }
}
