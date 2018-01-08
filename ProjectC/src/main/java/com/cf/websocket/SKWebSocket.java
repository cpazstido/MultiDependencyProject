package com.cf.websocket;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;


@Component
@ServerEndpoint(value = "/websocket", configurator = GetHttpSessionConfigurator.class)
public class SKWebSocket {

	ConcurrentMap<String, Session> concurrentMap = SingletonWebSocketSession.getInstance();

	public SKWebSocket(){
        System.out.println("");
    }
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException, InterruptedException {
		session.getBasicRemote().sendText("This is the last server message");
	}

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		concurrentMap.put(session.getId(), session);
//		@SuppressWarnings("unchecked")
//		Map<String, Object> hotel = (Map<String, Object>) httpSession.getAttribute("hotel");
//		if (null != hotel) {
//			String hId = (String) hotel.get("hotel_id");
//			concurrentMap.put(hId, session);
//		}
	}

	@OnClose
	public void onClose(Session session) {
		Iterator<Entry<String, Session>> it = concurrentMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Session> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				concurrentMap.remove(entry.getKey());
				break;
			}
		}
	}

	public void sendMessageToJspAlone(String message, String hId) throws IOException {
		Session session = concurrentMap.get(hId);
		if (null == session) {
			return ;
		}
		session.getBasicRemote().sendText(message);
	}

	
	
	public void sendMessageToJsp(String message, String sId) throws IOException {
		ThreadPoolManager threadPool = ThreadPoolManager.getInstance();
		String[] str = sId.split(",");
	    List<String> list = java.util.Arrays.asList(str);
		Iterator<Entry<String, Session>> it = concurrentMap.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<String, Session> entry = it.next();
			Session session = entry.getValue();
			String k = entry.getKey();
			boolean falg = false;
			if(list.indexOf(k)!=-1){
				falg = true;
			}
			if (session.isOpen() && falg==true) {
				threadPool.addExecuteTask(new Runnable() {
					@Override
					public void run() {
						try {
							session.getBasicRemote().sendText(message);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});
			}
		}
//		threadPool.shutdown();
	}
}