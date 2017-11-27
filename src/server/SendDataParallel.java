package server;

import javax.websocket.Session;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.RecursiveAction;

public class SendDataParallel extends RecursiveAction {
    private long numElements;
    private List<Session> sessions;
    private ByteBuffer data;

    public SendDataParallel(List<Session> sessions, ByteBuffer data, long numElements) {
        this.sessions = sessions;
        this.numElements = numElements;
        this.data = data;
    }

    @Override
    protected void compute() {
        try {
            if (sessions.size() <= numElements) {
                for (Session ss : sessions) {
                    ss.getBasicRemote().sendBinary(data);
                }

                return;
            }

            SendDataParallel s1 = new SendDataParallel(sessions.subList(0, sessions.size() / 2), data, numElements);
            SendDataParallel s2 = new SendDataParallel(sessions.subList(sessions.size() / 2, sessions.size()), data, numElements);

            s1.fork();    // start task in parallel
            s2.fork();

            s1.join();
            s2.join();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
