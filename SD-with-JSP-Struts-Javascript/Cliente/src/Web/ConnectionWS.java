package Web;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import rmiclient.RMIClient;

public final class ConnectionWS extends MessageInbound {
    
	private final String username;
	private final String reuniao;
	private final String actionItem;
	private String naming = "//127.0.0.1:25055/calc";
	private RMIClient rmiCon;
	
    public ConnectionWS(String username, String reuniao, String actionItem) {
        this.username = username;
        this.reuniao = reuniao;
        this.actionItem = actionItem;
        rmiCon = new RMIClient(naming);
    }
    
    @Override 
    protected void onOpen(WsOutbound outbound) { 
        ServletWebSocket.getConnections().add(this);
        String message = String.format("%s ligou-se!", username);
        rmiCon.guardaChat(reuniao, actionItem, message);
        ServletWebSocket.broadcast(message, reuniao, actionItem, username);
    }
    
    @Override 
    protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
        throw new RuntimeException("Metodo n‹o aceito");
    }
    
    @Override 
    protected void onTextMessage(CharBuffer msg) throws IOException {
        String message = String.format("%s: %s", username, msg.toString());
        rmiCon.guardaChat(reuniao, actionItem, message);
        ServletWebSocket.broadcast(message, reuniao, actionItem, username);
    }

	public String getReuniao() {
		return reuniao;
	}

	public String getActionItem() {
		return actionItem;
	}
	
	public String getUsername() {
		return username;
	}
    
}