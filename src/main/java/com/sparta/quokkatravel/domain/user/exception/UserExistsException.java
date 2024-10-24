package com.sparta.quokkatravel.domain.user.exception;

import com.sparta.quokkatravel.domain.common.exception.BadRequestException;

public class UserExistsException extends BadRequestException {
    public UserExistsException(String message) {
        super(message);
    }
}