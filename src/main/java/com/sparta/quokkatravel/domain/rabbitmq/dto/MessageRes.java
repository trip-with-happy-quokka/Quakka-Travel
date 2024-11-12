package com.sparta.quokkatravel.domain.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MessageRes {

    private final String title;
    private final String content;
}
