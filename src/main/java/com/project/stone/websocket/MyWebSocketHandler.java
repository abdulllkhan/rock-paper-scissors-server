// package com.project.stone.websocket;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
// import org.springframework.stereotype.Service;
// import org.springframework.web.socket.CloseStatus;
// import org.springframework.web.socket.WebSocketHandler;
// import org.springframework.web.socket.WebSocketMessage;
// import org.springframework.web.socket.WebSocketSession;

// @Component
// @Service
// public class MyWebSocketHandler implements WebSocketHandler {

//     private final WebSocketSessions webSocketSessions;

//     @Autowired
//     public MyWebSocketHandler(WebSocketSessions webSocketSessions) {
//         this.webSocketSessions = webSocketSessions;
//     }

//     @Override
//     public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//         webSocketSessions.addSession(session);
//     }

//     @Override
//     public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//         webSocketSessions.removeSession(session);
//     }

//     @Override
//     public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'handleMessage'");
//     }

//     @Override
//     public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'handleTransportError'");
//     }

//     @Override
//     public boolean supportsPartialMessages() {
//         return false;
//     }

// }
