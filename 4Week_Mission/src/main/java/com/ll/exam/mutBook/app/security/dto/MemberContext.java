package com.ll.exam.mutBook.app.security.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.ll.exam.mutBook.app.member.entity.Member;
import com.ll.exam.mutBook.app.member.entity.emum.AuthLevel;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@JsonIncludeProperties({"id", "createDate", "modifyDate", "username", "email", "nickname", "authorities"})
public class MemberContext extends User {
    private final Long id;
    private final LocalDateTime createDate;
    private final LocalDateTime modifyDate;
    private final String username;
    private final String email;
    private final String nickname;
    private final AuthLevel authLevel;

    public MemberContext(Member member) {
        super(member.getUsername(), "", member.getAuthorities());
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.authLevel = member.getAuthLevel();
    }

    public MemberContext(Member member, List<GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.authLevel = member.getAuthLevel();
    }

    public Member getMember() {
        return Member
                .builder()
                .id(id)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .username(username)
                .email(email)
                .nickname(nickname)
                .authLevel(authLevel)
                .build();
    }

    public String getName() {
        return getUsername();
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authorityName));
    }
}