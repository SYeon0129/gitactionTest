package com.quest_exfo.backend.service;

import com.quest_exfo.backend.common.Role;
import com.quest_exfo.backend.dto.MemberLoginDTO;
import com.quest_exfo.backend.dto.MemberSignupDTO;
import com.quest_exfo.backend.entity.Member;
import com.quest_exfo.backend.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member signup(MemberSignupDTO dto){
        logger.info("회원가입 요청: {}", dto);
        Member member = new Member();
        member.setEmail(dto.getEmail());
        member.setPassword(passwordEncoder.encode(dto.getPassword()));
        member.setName(dto.getName());
        member.setRole(Role.USER);
        Member savedMember = memberRepository.save(member);
        logger.info("회원가입 완료: {}", savedMember);
        return savedMember;
    }

    public Member login(MemberLoginDTO dto){
        logger.info("로그인 요청: {}", dto);
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("가입되어 있지 않습니다"));
        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())){
            throw new RuntimeException("비밀번호가 틀립니다");
        }
        logger.info("로그인 성공: {}", member);
        return member;
    }

    public Member findMemberByEmail(String email) {
        // 이메일을 기반으로 회원을 찾습니다.
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        return memberOptional.orElse(null);
    }

}
