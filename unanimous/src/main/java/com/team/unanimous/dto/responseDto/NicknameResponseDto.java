package com.team.unanimous.dto.responseDto;

import com.team.unanimous.model.user.User;
import lombok.Getter;

@Getter
public class NicknameResponseDto {

    private Long userId;

    private String nickname;

    public NicknameResponseDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
    }
}
