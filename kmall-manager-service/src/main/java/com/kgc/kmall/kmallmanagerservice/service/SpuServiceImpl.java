package com.kgc.kmall.kmallmanagerservice.service;

import com.kgc.kmall.bean.PmsBaseSaleAttr;
import com.kgc.kmall.bean.PmsProductInfo;
import com.kgc.kmall.bean.PmsProductInfoExample;
import com.kgc.kmall.bean.PmsProductSaleAttr;
import com.kgc.kmall.kmallmanagerservice.mapper.*;
import com.kgc.kmall.service.SpuService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {
    @Autowired
    private PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired
    private PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    @Autowired
    private PmsProductImageMapper pmsProductImageMapper;
    @Autowired
    private PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    private PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    @Override
    public List<PmsProductInfo> spuList(Long catalog3Id) {
        PmsProductInfoExample example=new PmsProductInfoExample();
        PmsProductInfoExample.Criteria criteria = example.createCriteria();
        criteria.andCatalog3IdEqualTo(catalog3Id);
        List<PmsProductInfo> infoList = pmsProductInfoMapper.selectByExample(example);
        return infoList;
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectByExample(null);
    }

    @Override
    public int saveSpuInfo(PmsProductInfo pmsProductInfo) {
        int i = 0;
        i = pmsProductInfoMapper.insert(pmsProductInfo);
        i = pmsProductImageMapper.insertBatch(pmsProductInfo.getId(), pmsProductInfo.getSpuImageList());
        i = pmsProductSaleAttrMapper.insertBatch(pmsProductInfo.getId(), pmsProductInfo.getSpuSaleAttrList());
        for (int j = 0; j < pmsProductInfo.getSpuSaleAttrList().size(); j++) {
            i = pmsProductSaleAttrValueMapper.insertBatch(pmsProductInfo.getId(),
                    pmsProductInfo.getSpuSaleAttrList().get(j).getSpuSaleAttrValueList());
        }
        return i;
    }
}
