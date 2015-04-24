package Web;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import rmiclient.RMIClient;

public final class ConnectionMenu extends MessageInbound {
    
	private final String username, type;
	private final String reuniao;
	private String naming = "//127.0.0.1:25055/calc";
	private RMIClient rmiCon;
	
    public ConnectionMenu(String username, String type, String reuniao) {
        this.username = username;
        this.type = type;
        this.reuniao = reuniao;
        rmiCon = new RMIClient(naming);
    }
    
    @Override 
    protected void onOpen(WsOutbound outbound) { 
        MenuWebSocket.getConnections().add(this);
        /*String message = String.format("%s ligou-se!", username);

        MenuWebSocket.broadcast(message, username);*/
    }
    
    @Override 
    protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
        throw new RuntimeException("Metodo n‹o aceito");
    }
    
    @Override 
    protected void onTextMessage(CharBuffer msg) throws IOException {
    	
    	String message = String.format("%s", msg.toString());
    	String[] temp = message.split("#");
    	
    	if(temp[2].equals("convite")){
    		
    		String conv = rmiCon.mostraConvites(temp[0]);
        	String[] tmp = conv.split("#");
        	
        	String imprimeBotao;
        	
        	rmiCon.convidarUsers("null#"+temp[1]+"#"+temp[0]);
        	if(tmp[0].equals("Sem convites") || tmp[0].equals("null") || tmp[0].equals(null)) imprimeBotao = (1)+ " - " + temp[1];
        	else imprimeBotao = (tmp.length+1)+ " - " + temp[1];

            MenuWebSocket.broadcast(imprimeBotao, temp[0], "convite", null);
            
    	}
    	else if(temp[2].equals("item")){
    		String conv = rmiCon.consultarActionItem(temp[1]);
        	String[] tmp = conv.split("#");
        	
        	rmiCon.criaActionItem(temp[1], temp[0]);
        	
        	String imprimeBotao;
        	
        	if(tmp[0].equals("Sem Action Items") || tmp[0].equals("null") || tmp[0].equals(null)) imprimeBotao = (1)+ " - " + temp[0];
        	else imprimeBotao = (tmp.length+1)+ " - " + temp[0];
        		
            MenuWebSocket.broadcast(imprimeBotao, null, "item", temp[1]);
    	}
    	else if(temp[2].equals("decision")){
    		String conv = rmiCon.consultaDecisions(temp[1]);
        	String[] tmp = conv.split("#");
        	
    		rmiCon.criarDecisions("null#"+temp[1]+"#"+temp[0]);
        	
        	String imprimeBotao;
        	
        	if(tmp[0].equals("Sem Key Decisions") || tmp[0].equals("null") || tmp[0].equals(null)) imprimeBotao = (1)+ " - " + temp[0];
        	else imprimeBotao = (tmp.length+1)+ " - " + temp[0];
        		
            MenuWebSocket.broadcast(imprimeBotao, null, "decision", temp[1]);
    	}
    	else if(temp[2].equals("tarefa")){
    		String conv = rmiCon.consultarTarefas(temp[1]);
        	String[] tmp = conv.split("#");
        	
        	String[] quem = temp[0].split(" - ");

        	rmiCon.criarTarefas("null#"+temp[1]+"#"+quem[1]+" - "+quem[0]);
        	
        	String imprimeBotao;
        	
        	if(tmp[0].equals("Sem tarefas") || tmp[0].equals("null") || tmp[0].equals(null)) imprimeBotao = (1)+ " > " + temp[0];
        	else imprimeBotao = (tmp.length+1)+ " > " + temp[0];
        	
            MenuWebSocket.broadcast(imprimeBotao, null, "tarefa", temp[1]);
    	}
        
    }
	
	public String getReuniao() {
		return reuniao;
	}

	public String getType() {
		return type;
	}

	public String getUsername() {
		return username;
	}
    
}