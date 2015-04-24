package rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
	
	public String checkLogin(String user, String pass) throws RemoteException;
	public String marcaStandardReuniao(String nome, String objectivo, String data, String hora, String local, String admin, boolean votacao) throws RemoteException;
	public String consultaReunioes(String username) throws RemoteException;
	public String entraReuniao(String nome) throws RemoteException;
	public String mostraConvites(String username) throws RemoteException;
	public String aceitarConvite(String username, String reuniao) throws RemoteException;
	public String recusarConvite(String username, String reuniao) throws RemoteException;
	public String convidarUsers(String answer) throws RemoteException;
	public String marcaMutableReuniao(String answer, String data, String hora) throws RemoteException;
	public String criarDecisions(String answer) throws RemoteException;
	public String consultaDecisions(String reuniao) throws RemoteException;
	public String criarTarefas(String answer) throws RemoteException;
	public String consultarTarefas(String reuniao) throws RemoteException;
	public String concluirTarefa(String answer) throws RemoteException;
	public void guardaChat(String reuniao, String action, String chat) throws RemoteException;
	public String downloadChat(String reuniao, String action) throws RemoteException;
	public String criarDataReuniao(String answer) throws RemoteException;
	public String consultarDatas(String reuniao) throws RemoteException;
	public String votarData(String reuniao, String data) throws RemoteException;
	public String resgisto(String username, String password) throws RemoteException;
	public String criaActionItem(String reuniao, String actionItem) throws RemoteException;
	public String consultarActionItem(String reuniao) throws RemoteException;
	public String eliminarActionItem(String reuniao, String actionItem) throws RemoteException;
	public String modificarActionItem(String reuniao, String actionItem, String novo) throws RemoteException;
    
}
