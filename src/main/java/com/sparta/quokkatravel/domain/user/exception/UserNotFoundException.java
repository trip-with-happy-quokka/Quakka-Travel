package com.sparta.quokkatravel.domain.user.exception;

import com.sparta.quokkatravel.domain.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}