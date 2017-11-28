package server;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@ServerEndpoint(value = "/liveStreamAudioThreaded")
public class LiveStreamAudioThreaded {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    private static Map<String, Vector<Session>> classMap = Collections.synchronizedMap(new HashMap<String, Vector<Session>>());

    @OnMessage
    public void processAudio(ByteBuffer audioData, Session session) {
        try {
            // Find the vector containing all the users in the current class
            String qString = session.getQueryString();
            String[] split = qString.split("class=");
            String courseName = split[1];

            Vector<Session> connections = new Vector<>(classMap.get(courseName));
            // do not send message back to the sender
            connections.remove(session);

            if (connections.isEmpty()) {
                return;
            }

            int step = connections.size() / Runtime.getRuntime().availableProcessors();
            if (step < 1)
                step = 1;

            ExecutorService executor = Executors.newFixedThreadPool(connections.size());
            for (int i = step, startIdx = 0; i <= connections.size(); i += step) {
                executor.execute(
                        new SendingThread(audioData, new Vector<>(connections.subList(startIdx, i)))
                );
                startIdx = i;
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
                Thread.yield();
            }
        } catch (Throwable ioe) {
            System.out.println("Error sending message " + ioe.getMessage());
        }

    }

    @OnOpen
    public void whenOpening(Session session) throws IOException, EncodeException {
        // Split the query string and get the class parameter
        String qString = session.getQueryString();
        String[] split = qString.split("class=");
        String courseName = split[1];

        // Try and find the vector with the name as the key
        Vector<Session> connections = classMap.get(courseName);
        // If none found, create a new entry with the name
        if (connections == null) {
            System.out.println("adding class - " + courseName);
            Vector<Session> newConnections = new Vector<>();
            newConnections.add(session);
            classMap.put(courseName, newConnections);
        }
        // If found, insert session into the vector
        else {
            System.out.println("adding session into " + courseName);
            connections.add(session);
        }

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
        // Remove session from vector containing all the sessions
        System.out.println("Session " + session.getId() + " disconnected!");
        sessions.remove(session);

        // Find the session in the map and remove it
        String qString = session.getQueryString();
        String[] split = qString.split("class=");
        String courseName = split[1];
        Vector<Session> allConnections = classMap.get(courseName);
        allConnections.remove(session);
    }
}