package com.sparta.quokkatravel;

import com.sparta.quokkatravel.domain.accommodation.dto.AccommodationRequestDto;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.repository.AccommodationRepository;
import com.sparta.quokkatravel.domain.room.dto.RoomRequestDto;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.room.repository.RoomRepository;
import com.sparta.quokkatravel.domain.user.dto.UserSignupRequestDto;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class BulkDataGenerationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeAll
    static void setUp(@Autowired UserRepository userRepository, @Autowired AccommodationRepository accommodationRepository, @Autowired RoomRepository roomRepository) {

        for (int i=0 ; i < 500 ; i++) {

            UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto("test"+i+"@example.com", "test"+i, "tester"+i, UserRole.HOST);
            User user = new User(userSignupRequestDto.getEmail(), userSignupRequestDto.getPassword(), userSignupRequestDto.getNickname(), userSignupRequestDto.getUserRole());
            userRepository.save(user);

            AccommodationRequestDto accommodationRequestDto = new AccommodationRequestDto("test name:"+i, "test description: "+i, "test address:"+i);
            Accommodation accommodation = new Accommodation(accommodationRequestDto.getName(), accommodationRequestDto.getDescription(), accommodationRequestDto.getAddress(), user);
            accommodationRepository.save(accommodation);

            RoomRequestDto roomRequestDto = new RoomRequestDto("test name:"+i, "test description:"+i, 2L, 10000L, 20000L);
            Room room = new Room(roomRequestDto, accommodation);
            roomRepository.save(room);
        }
    }

    @Test
    @DisplayName("User 데이터 생성 확인")
    void testUserDataGeneration() {
        // User 데이터가 1000개 생성되었는지 확인
        assertThat(userRepository.count()).isEqualTo(1000);
        assertThat(accommodationRepository.count()).isEqualTo(1000);
        assertThat(roomRepository.count()).isEqualTo(1000);
    }
}
