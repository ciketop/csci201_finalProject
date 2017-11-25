package server;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

@ServerEndpoint(value = "/liveStream")
public class LiveStreamServerSocket {
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    private static Map<String, Vector<Session>> classMap = Collections.synchronizedMap(new HashMap<String, Vector<Session> >());
    
    @OnMessage
    public void processVideo(byte[] imageData, Session session) {
        try {
            // Wrap a byte array into a buffer

            ByteBuffer buffer = ByteBuffer.wrap(imageData);

            String qString = session.getQueryString();
			String[] split = qString.split("class=");
			String courseName = split[1];
			Vector<Session> connections = classMap.get(courseName);
			for(Session s:connections) {
				s.getBasicRemote().sendBinary(buffer);
			}
        } catch (Throwable ioe) {
            System.out.println("Error sending message " + ioe.getMessage());
        }

    }

    @OnOpen
    public void whenOpening(Session session) throws IOException, EncodeException {
    		
    		String qString = session.getQueryString();
		String[] split = qString.split("class=");
		String courseName = split[1];
		Vector<Session> connections = classMap.get(courseName);
		if(connections == null) {
			System.out.println("adding class - " + courseName);
			Vector<Session> newConnections = new Vector<Session>();
			newConnections.add(session);
			classMap.put(courseName, newConnections);
		}
		else {
			System.out.println("adding session into " + courseName);
			connections.add(session);
		}
    	
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
        
        String qString = session.getQueryString();
		String[] split = qString.split("class=");
		String courseName = split[1];
		Vector<Session> allConnections = classMap.get(courseName);
		allConnections.remove(session);
    }
}
