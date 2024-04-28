// package com.project.stone.websocket2;

// import javax.websocket.*;
// import javax.websocket.server.ServerEndpoint;
// import java.io.IOException;

// @ServerEndpoint("/websocket")
// public class MyWebSocketEndpoint {

//     private static WebSocketManager webSocketManager = new WebSocketManager();

//     @OnOpen
//     public void onOpen(Session session) {
//         // Add the WebSocket session to the manager
//         webSocketManager.addWebSocket(session.getId(), session);
//     }

//     @OnMessage
//     public void onMessage(String message, Session session) {
//         // Handle incoming messages
//     }

//     @OnClose
//     public void onClose(Session session) {
//         // Remove the WebSocket session from the manager
//         webSocketManager.removeWebSocket(session.getId());
//     }

//     @OnError
//     public void onError(Throwable error) {
//         error.printStackTrace();
//     }

//     // Additional methods for sending messages, etc.
// }
