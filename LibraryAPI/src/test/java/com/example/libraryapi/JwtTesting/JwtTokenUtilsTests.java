package com.example.libraryapi.JwtTesting;

import com.example.libraryapi.models.User;
import com.example.libraryapi.models.enums.Role;
import com.example.libraryapi.utils.JwtTokenUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class JwtTokenUtilsTests {

    private final static JwtTokenUtils jwtTokenUtils=new JwtTokenUtils();
    private final static User user=new User();
    private final static User admin=new User();
    private final static String USERNAME_USER="user";
    private final static String USERNAME_ADMIN="admin";
    private final static String ROLE_USER="ROLE_USER";
    private final static String ROLE_ADMIN="ROLE_ADMIN";
    private final static String JWT_SECRET="984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gf";
    private static String  userToken;
    private static String adminToken;

    @BeforeAll
    static void setup(){

        user.setUsername(USERNAME_USER);
        user.setRoles(Set.of(Role.ROLE_USER));

        admin.setUsername(USERNAME_ADMIN);
        admin.setRoles(Set.of(Role.ROLE_ADMIN));

        jwtTokenUtils.setJwtLifetime(Duration.ofMinutes(30));
        jwtTokenUtils.setSecret(JWT_SECRET);

        userToken=jwtTokenUtils.generateToken(user);
        adminToken=jwtTokenUtils.generateToken(admin);
    }



    @Test
    void testClaims(){

        assertNotNull(userToken);
        assertNotNull(adminToken);

        assertEquals(jwtTokenUtils.getUsername(userToken), USERNAME_USER);
        assertEquals(jwtTokenUtils.getRoles(userToken), List.of(ROLE_USER));

        assertEquals(jwtTokenUtils.getUsername(adminToken),USERNAME_ADMIN);
        assertEquals(jwtTokenUtils.getRoles(adminToken),List.of(ROLE_ADMIN));

    }




}
