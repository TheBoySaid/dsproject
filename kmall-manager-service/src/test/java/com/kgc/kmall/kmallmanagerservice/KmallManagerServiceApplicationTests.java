package com.kgc.kmall.kmallmanagerservice;

import com.kgc.kmall.bean.PmsBaseCatalog1;
import com.kgc.kmall.kmallmanagerservice.service.CatalogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class KmallManagerServiceApplicationTests {

    @Autowired
    private CatalogServiceImpl catalogServiceImpl;

    @Test
    void contextLoads() {
        List<PmsBaseCatalog1> list = catalogServiceImpl.getCatalog1();
        for (PmsBaseCatalog1 item : list) {
            System.out.println(item);
        }
    }

}
