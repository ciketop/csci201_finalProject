package server;
//package csci201;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint( value ="/chatroom")
public class ServerSocket {
	
	// Vector to hold all the sessions
	// Map between class name and all the users in that class
	private static Vector<Session> sessionVector = new Vector<Session>();
	private static Map<String, Vector<Session>> classMap = Collections.synchronizedMap(new HashMap<String, Vector<Session> >());
	
	@OnOpen
	public void open(Session session) {

		// Get the query string which should be in the form of "class=<something>"
		// Split it and the parameter value
		String qString = session.getQueryString();
		String[] split = qString.split("class=");
		String courseName = split[1];
		
		// See if there's already an entry in the Map, if not, create one
		Vector<Session> connections = classMap.get(courseName);
		if(connections == null) {
			System.out.println("adding class - " + courseName);
			Vector<Session> newConnections = new Vector<Session>();
			newConnections.add(session);
			classMap.put(courseName, newConnections);
		}
		// If there is, add this session to that entry in the map
		else {
			System.out.println("adding session into " + courseName);
			connections.add(session);
		}
		
		System.out.println("courseName: " + courseName);
		sessionVector.add(session);
	}
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println(message);
		try {
			// Again, try to find the vector containing all the sessions in the class
			String qString = session.getQueryString();
			String[] split = qString.split("class=");
			String courseName = split[1];
			Vector<Session> connections = classMap.get(courseName);
			
			// Loop through the vector and send the message to everyone in the class
			for(Session s:connections) {
				s.getBasicRemote().sendText(message);
			}
		}catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			close(session);
		}
	}
	@OnClose
	public void close(Session session) {
		System.out.println("Client Disconnected!");
		// Remove the session from the overallVector
		sessionVector.remove(session);
		
		// Remove the session from the map
		String qString = session.getQueryString();
		String[] split = qString.split("class=");
		String courseName = split[1];
		Vector<Session> allConnections = classMap.get(courseName);
		allConnections.remove(session);
	}
	@OnError
	public void onError(Throwable error) {
		System.out.println("Error!");
		System.out.println(error.getMessage());
	}
}
