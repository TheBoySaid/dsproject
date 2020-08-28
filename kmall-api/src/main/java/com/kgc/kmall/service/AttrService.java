package com.kgc.kmall.service;

import com.kgc.kmall.bean.PmsBaseAttrInfo;
import com.kgc.kmall.bean.PmsBaseAttrValue;

import java.util.List;

public interface AttrService {
    List<PmsBaseAttrInfo> queryAttrInfo(Long catalog3);

    Integer save(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> queryByAttrId(Long attrId);
}
