package com.kgc.kmall.kmallmanagerservice.service;

import com.kgc.kmall.bean.PmsBaseAttrInfo;
import com.kgc.kmall.bean.PmsBaseAttrInfoExample;
import com.kgc.kmall.bean.PmsBaseAttrValue;
import com.kgc.kmall.bean.PmsBaseAttrValueExample;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsBaseAttrInfoMapper;
import com.kgc.kmall.kmallmanagerservice.mapper.PmsBaseAttrValueMapper;
import com.kgc.kmall.service.AttrService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class AttrServiceImpl implements AttrService {
    @Autowired
    private PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;

    @Autowired
    private PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Override
    public List<PmsBaseAttrInfo> queryAttrInfo(Long catalog3) {
        PmsBaseAttrInfoExample example = new PmsBaseAttrInfoExample();
        PmsBaseAttrInfoExample.Criteria criteria = example.createCriteria();
        criteria.andCatalog3IdEqualTo(catalog3);
        List<PmsBaseAttrInfo> list = pmsBaseAttrInfoMapper.selectByExample(example);
        return list;
    }

    @Override
    public Integer save(PmsBaseAttrInfo pmsBaseAttrInfo) {
        Integer i = 0;
        if (pmsBaseAttrInfo.getId() == null) {
            i = pmsBaseAttrInfoMapper.insert(pmsBaseAttrInfo);
        }else{
            i = pmsBaseAttrInfoMapper.updateByPrimaryKey(pmsBaseAttrInfo);
            //删除原来属性
            PmsBaseAttrValueExample example = new PmsBaseAttrValueExample();
            PmsBaseAttrValueExample.Criteria criteria = example.createCriteria();
            criteria.andAttrIdEqualTo(pmsBaseAttrInfo.getId());
            i = pmsBaseAttrValueMapper.deleteByExample(example);
        }
        if (pmsBaseAttrInfo.getAttrValueList().size() > 0) {
            i = pmsBaseAttrValueMapper.insertBatch(pmsBaseAttrInfo.getId(), pmsBaseAttrInfo.getAttrValueList());
        }
        return i;
    }

    @Override
    public List<PmsBaseAttrValue> queryByAttrId(Long attrId) {
        PmsBaseAttrValueExample example = new PmsBaseAttrValueExample();
        PmsBaseAttrValueExample.Criteria criteria = example.createCriteria();
        criteria.andAttrIdEqualTo(attrId);
        return pmsBaseAttrValueMapper.selectByExample(example);
    }

}
