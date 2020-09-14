package com.kgc.kmall.kmallmanagerservice.service;

import com.kgc.kmall.bean.*;
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
        if (pmsProductInfo.getSpuImageList() != null)
            i = pmsProductImageMapper.insertBatch(pmsProductInfo.getId(), pmsProductInfo.getSpuImageList());
        if (pmsProductInfo.getSpuSaleAttrList() != null) {
            i = pmsProductSaleAttrMapper.insertBatch(pmsProductInfo.getId(), pmsProductInfo.getSpuSaleAttrList());
            for (int j = 0; j < pmsProductInfo.getSpuSaleAttrList().size(); j++) {
                if (pmsProductInfo.getSpuSaleAttrList().get(j).getSpuSaleAttrValueList() != null)
                i = pmsProductSaleAttrValueMapper.insertBatch(pmsProductInfo.getId(),
                        pmsProductInfo.getSpuSaleAttrList().get(j).getSpuSaleAttrValueList());
            }
        }
        return i;
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(Long spuId) {
        PmsProductSaleAttrExample example = new PmsProductSaleAttrExample();
        PmsProductSaleAttrExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(spuId);
        List<PmsProductSaleAttr> list = pmsProductSaleAttrMapper.selectByExample(example);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                PmsProductSaleAttrValueExample example1 = new PmsProductSaleAttrValueExample();
                PmsProductSaleAttrValueExample.Criteria criteria1 = example1.createCriteria();
                criteria1.andProductIdEqualTo(spuId);
                criteria1.andSaleAttrIdEqualTo(list.get(i).getSaleAttrId());
                list.get(i).setSpuSaleAttrValueList(pmsProductSaleAttrValueMapper.selectByExample(example1));
            }
        }
        return list;
    }

    @Override
    public List<PmsProductImage> spuImageList(Long spuId) {
        PmsProductImageExample example = new PmsProductImageExample();
        PmsProductImageExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(spuId);
        return pmsProductImageMapper.selectByExample(example);
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrListIsCheck(Long spuId, Long skuId) {
        List<PmsProductSaleAttr> pmsProductSaleAttrList = pmsProductSaleAttrMapper.spuSaleAttrListIsCheck(spuId, skuId);
        return pmsProductSaleAttrList;
    }


}
