package com.lks.core.enums;

/**
 * Created by lokkur on 8/12/2015.
 */
public enum ExceptionCode {

    INVALID_USER("Invalid user name or password"),
    USER_EXISTS("User already exists"),
    BRANCH_EXISTS("Branch already exists"),
    INVALID_BRANCH("Invalid branch name or code"),
    SYSTEM_ERROR("System encountered an unfortunate exception");

    private String message;

    ExceptionCode(String message) {
        this.message = message;
    }
}
