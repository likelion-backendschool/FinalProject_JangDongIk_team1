package com.ll.exam.final__2022_10_08.app.order.entiry;

import com.ll.exam.final__2022_10_08.app.base.entity.BaseEntity;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class Order extends BaseEntity {
    @CreatedDate
    private LocalDateTime payDate; // 결제날짜
    private boolean readyStatus; // 주문완료여부
    private boolean isPaid; // 결제여부
    private boolean isCanceled; // 취소여부
    private boolean isRefunded; // 환불여부
    @Column(columnDefinition = "LONGTEXT")
    private String name;

    @ManyToOne(fetch = LAZY)
    private Member member;
}
