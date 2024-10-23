package com.sparta.quokkatravel.domain.room.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/guest")
@PreAuthorize("hasRole('ROLE_GUEST') or hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Room", description = "Guest 객실 관련 컨트롤러")
public class GuestRoomController {

    //

}
