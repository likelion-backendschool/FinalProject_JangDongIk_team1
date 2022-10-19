package com.ll.comibird.Week_Mission.app.post.entity;


import com.ll.comibird.Week_Mission.app.base.entity.BaseEntity;
import com.ll.comibird.Week_Mission.app.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Post extends BaseEntity {
    @Column(nullable = false)
    private String subject;

    private String content;
    private String contentHtml;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorId")
    private Member author;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL})
    private List<PostHashTag> postHashTags = new ArrayList<>();
}
