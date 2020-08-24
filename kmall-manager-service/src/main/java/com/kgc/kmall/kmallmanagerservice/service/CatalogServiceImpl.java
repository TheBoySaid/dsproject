package com.kgc.kmall.kmallmanagerservice.service;

import com.kgc.kmall.bean.PmsBaseCatalog1;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsBaseCatalog1Mapper;
import com.kgc.kmall.service.CatalogService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;
    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        return pmsBaseCatalog1Mapper.selectByExample(null);
    }

}
