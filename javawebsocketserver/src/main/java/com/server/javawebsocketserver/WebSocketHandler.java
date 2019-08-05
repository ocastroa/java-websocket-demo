package com.server.javawebsocketserver;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import java.io.IOException;

public class WebSocketHandler extends AbstractWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String msg = String.valueOf(message.getPayload());
        switch(msg){
            case("1"):
                System.out.println("Dog button was pressed");
                session.sendMessage(new TextMessage("Woooof"));
                break;

            case("2"):
                System.out.println("Cat button was pressed");
                session.sendMessage(new TextMessage("Meooow"));
                break;

            case("3"):
                System.out.println("Pig button was pressed");
                session.sendMessage(new TextMessage("Bork Bork"));
                break;

            case("4"):
                System.out.println("Fox button was pressed");
                session.sendMessage(new TextMessage("Fraka-kaka-kaka"));
                break;
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        System.out.println("New Binary Message Received");
        session.sendMessage(message);
    }
}