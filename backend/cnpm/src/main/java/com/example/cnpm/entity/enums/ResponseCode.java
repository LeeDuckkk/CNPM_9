//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.cnpm.entity.enums;

public enum ResponseCode {
    SUCCESS("SUCCESS"),
    ERROR("ERROR"),
    NOT_FOUND("NOT_FOUND"),
    INVALID_TOKEN("INVALID_TOKEN"),
    TOKEN_EXPIRED("TOKEN_EXPIRED"),
    USER_IS_ENABLE("USER_IS_ENABLE"),
    OLD_PASSWORD_NOT_MATCH("OLD_PASSWORD_NOT_MATCH"),

    OAUTH_NOT_SUPPORT_PROVIDER("OAUTH_NOT_SUPPORT_PROVIDER"),

    USER_IS_DISABLE("USER_IS_DISABLE"),

    USER_IS_DELETE("USER_IS_DELETE");

    private String code;

    private ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
