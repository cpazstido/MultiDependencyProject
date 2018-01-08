package com.cf.websocket;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SingletonWebSocketSession {
	private static  ConcurrentMap<String, Session> instance;

	private SingletonWebSocketSession() {}

	public static ConcurrentMap <String, Session> getInstance() {
		if (instance == null) {
			synchronized (SingletonWebSocketSession.class) {
				if (instance == null)
					instance = new ConcurrentHashMap<String, Session>();
			}
		}
		return instance;
	}
}
