package com.quest_exfo.backend.controller;

import com.quest_exfo.backend.dto.MemberLoginDTO;
import com.quest_exfo.backend.dto.MemberSignupDTO;
import com.quest_exfo.backend.entity.Member;
import com.quest_exfo.backend.security.CustomOAuth2UserService;
import com.quest_exfo.backend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberSignupDTO signupDTO) {
        String token = jwtTokenProvider.createToken(signupDTO.getEmail(), "ROLE_USER");
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginDTO loginDTO) {
        // 로그인을 직접 처리하는 코드가 없으므로, OAuth2 프로바이더로부터 받은 정보로 토큰을 생성
        String token = jwtTokenProvider.createToken(loginDTO.getEmail(), "ROLE_USER");
        return ResponseEntity.ok(token);
    }
}