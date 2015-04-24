package Web;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
 
@WebServlet(urlPatterns = "/simple")
public class ServletWebSocket extends WebSocketServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final ArrayList < ConnectionWS > connections = new ArrayList < ConnectionWS > ();
    
    @Override 
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
        String username = request.getParameter("username");
        String reuniao = request.getParameter("reuniao");
        String actionItem = request.getParameter("actionItem");
        return new ConnectionWS(username, reuniao, actionItem);
    }
    
    public static final ArrayList < ConnectionWS > getConnections() {
        return connections;
    }
    
    public static final void broadcast(String message, String reuniao, String actionItem, String username) {
        for (ConnectionWS con: ServletWebSocket.getConnections()) {
        	if(con.getReuniao().equals(reuniao) && con.getActionItem().equals(actionItem) && !con.getUsername().equals(username)){
        		try {
                    con.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
                    System.out.println("Enviou!");
                } catch (IOException ioe) {
                    System.out.println("Aconteceu um erro");
                }
        	}
        }
    }
}