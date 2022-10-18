package com.ll.comibird.Week_Mission.app.product.entity;


import com.ll.comibird.Week_Mission.app.base.entity.BaseEntity;
import com.ll.comibird.Week_Mission.app.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId")
    private Member member;
}
