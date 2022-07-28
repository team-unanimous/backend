package com.team.unanimous.model.chat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.team.unanimous.dto.requestDto.ChatRoomRequestDto;
import com.team.unanimous.model.Timestamped;
import com.team.unanimous.model.user.User;
import com.team.unanimous.service.UserService;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatRoom extends Timestamped {
    @Id
    @Column(name = "chat_room_id")
    private Long id;

    @Column
    private String chatRoomName;

    @Column
    private Long meetingId;

    @JsonManagedReference
    @OneToMany(mappedBy = "chatRoom",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ChatRoomUser> user;

//    public ChatRoom(ChatRoomRequestDto requestDto, List<ChatRoomUser> user) {
//        this.chatRoomName = requestDto.getChatRoomName();
////        this.user.add(chatRoomUser);
//        this.user = user;
//        }


    public ChatRoom(String name){
        this.chatRoomName = name;
    }
}
