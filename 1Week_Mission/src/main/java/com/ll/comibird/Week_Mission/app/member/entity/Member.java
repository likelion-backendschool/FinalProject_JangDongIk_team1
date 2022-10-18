package com.ll.comibird.Week_Mission.app.member.entity;

import com.ll.comibird.Week_Mission.app.base.entity.BaseEntity;
import com.ll.comibird.Week_Mission.app.product.entity.Product;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // 저자명
    @Column(unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    // 권한 1 = 일반, 3 = 저자, 7 = 관리자
    private Integer authLevel;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Product> productList = new ArrayList<>();
}
