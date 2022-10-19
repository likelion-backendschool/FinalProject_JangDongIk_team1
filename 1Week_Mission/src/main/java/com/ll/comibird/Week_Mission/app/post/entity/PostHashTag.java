package com.ll.comibird.Week_Mission.app.post.entity;

import com.ll.comibird.Week_Mission.app.base.entity.BaseEntity;
import com.ll.comibird.Week_Mission.app.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class PostHashTag extends BaseEntity {
    @ManyToOne
    @ToString.Exclude
    private Post post;
    @ManyToOne
    @ToString.Exclude
    private PostKeyword postKeyword;
}