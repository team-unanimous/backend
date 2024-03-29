package com.team.unanimous.model.chat;

import com.team.unanimous.dto.requestDto.ChatMessageRequestDto;
import com.team.unanimous.model.Timestamped;
import com.team.unanimous.model.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    public enum MessageType {
        STAMP, TALK, RESULT, ISSUE
    }
    //ENTER를 STAMP로 QUIT-> RESULT

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private MessageType type;

    @Column
    private String roomId;

    // Redis MessageListener 로 뒙소켓을 통해 바로 채팅방에 메시지를 전달해주기 위한 값을 따로 설정해주었다
    @Column
    private String nickname;

    @Column
    private String sender;

    @Column
    private String message;

    @Column
    private String profileUrl;

    @Column
    private String createdAt;


    @Builder
    public ChatMessage(ChatMessageRequestDto chatMessageRequestDto, User user, String createdAt) {
        this.type = chatMessageRequestDto.getType();
        this.roomId = chatMessageRequestDto.getRoomId();
        this.nickname = chatMessageRequestDto.getNickname();
        this.sender = chatMessageRequestDto.getSender();
        this.message = chatMessageRequestDto.getMessage();
        this.profileUrl = user.getImage().getImageUrl();
        this.createdAt = createdAt;
    }
}