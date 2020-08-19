package com.kgc.kmall.kmalluserservice.service;

import com.kgc.kmall.bean.Member;
import com.kgc.kmall.kmalluserservice.mapper.MemberMapper;
import com.kgc.kmall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberMapper memberMapper;

    @Override
    public List<Member> selectAllMember() {
        List<Member> members = memberMapper.selectByExample(null);
        return members;
    }
}

