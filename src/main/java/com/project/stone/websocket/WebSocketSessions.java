// package com.project.stone.websocket;

// import java.util.Set;
// import java.util.concurrent.ConcurrentHashMap;

// import org.springframework.web.socket.WebSocketSession;

// import org.springframework.stereotype.Component;

// @Component
// public class WebSocketSessions {

//     private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

//     public void addSession(WebSocketSession session) {
//         sessions.add(session);
//     }

//     public void removeSession(WebSocketSession session) {
//         sessions.remove(session);
//     }

//     public Set<WebSocketSession> getSessions() {
//         return sessions;
//     }

//     // public void addSession(WebSocketSession session) {
//     //     sessions.add(session);
//     // }

//     // public void removeSession(WebSocketSession session) {
//     //     sessions.remove(session);
//     // }
// }