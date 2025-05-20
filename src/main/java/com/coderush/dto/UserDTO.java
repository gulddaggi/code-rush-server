package com.coderush.dto;

import com.coderush.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String nickname;

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .id(this.id)
                .nickname(this.nickname)
                .build();
    }
}
