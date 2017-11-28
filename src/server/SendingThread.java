package server;

import javax.websocket.Session;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

public class SendingThread extends Thread {
    private ByteBuffer buffer;
    private Vector<Session> sessions;

    public SendingThread(ByteBuffer buffer, Vector<Session> sessions) {
        this.buffer = buffer;
        this.sessions = sessions;
    }

    @Override
    public void run() {
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendBinary(buffer);
            } catch (IOException ex) {
                System.out.println("@SendingThread.run() -> IOException:" + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
