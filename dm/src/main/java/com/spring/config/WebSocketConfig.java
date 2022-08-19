package com.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.spring.dm.UserHandshakeHandler;

//import com.spring.stomp.UserHandshakeHandler;

@Configuration // Spring Configuration class
@EnableWebSocketMessageBroker // WebScoket message handling을 허용해준다 
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	

  @Override // MessageBroker는 송신자에게 수신자의 이전 메세지 프로토콜로 변환해주는 모듈 중 하나
            // 요청이 오면 그에 해당하는 통신 채널로 전송, 응답 또한 같은 경로로 가서 응답한다.
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic/", "/queue/"); // 해당 주소를 구독하고 있는 클라이언트들에게 메시지 전달
    config.setApplicationDestinationPrefixes("/send"); // 클라이언트에서 보낸 메시지를 받을 prefix
    config.setUserDestinationPrefix("/user");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {// 최초 소켓 연결 시 endpoint
    registry.addEndpoint("/ws-dm")  //SocketJs 연결 주소
//    client.send(`/dm/notice/보낼주소`,{},JSON.stringify(보낼데이터))
    		.setAllowedOrigins("http://localhost:8080", "https://apic.app", "http://localhost:3000")
    		.setHandshakeHandler(new UserHandshakeHandler())
    		.withSockJS(); // javascript에서 SockJS생성자를 통해 연결
    
  
  }

}

//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//import com.spring.websockethandler.NoticeWebSocketHandler;
//
//import lombok.RequiredArgsConstructor;
//
//@Configuration
//@EnableWebSocket
//@RequiredArgsConstructor
//public class WebSocketConfig implements WebSocketConfigurer{
//	
//	private final NoticeWebSocketHandler noticeHandler;
//	
//	@Override
//	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//		registry.addHandler(noticeHandler, "ws/notice").setAllowedOrigins("*");
//	}
//	
//}