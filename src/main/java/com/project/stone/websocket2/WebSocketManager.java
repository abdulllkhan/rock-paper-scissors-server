// package com.project.stone.websocket2;

// import javax.websocket.Session;

// import org.springframework.stereotype.Component;
// import org.springframework.stereotype.Service;

// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;

// @Component
// @Service
// public class WebSocketManager {

//     private final Map<String, Session> webSockets = new ConcurrentHashMap<>();

//     public void addWebSocket(String id, Session session) {
//         webSockets.put(id, session);
//     }

//     public void removeWebSocket(String id) {
//         webSockets.remove(id);
//     }

//     public Session getWebSocket(String id) {
//         return webSockets.get(id);
//     }

//     public void closeWebSocket(String id) {
//         Session session = webSockets.get(id);
//         if (session != null && session.isOpen()) {
//             try {
//                 session.close();
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         }
//         removeWebSocket(id);
//     }
// }

