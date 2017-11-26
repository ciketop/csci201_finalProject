package server;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@ServerEndpoint(value = "/liveStreamAudio")
public class LiveStreamAudio {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void processAudio(ByteBuffer audioData, Session session) {
        try {
            for (Session ss : sessions) {
                if (session == ss) {
//                    System.out.println("session " + session + " skipped!");
                    continue;
                }
                ss.getBasicRemote().sendBinary(audioData);
            }
        } catch (Throwable ioe) {
            System.out.println("Error sending message " + ioe.getMessage());
        }

    }

    @OnOpen
    public void whenOpening(Session session) throws IOException, EncodeException {
        System.out.println(session);

        session.setMaxBinaryMessageBufferSize(4096 * 1024);
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