// package com.project.stone.websocket2;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import javax.websocket.ContainerProvider;
// import javax.websocket.DeploymentException;
// import javax.websocket.Session;
// import javax.websocket.WebSocketContainer;

// import java.io.IOException;
// import java.net.URI;

// @RestController
// // @RequestMapping("/api/websocket")
// public class WebSocketController {

//     private WebSocketManager webSocketManager;

//     @Autowired
//     public WebSocketController(WebSocketManager webSocketManager) {
//         this.webSocketManager = webSocketManager;
//     }

//     @PostMapping("/api/websocket/create")
//     public void createWebSocket() throws DeploymentException, IOException {
//         // Create a new WebSocket connection and add it to the manager
//         WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//         String id = "my-websocket";
//         Session session = container.connectToServer(MyWebSocketEndpoint.class, URI.create("ws://localhost:8080/websocket"));
//         webSocketManager.addWebSocket(id, session);
//     }

//     @PostMapping("/close")
//     public void closeWebSocket(@RequestParam String id) throws IOException {
//         // Close the WebSocket connection and remove it from the manager
//         webSocketManager.closeWebSocket(id);
//     }
// }

