package com.ll.comibird.Week_Mission.app.member.repository;


import com.ll.comibird.Week_Mission.app.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
