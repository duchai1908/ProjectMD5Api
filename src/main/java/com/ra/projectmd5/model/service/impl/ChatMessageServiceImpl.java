package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.model.entity.ChatMessage;
import com.ra.projectmd5.model.repository.ChatMessageRepository;
import com.ra.projectmd5.model.service.IChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements IChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessage createChatMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    @Override
    public List<ChatMessage> getHistory(Long senderId, Long receiverId) {
        return chatMessageRepository.getHistory(senderId, receiverId);
    }
}
