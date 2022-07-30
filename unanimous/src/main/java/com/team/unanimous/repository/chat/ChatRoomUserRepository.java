package com.team.unanimous.repository.chat;

import com.team.unanimous.model.chat.ChatRoom;
import com.team.unanimous.model.chat.ChatRoomUser;
import com.team.unanimous.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser,Long> {


    List<ChatRoomUser> findAllByChatRoom(ChatRoom chatRoom);

    List<ChatRoomUser> findAllByChatRoomId(Long chatRoomId);

    Optional<ChatRoomUser> findAllByChatRoom_Id (Long chatRoomId);

    List<ChatRoomUser> findAllByUser_Id(Long id);

    List<ChatRoomUser> findAllByUser_IdAndChatRoomId(Long userId, Long chatRoomId);
}
