package com.ra.projectmd5.model.service;

import com.ra.projectmd5.model.entity.ChatMessage;

import java.util.List;

public interface IChatMessageService {
    ChatMessage createChatMessage(ChatMessage chatMessage);
    List<ChatMessage> getHistory(Long senderId,Long receiverId);
}
