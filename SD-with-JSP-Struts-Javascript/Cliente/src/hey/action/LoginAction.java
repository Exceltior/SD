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

import hey.model.HeyBean;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null, type = null, reuniao = null;
	private String naming = "//127.0.0.1:25055/calc";
	private RMIClient rmiCon;

	@Override
	public String execute() throws MalformedURLException, RemoteException, NotBoundException {
		
		rmiCon = new RMIClient(naming);
		
		if(this.type.equals("Login")){
			if(this.username != null && !username.equals("")) {
				this.getHeyBean().setUsername(this.username);
				this.getHeyBean().setPassword(this.password);
				boolean see = false;
				try {
					see = this.getHeyBean().getUserMatchesPassword();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(see == false) return LOGIN;
				
				abreMainMenu(username);
				
				String temp = "<form method="+"get"+" action="+"Servlet"+" id="+"myForm"+" >"+
									"<input type="+"hidden"+" name="+"entraMenu"+" value="+""+">"+
									"<input type="+"hidden"+" name="+"username"+" value="+username+">"+
								"</form>";
	
				session.put("form", temp);
				session.put("username", username);
				session.put("loggedin", see);
				
				return SUCCESS;
			}
			else
				return LOGIN;
		}
		else if(this.type.equals("Registo")){
			rmiCon.registo(username, password);
			abreMainMenu(username);
			return SUCCESS;
		}
		else if(this.type.equals("SairReuniao")){
			abreMainMenu(username);
			return SUCCESS;
		}
		else if(this.type.equals("aceitaConvite")){
			String reunioesTemp = rmiCon.mostraConvites(username);
			System.out.println(reunioesTemp);
			String[] temp = reunioesTemp.split("#");
			this.reuniao = temp[Integer.parseInt(reuniao)-1];
			rmiCon.aceitarConvite(username, reuniao);
			abreMainMenu(username);
			return SUCCESS;
		}
		else return LOGIN;
	}
	
	public void setReuniao(String reuniao) {
		this.reuniao = reuniao;
	}
	
	public void setUsername(String username) {
		this.username = username; // will you sanitize this input? maybe use a prepared statement?
	}

	public void setPassword(String password) {
		this.password = password; // what about this input? 
	}
	
	public HeyBean getHeyBean() throws MalformedURLException, RemoteException, NotBoundException {
		if(!session.containsKey("heyBean"))
			this.setHeyBean(new HeyBean());
		return (HeyBean) session.get("heyBean");
	}

	public void setHeyBean(HeyBean heyBean) {
		this.session.put("heyBean", heyBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void abreMainMenu(String username){
		
		String reunioes = rmiCon.consultaReunioes(username);
		String convites = rmiCon.mostraConvites(username);
		
		String[] temp = reunioes.split("#");
		String[] aux = convites.split("#");
		
		int isThere = 0;
		
		if(aux.length<1 || aux[0].equals(null) || aux[0].equals("Sem convites")) isThere = 0;
		else isThere = 1;
		
		String variablesJavaScript = "<script language="+"javascript"+" type="+"text/javascript"+">"
				+" var username='"+username+"'; "
				+" var isThere='"+isThere+"'; ";
		
		String reunioesImprime = "";
		String convitesImprime = "";
		
		for(int i = 0; i < temp.length; i++){
			if(!temp[i].equals("Sem reunioes")){
				reunioesImprime += ("<li>"+(i+1)+" - "+temp[i]+"</li>");
			}
		}
		
		if(reunioesImprime.equals("")){
			reunioesImprime = "<li>Sem Reunioes</li>";
		}
		
		for(int i = 0; i < aux.length; i++){
			if(!aux[i].equals("Sem convites"))
				convitesImprime += ("<li>" +(i+1)+" - "+aux[i]+"</li><br>");
		}
		
		if(convitesImprime.equals("")){
			convitesImprime = "<li>Sem Convites</li>";
		}
		
		session.put("message", reunioesImprime);
		session.put("convites", convitesImprime);
		session.put("ligar", variablesJavaScript);
		
	}
}
