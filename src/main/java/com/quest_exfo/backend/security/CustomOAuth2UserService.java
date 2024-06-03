package com.quest_exfo.backend.security;

import com.quest_exfo.backend.dto.MemberSignupDTO;
import com.quest_exfo.backend.entity.Member;
import com.quest_exfo.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth2 프로바이더로부터 받은 사용자 정보를 이용하여 로컬 회원 정보를 확인하거나 생성
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");

        // 회원 가입 여부 확인
        Member member = memberService.findMemberByEmail(email);
        if (member == null) {
            // 새 회원 생성
            MemberSignupDTO signupDTO = new MemberSignupDTO();
            signupDTO.setEmail(email);
            // 추가 필요한 정보가 있다면 여기에 추가

            member = memberService.signup(signupDTO);


            // 새 회원을 등록한 후에는 다시 회원을 검색하여 가져옴
            member = memberService.findMemberByEmail(email);
        }

        // 로그인 완료 후 토큰 생성
        String token = jwtTokenProvider.createToken(member.getName(), member.getRole().name());

        // 토큰과 함께 로그인 완료 메시지를 반환
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("message", "로그인 완료");
        return new DefaultOAuth2User(
                Collections.singleton(new OAuth2UserAuthority(attributes)),
                attributes,
                "email");
    }
}
