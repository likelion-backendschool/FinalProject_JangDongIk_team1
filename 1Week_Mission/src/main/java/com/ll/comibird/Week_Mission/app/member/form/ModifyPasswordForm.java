package com.ll.comibird.Week_Mission.app.member.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ModifyPasswordForm {
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordConfirm;
}
