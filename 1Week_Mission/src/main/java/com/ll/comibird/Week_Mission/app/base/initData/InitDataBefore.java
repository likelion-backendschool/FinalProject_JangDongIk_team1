package com.ll.comibird.Week_Mission.app.base.initData;


import com.ll.comibird.Week_Mission.app.member.entity.Member;
import com.ll.comibird.Week_Mission.app.member.service.MemberService;

public interface InitDataBefore {
    default void before(MemberService memberService) {
        Member member1 = memberService.join("user1", "1234","user1@test.com");
        Member member2 = memberService.join("user2", "1234","user2@test.com", "작가1");
    }
}