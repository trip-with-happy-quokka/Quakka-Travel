package com.sparta.quokkatravel;

import com.sparta.quokkatravel.domain.user.entity.UserRole;
import org.junit.jupiter.api.Test;


class UserRoleTest {
    @Test
    void test(){
        System.out.print(UserRole.values());
        System.out.print(UserRole.USER.name());
        System.out.print(UserRole.USER.ordinal());
    }
}
