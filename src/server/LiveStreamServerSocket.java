package server;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value = "/liveStream")
public class LiveStreamServerSocket {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void processVideo(byte[] imageData, Session session) {
    		
        try {
            // Wrap a byte array into a buffer

            ByteBuffer buffer = ByteBuffer.wrap(imageData);

            for (Session ss : sessions) {
                ss.getBasicRemote().sendBinary(buffer);
            }
        } catch (Throwable ioe) {
            System.out.println("Error sending message " + ioe.getMessage());
        }

    }

    @OnOpen
    public void whenOpening(Session session) throws IOException, EncodeException {
        session.setMaxBinaryMessageBufferSize(1024 * 1024);
        System.out.println(session.getId() + " - connected!");
        sessions.add(session);
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("Error!");
        error.printStackTrace();
    }

    @OnClose
    public void whenClosing(Session session) {
        System.out.println("Goodbye !");
        sessions.remove(session);
    }
}
