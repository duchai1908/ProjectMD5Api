package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT c FROM ChatMessage c WHERE (c.senderId = :senderId AND c.recipientId = :receiverId) OR (c.senderId = :receiverId AND c.recipientId = :senderId) ORDER BY c.timestamp")
    List<ChatMessage> getHistory(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
}
