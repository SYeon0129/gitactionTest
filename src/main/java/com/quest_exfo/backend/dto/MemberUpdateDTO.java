package com.quest_exfo.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberUpdateDTO {

    private String email;
    private String password;
    private String passwordCheck;
    private String name;

    @Builder
    public MemberUpdateDTO(String email, String password, String passwordCheck, String name){
        this.email=email;
        this.password=password;
        this.passwordCheck=passwordCheck;
        this.name=name;
    }
}
