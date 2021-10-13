package com.jinho.homepage.util;

public enum ValidationConstant {
    /**
     *  Email
     */
    TOKEN_NOT_FOUND("Token Not Found")


    /**
     * Login
     */
    , EMAIL_AUTH_REQUIRED("이메일 인증이 필요합니다.")
    ;

    private final String ErrorMessage;

    ValidationConstant(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
