package Web;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
 
@WebServlet(urlPatterns = "/menu")
public class MenuWebSocket extends WebSocketServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final ArrayList < ConnectionMenu > connections = new ArrayList < ConnectionMenu > ();
    
    @Override 
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
        String username = request.getParameter("username");
        String type = request.getParameter("type");
        String reuniao = request.getParameter("reuniao");
        return new ConnectionMenu(username, type, reuniao);
    }
    
    public static final ArrayList < ConnectionMenu > getConnections() {
        return connections;
    }
    
    public static final void broadcast(String message, String username, String type, String reuniao) {
    	
    	ArrayList<ConnectionMenu> temp = getConnections();
    	String finalout = "<li>"+"Notificacao ("+type+"): "+message+"</li>";
    	
    	System.out.println(reuniao + " - "+finalout+" - "+type);
    	
    	if(type.equals("convite")){
    		for (int i = 0; i < temp.size(); i++) {
            	if(temp.get(i).getUsername().equals(username) && temp.get(i).getType().equals("outside")){
            		try {
            			temp.get(i).getWsOutbound().writeTextMessage( CharBuffer.wrap(finalout) ) ; 
            			System.out.println("Enviando mensagem de texto (" + finalout + ")");
            			
                    } catch (IOException ioe) {
                        System.out.println("Aconteceu um erro");
                    }
            	}
            }
    	}
    	else{
    		for (int i = 0; i < temp.size(); i++) {
            	if(temp.get(i).getType().equals("inside") && temp.get(i).getReuniao().equals(reuniao)){
            		try {
            			temp.get(i).getWsOutbound().writeTextMessage( CharBuffer.wrap(finalout) ) ; 
            			System.out.println("Enviando mensagem de texto (" + finalout + ")");
            			
                    } catch (IOException ioe) {
                        System.out.println("Aconteceu um erro");
                    }
            	}
            }
    	}
    		
        
    }
}