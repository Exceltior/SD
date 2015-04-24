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


public class ReuniaoAction extends ActionSupport implements SessionAware {
	
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, reuniao = null, type = null, actionItem = null, decision = null, userTask = null, tarefa = null, userConvite = null;
	private String nomeReuniao = null, objectivo = null, local = null, data = null, hora = null, tarefaNova = null, toWho = null, decisionNova = null;
	private String naming = "//127.0.0.1:25055/calc";

	private RMIClient rmiCon;

	@Override
	public String execute() throws MalformedURLException, RemoteException, NotBoundException {
		
		rmiCon = new RMIClient(naming);
		
		if(this.username != null && !username.equals("") && type.equals("EntraReuniao")) {
			
			String reunioes = rmiCon.consultaReunioes(username);

			String[] temp = reunioes.split("#");
			
			session.put("reuniao", temp[Integer.parseInt(reuniao)-1]);
			System.out.println(temp[Integer.parseInt(reuniao)-1]);
			abreReuniao(temp[Integer.parseInt(reuniao)-1], username);
			
			return SUCCESS;
		
		}
		else if(this.type.equals("CriarItem")){
	    	
	    	/*String rmiAnswer = */rmiCon.criaActionItem(reuniao, actionItem);
	    	
	    	abreReuniao(reuniao, username);

			return SUCCESS;
		}
		else if(this.type.equals("CriarDecision")){
			
			rmiCon.criarDecisions("null#"+reuniao+"#"+decision);
			
			abreReuniao(reuniao, username);
			
			return SUCCESS;
		}
		else if(this.type.equals("CriarTarefa")){
			
			rmiCon.criarTarefas("null#"+reuniao+"#"+userTask+" - "+tarefa);
			
			abreReuniao(reuniao, username);
			
			return SUCCESS;
		}
		else if(this.type.equals("concluirTarefa")){
			
			String tasks = rmiCon.consultarTarefas(reuniao);
			
			String [] temp = tasks.split("#");
			
			rmiCon.concluirTarefa("null#"+reuniao+"#"+temp[Integer.parseInt(tarefa)-1]);
			
			abreReuniao(reuniao, username);
			
			return SUCCESS;
		}
		else if(this.type.equals("convidarUser")){
			
			rmiCon.convidarUsers("null#"+reuniao+"#"+userConvite);
			
			abreReuniao(reuniao, username);
			
			return SUCCESS;	
		}
		else if(this.type.equals("EliminarAction")){
			
			rmiCon.eliminarActionItem(reuniao, actionItem);
			abreReuniao(reuniao, username);
			return SUCCESS;
			
		}
		else if(this.type.equals("SairItem")){

			abreReuniao(reuniao, username);
			return SUCCESS;
			
		}
		else if(this.type.equals("CriarReuniao")){
			session.put("username", username);
			return SUCCESS;
		}
		else if(this.type.equals("RegistarReuniao")){
			
			if(!tarefaNova.equals("") && !toWho.equals("")) tarefaNova = toWho + " - " + tarefaNova;
			
			String decisionsTemp = null;
			
			if(!decisionNova.equals("")) decisionsTemp = decisionNova;
	    	
			String answer = "null#" + nomeReuniao + "#" + objectivo + "#" + local;
			
			if(tarefaNova != null && !tarefaNova.equals("")) answer += "#" + tarefaNova;
			else answer += "#null";
			if(decisionsTemp != null) answer += "#" + decisionsTemp;
			else answer += "#null";
			
			answer += "#" + username;
	    	
    		String resposta = rmiCon.marcaMutableReuniao(answer, data, hora);
    		
    		System.out.println(resposta);
    		
    		if(resposta.equals("Sucesso")){
    			
    			abreReuniao(nomeReuniao, username);
    			return SUCCESS;
	    	    
    		}
    		else{
    			
    			return LOGIN;
    			
    		}

		}
		else return LOGIN;
		
	}
	
	public void setNomeReuniao(String nomeReuniao) {
		this.nomeReuniao = nomeReuniao;
	}

