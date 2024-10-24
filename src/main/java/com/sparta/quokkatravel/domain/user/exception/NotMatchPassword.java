package com.sparta.quokkatravel.domain.user.exception;

import com.sparta.quokkatravel.domain.common.exception.BadRequestException;

public class NotMatchPassword extends BadRequestException {
    public NotMatchPassword(String message) {
        super(message);
    }
}
