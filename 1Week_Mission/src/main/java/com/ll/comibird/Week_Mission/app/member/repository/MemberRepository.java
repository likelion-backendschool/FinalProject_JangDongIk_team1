package com.ll.comibird.Week_Mission.app.member.repository;


import com.ll.comibird.Week_Mission.app.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Object> findByUsername(String username);

    Member findByEmail(String email);
}
