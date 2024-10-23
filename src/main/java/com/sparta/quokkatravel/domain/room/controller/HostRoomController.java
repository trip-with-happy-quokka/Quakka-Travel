package com.sparta.quokkatravel.domain.room.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/host")
@PreAuthorize("hasRole('ROLE_HOST')")
@RequiredArgsConstructor
@Tag(name = "Room", description = "Host 객실 관련 컨트롤러")
public class HostRoomController {
}
