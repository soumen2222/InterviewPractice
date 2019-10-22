package com.zvarad;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class ChatServer {
	
	static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

	@OnMessage
	public void chat(String msg){
		System.out.println(msg);
		
		for (Session peer:peers){
			try {
				peer.getBasicRemote().sendObject(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EncodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@OnOpen
	public void onOpen(Session session){
	System.out.println("Socket Opened...................");
	peers.add(session);
	}
	
	@OnClose
	public void onClose(Session session){
	System.out.println("Socket Closed...................");
	peers.remove(session);
	}
	
	
	
	
}
