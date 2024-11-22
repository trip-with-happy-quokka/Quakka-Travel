package com.sparta.quokkatravel.domain.email.service;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.reservation.entity.Reservation;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationEmailService {

    private final EmailSendService emailSendService;

    // 예약 생성 시 호스트와 게스트에게 이메일 전송
    public void sendReservationCreationEmail(User host, String guestEmail, User user, Accommodation accommodation, Room room, Reservation reservation) {
        // 호스트에게 전송될 제목과 내용
        String hostSubject = "새 예약 알림: " + user.getNickname() + "님이 방을 예약했습니다.";
        String hostMessage = generateMessage("새로운 예약 정보는 다음과 같습니다.", user, accommodation, room, reservation);

        // 게스트에게 전송될 제목과 내용
        String guestSubject = "예약이 성공적으로 완료되었습니다. 결제를 진행하여 예약 확정까지 완료하세요!";
        String guestMessage = generateMessage("고객님의 숙박 예약 정보는 다음과 같습니다.", user, accommodation, room, reservation);

        // 호스트에게 전송
        emailSendService.sendSimpleMessageAsync(host.getEmail(), hostSubject, hostMessage);

        // 게스트에게 전송
        emailSendService.sendSimpleMessageAsync(guestEmail, guestSubject, guestMessage);
    }

    // 예약 수정 시 호스트와 게스트에게 이메일 전송
    public void sendReservationUpdateEmail(User host, String guestEmail, User user, Accommodation accommodation, Room room, Reservation reservation) {
        // 호스트에게 전송될 제목과 내용
        String hostSubject = "변경된 예약 알림: " + user.getNickname() + "님이 예약 정보를 수정했습니다.";
        String hostMessage = generateMessage("수정된 예약 정보는 다음과 같습니다.", user, accommodation, room, reservation);

        // 게스트에게 전송될 제목과 내용
        String guestSubject = "예약 정보 수정이 성공적으로 완료되었습니다.";
        String guestMessage = generateMessage("수정된 예약 정보는 다음과 같습니다.", user, accommodation, room, reservation);

        // 호스트에게 전송
        emailSendService.sendSimpleMessageAsync(host.getEmail(), hostSubject, hostMessage);

        // 게스트에게 전송
        emailSendService.sendSimpleMessageAsync(guestEmail, guestSubject, guestMessage);
    }

    // 예약 취소 시 호스트와 게스트에게 이메일 전송
    public void sendReservationCancellationEmail(User host, String guestEmail, User user, Accommodation accommodation, Room room, Reservation reservation) {
        // 호스트에게 전송될 제목과 내용
        String hostSubject = "취소된 예약 알림: " + user.getNickname() + "님이 예약을 취소했습니다.";
        String hostMessage = generateMessage("취소된 예약 정보는 다음과 같습니다.", user, accommodation, room, reservation);

        // 게스트에게 전송될 제목과 내용
        String guestSubject = "예약 취소가 성공적으로 완료되었습니다.";
        String guestMessage = generateMessage("취소된 예약 정보는 다음과 같습니다.", user, accommodation, room, reservation);

        // 호스트에게 전송
        emailSendService.sendSimpleMessageAsync(host.getEmail(), hostSubject, hostMessage);

        // 게스트에게 전송
        emailSendService.sendSimpleMessageAsync(guestEmail, guestSubject, guestMessage);
    }

    // 공통 메시지 생성 메서드
    private String generateMessage(String actionMessage, User user, Accommodation accommodation, Room room, Reservation reservation) {
        return "\n" + actionMessage + "\n"
                + "\n[예약자 닉네임] : " + user.getNickname()
                + "\n[예약자 이메일주소] : " + user.getEmail()
                + "\n[숙소 이름] : " + accommodation.getName()
                + "\n[숙소 소개] : " + accommodation.getDescription()
                + "\n[숙소 주소] : " + accommodation.getAddress()
                + "\n[방 이름] : " + room.getName()
                + "\n[방 설명] : " + room.getDescription()
                + "\n[1박 요금] : " + room.getPricePerNight()
                + "\n[1인당 추가 요금] : " + room.getPricePerOverCapacity()
                + "\n[방문 인원수] : " + reservation.getNumberOfGuests()
                + "\n[입실 날짜] : " + reservation.getStartDate()
                + "\n[퇴실 날짜] : " + reservation.getEndDate()
                + "\n\n쿼카트래블을 이용해주셔서 감사합니다. :)";
    }
}

