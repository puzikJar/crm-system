package com.ox.company.crm.config.websocket;

import com.ox.company.crm.event.TaskAssignEvent;
import com.ox.company.crm.websocket.model.ChatMessage;
import com.ox.company.crm.websocket.model.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageSendingOperations;

    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations messageSendingOperations) {
        this.messageSendingOperations = messageSendingOperations;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        var headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        var username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {
            messageSendingOperations
                    .convertAndSend("/topic/public",
                            new ChatMessage("", username, MessageType.LEAVE));
        }
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        var headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        var username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {
            messageSendingOperations
                    .convertAndSend("/topic/public",
                            new ChatMessage("", username, MessageType.JOIN));
        }
    }

    @EventListener
    public void handleNewTaskAssignEvent(TaskAssignEvent event) {
        messageSendingOperations.convertAndSend("/topic/public", event);
    }

}
