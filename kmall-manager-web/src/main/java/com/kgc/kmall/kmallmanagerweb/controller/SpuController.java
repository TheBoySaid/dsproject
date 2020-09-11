package com.kgc.kmall.kmallmanagerweb.controller;

import com.kgc.kmall.bean.PmsBaseSaleAttr;
import com.kgc.kmall.bean.PmsProductImage;
import com.kgc.kmall.bean.PmsProductInfo;
import com.kgc.kmall.bean.PmsProductSaleAttr;
import com.kgc.kmall.service.SpuService;
import org.apache.commons.io.FilenameUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class SpuController {
    @Reference
    SpuService spuService;

    @Value("${fileServer.url}")
    String fileUrl;

    @RequestMapping("/spuList")
    public List<PmsProductInfo> spuList(Long catalog3Id) {
        List<PmsProductInfo> infoList = spuService.spuList(catalog3Id);
        return infoList;
    }

    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException,MyException {
        //文件上传
        //返回文件上传后的路径
        String configFile = this.getClass().getResource("/tracker.conf").getFile();
        ClientGlobal.init(configFile);
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getTrackerServer();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        //String orginalFilename="C:\\Users\\alienware\\Pictures\\课工场桌面壁纸.jpg";
        //获取文件名，通过文件名获取文件扩展名
        String fileName = file.getOriginalFilename();
        String extName = FilenameUtils.getExtension(fileName);

        String path = fileUrl;
        String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);
        for (int i = 0; i < upload_file.length; i++) {
            String s = upload_file[i];
            path += "/" + s;
        }
        return path;
    }

    @RequestMapping("/baseSaleAttrList")
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> saleAttrList = spuService.baseSaleAttrList();
        return saleAttrList;
    }

    @RequestMapping("/saveSpuInfo")
    public int saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        return spuService.saveSpuInfo(pmsProductInfo);
    }

    @RequestMapping("/spuSaleAttrList")
    public List<PmsProductSaleAttr> spuSaleAttrList(Long spuId) {
        return spuService.spuSaleAttrList(spuId);
    }

    @RequestMapping("/spuImageList")
    public List<PmsProductImage> spuImageList(Long spuId) {
        return spuService.spuImageList(spuId);
    }
}
