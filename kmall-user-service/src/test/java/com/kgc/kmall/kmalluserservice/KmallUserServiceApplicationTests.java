package com.kgc.kmall.kmalluserservice;

import com.kgc.kmall.bean.Member;
import com.kgc.kmall.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class KmallUserServiceApplicationTests {

    @Autowired
    MemberService memberService;

    @Test
    void contextLoads() {
        List<Member> members = memberService.selectAllMember();
        for (Member member : members) {
            System.out.println(member.toString());
        }
    }


}
