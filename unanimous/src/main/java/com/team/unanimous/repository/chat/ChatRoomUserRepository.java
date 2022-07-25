package com.team.unanimous.repository.chat;

import com.team.unanimous.model.chat.ChatRoom;
import com.team.unanimous.model.chat.ChatRoomUser;
import com.team.unanimous.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser,Long> {


    List<ChatRoomUser> findAllByChatRoom(ChatRoom chatRoom);

    Optional<ChatRoomUser> findAllByChatRoom_Id (Long chatRoomId);

    ChatRoomUser findByUser_Id(Long id);

    Optional<ChatRoomUser> findAllByUser_Id(Long id);

    void deleteByUser_Id(Long userId);
}