	public void setObjectivo(String objectivo) {
		this.objectivo = objectivo;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public void setTarefaNova(String tarefaNova) {
		this.tarefaNova = tarefaNova;
	}

	public void setToWho(String toWho) {
		this.toWho = toWho;
	}

	public void setDecisionNova(String decisionNova) {
		this.decisionNova = decisionNova;
	}

	public void setNaming(String naming) {
		this.naming = naming;
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

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public void setUserTask(String userTask) {
		this.userTask = userTask;
	}

	public void setTarefa(String tarefa) {
		this.tarefa = tarefa;
	}

	public void setUserConvite(String userConvite) {
		this.userConvite = userConvite;
	}
	
	public void abreReuniao(String pedido, String username){
		
		String reuniaoinfo = rmiCon.entraReuniao(pedido);
    	String actionItems = rmiCon.consultarActionItem(pedido);
    	String keyDecisions = rmiCon.consultaDecisions(pedido);
    	String tasks = rmiCon.consultarTarefas(pedido);

    	String[] info = reuniaoinfo.split("#");
    	String[] listaItems = actionItems.split("#");
    	String[] listaDecisions = keyDecisions.split("#");
    	String[] listaTarefas = tasks.split("#");
    	
    	//String criaItem = "Nome Action: <s:input type="+"text"+" name="+"newAction"+"><s:button type="+"submit"+" name="+"criarAction"+" value="+info[0]+"#"+username+">Criar novo</s:button>";
    	//String criaDecision = "Key Decision: <s:input type="+"text"+" name="+"newDecision"+"><s:button type="+"submit"+" name="+"criarDecision"+" value="+info[0]+"#"+username+">Criar nova</s:button>";
    	//String criaTarefa = "Task: <s:input type="+"text"+" name="+"newTask"+"><input type="+"text"+" name="+"toWho"+"><s:button type="+"submit"+" name="+"criarTask"+" value="+info[0]+"#"+username+">Criar nova</s:button>";
    	//String sairMenu = "<s:button type="+"submit"+" name="+"sairMainMenu"+" value="+username+">Sair</s:button>";
    	String variablesJavaScript = "<script language="+"javascript"+" type="+"text/javascript"+">"
				+" var username='"+username+"'; "
				+" var reuniao='"+info[0]+"'; ";
    	
    	String itemsImprime = "";
    	String decisionsImprime = "";
    	String tarefasImprime = "";
    	String minhasTasks = "";
		
		for(int i = 0; i < listaItems.length; i++)
			if(!listaItems[i].equals("null") && !listaItems[i].equals("Sem Action Items"))
				itemsImprime += ("<li>"+(i+1)+" - "+listaItems[i]+"</li>");
		for(int i = 0; i < listaDecisions.length; i++)
			if(!listaDecisions[i].equals("null") && !listaDecisions[i].equals("Sem Key Decisions"))
				decisionsImprime += ("<li>"+(i+1)+" - "+listaDecisions[i]+"</li>");
		for(int i = 0; i < listaTarefas.length; i++)
			if(!listaTarefas[i].equals("null") && !listaTarefas[i].equals("Sem tarefas"))
				tarefasImprime += ("<li>"+(i+1)+" - "+listaTarefas[i]+"</li>");
		for(int i = 0; i < listaTarefas.length; i++)
			if(!listaTarefas[i].equals("null") && !listaTarefas[i].equals("Sem tarefas")){
				String[] temp = listaTarefas[i].split(" - ");
				if(temp[0].equals(username)){
					String[] aux = temp[temp.length-1].split(" ");
					if(aux[aux.length-1].equals("(Concluido)"))
						minhasTasks += ("<li>"+(i+1)+" - "+listaTarefas[i]+"</li>");
					else
						minhasTasks += ("<li>"+(i+1)+" - "+listaTarefas[i]+"</li>");
				}
			}
		
		if(itemsImprime.equals("")) itemsImprime = "<li> Sem Action Items </li>";
		if(decisionsImprime.equals("")) decisionsImprime = "<li> Sem Key Decisions </li>";
		if(tarefasImprime.equals("")) tarefasImprime = "<li> Sem Tarefas </li>";
		if(minhasTasks.equals("")) minhasTasks = "<li> Sem Tarefas </li>";
		
		//reuniao = info[0];
		
		session.put("listaItems", itemsImprime);
		session.put("listaDecisions", decisionsImprime);
		session.put("listaTarefas", tarefasImprime);
		session.put("minhasTarefas", minhasTasks);
		//session.put("criarItem", criaItem);
		//session.put("criarDecision", criaDecision);
		//session.put("criarTarefa", criaTarefa);
		session.put("nomeReuniao", info[0]);
    	session.put("nomeAdmin", info[1]);
    	session.put("nomeLocal", info[2]);
    	session.put("data", info[3]);
    	session.put("hora", info[4]);
    	session.put("tipo", info[5]);
    	session.put("objectivo", info[6]);
    	session.put("reuniao", pedido);
    	//session.put("sairMenu", sairMenu);
    	session.put("ligar", variablesJavaScript);
		
	}
}
