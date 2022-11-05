package com.ll.comibird.Week_Mission.app.member.form;

import lombok.Data;

@Data
public class ModifyPasswordForm {

    private String oldPassword;

    private String password;

    private String passwordConfirm;
}
