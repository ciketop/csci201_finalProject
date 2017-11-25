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

            // Find the vector containing all the users in the current class
            String qString = session.getQueryString();
			String[] split = qString.split("class=");
			String courseName = split[1];
			Vector<Session> connections = classMap.get(courseName);
			// Loop through the vector, send stream to all
			for(Session s:connections) {
				s.getBasicRemote().sendBinary(buffer);
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
		if(connections == null) {
			System.out.println("adding class - " + courseName);
			Vector<Session> newConnections = new Vector<Session>();
			newConnections.add(session);
			classMap.put(courseName, newConnections);
		}
		// If found, insert session into the vector
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
    		// Remove session from vector containing all the sessions
        System.out.println("Goodbye !");
        sessions.remove(session);
        
        // Find the session in the map and remove it
        String qString = session.getQueryString();
		String[] split = qString.split("class=");
		String courseName = split[1];
		Vector<Session> allConnections = classMap.get(courseName);
		allConnections.remove(session);
    }
}
