package server;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Vector;

@ServerEndpoint(value = "/ChatServerSocket")
public class ChatServerSocket {
    public static Vector<Session> sessions = new Vector<>();

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            for (Session s : sessions) {
                s.getBasicRemote().sendText(message);
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            close(session);
        }
    }

    @OnOpen
    public void open(Session session) {
        System.out.println("Client Connected!");
        sessions.add(session);
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("Error!");
    }

    @OnClose
    public void close(Session session) {
        System.out.println("Client Disconnected!");
        sessions.remove(session);
    }
}
