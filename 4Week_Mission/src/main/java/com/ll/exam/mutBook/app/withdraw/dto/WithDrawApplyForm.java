package com.ll.exam.mutBook.app.withdraw.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WithDrawApplyForm {
    @NotBlank
    private String bankName;
    @NotBlank
    private String bankAccountNo;
    @NotNull
    private Integer price;
}
