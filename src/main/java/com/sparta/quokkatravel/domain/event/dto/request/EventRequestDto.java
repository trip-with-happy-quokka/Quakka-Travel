package com.sparta.quokkatravel.domain.event.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EventRequestDto {

    @NotBlank(message = "행사 명을 입력해주세요.")
    private String eventName;

    @NotBlank(message = "행사 내용을 입력해주세요.")
    private String eventContent;
}
