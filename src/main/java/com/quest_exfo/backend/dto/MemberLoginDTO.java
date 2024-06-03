package com.quest_exfo.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberLoginDTO {

    private String email;
    private String password;

    @Builder
    public MemberLoginDTO(String email, String password){
        this.email=email;
        this.password=password;
    }
}
