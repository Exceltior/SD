/**
 * Raul Barbosa 2014-11-07
 */
package hey.action;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.interceptor.SessionAware;

import rmiclient.RMIClient;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;


public class ItemAction extends ActionSupport implements SessionAware {
	
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, reuniao = null, type = null, actionItem = null, newName = null;
	private String naming = "//127.0.0.1:25055/calc";
	private int idItem;

	private RMIClient rmiCon;

	@Override
	public String execute() throws MalformedURLException, RemoteException, NotBoundException {

		rmiCon = new RMIClient(naming);
		
		if(type.equals("EntrarItem")){
			
			String item = rmiCon.consultarActionItem(reuniao);
			String[] temp = item.split("#");
			
			idItem = Integer.parseInt(actionItem);
	    	actionItem = temp[Integer.parseInt(actionItem)-1];
	    	
			entraItem();
			return SUCCESS;
		}
		else if(type.equals("MudarNome")){
			
			rmiCon.modificarActionItem(reuniao, actionItem, newName);
			actionItem = newName;
			entraItem();
			return SUCCESS;
			
		}
		
		return null;
		
	}
	
	public void setUsername(String username) {
		this.username = username; // will you sanitize this input? maybe use a prepared statement?
	}

	public void setReuniao(String reuniao) {
		this.reuniao = reuniao; // what about this input? 
	}
	
	public void setType(String type) {
		this.type = type; // what about this input? 
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public void setActionItem(String actionItem) {
		this.actionItem = actionItem;
	}
	
	public void setNewName(String newName) {
		this.newName = newName;
	}

	public void entraItem(){
    	
    	String variablesJavaScript = "<script language="+"javascript"+" type="+"text/javascript"+">"
    						+" var username='"+username+"'; "
    						+" var reuniao='"+reuniao+"'; "
    						+" var idItem='"+idItem+"'; "
    						+" var actionItem='"+actionItem+"'; ";
    	
    	/*String modificarItem = "<button type="+"submit"+" name="+"mudaNomeItem"+" value="+reuniao+"#"+actionItem+"#"+username+">Alterar</button>"+"<button type="+"submit"+" name="+"eliminaItem"+" value="+reuniao+"#"+actionItem+"#"+username+" >Apagar Item</button>";
    	String sairMenu = "<button type="+"submit"+" name="+"sairReuniao"+" value="+reuniao+"#"+username+">Sair do Chat</button>";*/
    	System.out.println(actionItem + " - " + idItem + " - " + reuniao);
    	String allChat = rmiCon.downloadChat(reuniao, actionItem);
    	
    	String[] chat = allChat.split("#");
    	String imprimeChat = "<br>";
    	
    	if(chat.length > 0){
    		for(int i = 0; i < chat.length; i++){
	    		imprimeChat += chat[i]+"<br><br>";
	    	}
    	}
    	
    	
    	
    	session.put("nomeAction", actionItem);
    	session.put("idAction", idItem);
    	session.put("username", username);
    	session.put("reuniao", reuniao);
    	session.put("ligar", variablesJavaScript);
    	//session.put("modificar", modificarItem);
    	session.put("chat", imprimeChat);
    	//session.put("sairMenu", sairMenu);
	}
}
