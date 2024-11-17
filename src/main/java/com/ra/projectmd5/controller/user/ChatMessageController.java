package com.ra.projectmd5.controller.user;

import com.ra.projectmd5.model.entity.ChatMessage;
import com.ra.projectmd5.model.service.IChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping
@RestController
public class ChatMessageController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private IChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void sendMessage( ChatMessage chatMessage ) {
        // Lưu tin nhắn vào database thông qua service
        ChatMessage savedMessage = chatMessageService.createChatMessage(chatMessage);

        // Tạo channelId chung cho cặp người dùng
//        String channelId = createChannelId(chatMessage.getSenderId(), chatMessage.getRecipientId());

        // Gửi tin nhắn đến kênh của người nhận
        String destinationRecipient = "/user/" + chatMessage.getRecipientId() + "/chat/messages";
        // Gửi tin nhắn đến kênh của người gửi
        String destinationSender = "/user/" + chatMessage.getSenderId() + "/chat/messages" ;
        messagingTemplate.convertAndSend(destinationRecipient, savedMessage);
        messagingTemplate.convertAndSend(destinationSender, savedMessage);
    }
}
