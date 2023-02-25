package com.Easy.webcarpool.api.repository;

import com.Easy.webcarpool.api.domain.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {


    @Query("SELECT c FROM ChatRoomEntity c WHERE c.roomid = :roomId")
    public Optional<ChatRoomEntity> findChatRoomByRoomId(@Param("roomId") String roomId);

    //select * from testTBL where name1='kim' or name2='kim';
    @Query("SELECT c FROM ChatRoomEntity c WHERE c.passenger = :email OR c.driver = :email")
    public List<ChatRoomEntity> findChatRoomByEmail(@Param("email") String email);


    //select * from chat_room_entity where post_type = 'driver' AND post_id=2 AND passenger='lee' AND driver='kim';

    @Query("SELECT c FROM ChatRoomEntity c WHERE c.postType = :postType AND c.postId = :postId AND c.driver = :driver AND c.passenger = :passenger")
    Optional<ChatRoomEntity> findChatRoomByDto(@Param("postType") String postType,
                                               @Param("postId") Long postId,
                                               @Param("driver") String driver,
                                               @Param("passenger") String passenger);
    @Transactional
    @Modifying
    @Query("UPDATE ChatRoomEntity c SET c.lastChatTime = :lastChatTime , c.lastMessage = :lastMessage WHERE c.roomid = :roomId")
    void updateLastChat(@Param("roomId") String roomId, @Param("lastChatTime") String lastChatTime, @Param("lastMessage") String lastMessage);
}
