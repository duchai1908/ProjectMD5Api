package com.ra.projectmd5.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
// vì khi dùng socket các request không dùng RequestMapping mà dùng MessageMapping nên phải dùng @EnableWebSocketMessageBroker
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/queue","/user"); // Định nghĩa nơi server gửi tin nhắn tới
        registry.setApplicationDestinationPrefixes("/app"); // Tiền tố cho các tin nhắn client gửi lên
        // Đặt prefix cho các kênh riêng tư
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-websocket").setAllowedOrigins("http://localhost:5174","http://localhost:5173").withSockJS(); // Endpoint để client kết nối
    }
}
