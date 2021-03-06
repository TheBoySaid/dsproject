package com.kgc.kmall.kmallmanagerservice.service;

import com.kgc.kmall.bean.*;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsBaseCatalog1Mapper;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsBaseCatalog2Mapper;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsBaseCatalog3Mapper;
import com.kgc.kmall.service.CatalogService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;
    @Autowired
    private PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;
    @Autowired
    private PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;
    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        return pmsBaseCatalog1Mapper.selectByExample(null);
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(Integer catalog1Id) {
        PmsBaseCatalog2Example example = new PmsBaseCatalog2Example();
        PmsBaseCatalog2Example.Criteria criteria = example.createCriteria();
        criteria.andCatalog1IdEqualTo(catalog1Id);
        return pmsBaseCatalog2Mapper.selectByExample(example);
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(Long catalog2Id) {
        PmsBaseCatalog3Example example = new PmsBaseCatalog3Example();
        PmsBaseCatalog3Example.Criteria criteria = example.createCriteria();
        criteria.andCatalog2IdEqualTo(catalog2Id);
        return pmsBaseCatalog3Mapper.selectByExample(example);
    }

}
