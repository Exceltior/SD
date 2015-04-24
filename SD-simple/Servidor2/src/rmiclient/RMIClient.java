package rmiclient;


import rmiinterface.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClient {
	
	String naming = "//127.0.0.1:25055/calc";

	
	public RMIClient(){
		
	}

	public String checkLogin(String user, String pass) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.checkLogin(user, pass);
		
		return temp;
		
	}
	
	public String marcaStandardReuniao(String nome, String objectivo, String data, String hora, String local, String admin, boolean votacao) throws MalformedURLException, RemoteException, NotBoundException{
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.marcaStandardReuniao(nome, objectivo, data, hora, local, admin, votacao);
		
		return temp;
		
	}

	public String consultaReunioes(String username) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.consultaReunioes(username);
		
		return temp;
		
	}

	public String entraReuniao(String nome) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.entraReuniao(nome);
		
		return temp;
		
	}

	public String mostraConvites(String username) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.mostraConvites(username);
		
		return temp;
		
	}

	public String aceitarConvite(String username, String reuniao) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.aceitarConvite(username, reuniao);
		
		return temp;
		
	}

	public String recusarConvite(String username, String reuniao) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.recusarConvite(username, reuniao);
		
		return temp;
		
	}

	public String convidarUsers(String answer) throws MalformedURLException, RemoteException, NotBoundException {

		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.convidarUsers(answer);
		
		return temp;
		
	}

	public String marcaMutableReuniao(String answer) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.marcaMutableReuniao(answer);
		
		return temp;
		
	}

	public String criarDecisions(String answer) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.criarDecisions(answer);
		
		return temp;
		
	}

	public String consultaDecisions(String reuniao) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.consultaDecisions(reuniao);
		
		return temp;
		
	}

	public String criarTarefas(String answer) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.criarTarefas(answer);
		
		return temp;
		
	}

	public String consultarTarefas(String reuniao) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.consultarTarefas(reuniao);
		
		return temp;
		
	}

	public String criaData(String answer) throws MalformedURLException, RemoteException, NotBoundException {

		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.criarDataReuniao(answer);
		
		return temp;
		
	}

	public String concluirTarefa(String answer) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.concluirTarefa(answer);
		
		return temp;
		
	}

	public String guardaChat(String reuniao, String action, String chat) throws MalformedURLException, RemoteException, NotBoundException {

		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		ci.guardaChat(reuniao, action, chat);
		
		return null;
		
	}

	public String downloadChat(String reuniao, String action) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.downloadChat(reuniao, action);
		
		return temp;
		
	}

	public String consultarDatas(String reuniao) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.consultarDatas(reuniao);
		
		return temp;
		
	}

	public String votarData(String reuniao, String data) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.votarData(reuniao, data);
		
		return temp;
		
	}

	public String registo(String username, String password) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.resgisto(username, password);
		
		return temp;
		
	}

	public String criaActionItem(String reuniao, String actionItem) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.criaActionItem(reuniao, actionItem);
		
		return temp;
		
	}

	public String consultarActionItem(String reuniao) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.consultarActionItem(reuniao);
		
		return temp;
		
	}

	public String eliminarActionItem(String reuniao, String actionItem) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.eliminarActionItem(reuniao, actionItem);
		
		return temp;
		
	}

	public String modificarActionItem(String reuniao, String actionItem, String novo) throws MalformedURLException, RemoteException, NotBoundException {
		
		RMIInterface ci = (RMIInterface) Naming.lookup(naming);
		String temp = ci.modificarActionItem(reuniao, actionItem, novo);
		
		return temp;
		
	}

}
	
	
	