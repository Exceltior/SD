package rmiinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {
	
	public String checkLogin(String user, String pass) throws RemoteException;
	public String marcaStandardReuniao(String nome, String objectivo, String data, String hora, String local, String admin, boolean votacao) throws RemoteException;
	public String consultaReunioes(String username);
	public String entraReuniao(String nome);
	public String mostraConvites(String username);
	public String aceitarConvite(String username, String reuniao);
	public String recusarConvite(String username, String reuniao);
	public String convidarUsers(String answer);
	public String marcaMutableReuniao(String answer);
	public String criarDecisions(String answer);
	public String consultaDecisions(String reuniao);
	public String criarTarefas(String answer);
	public String consultarTarefas(String reuniao);
	public String concluirTarefa(String answer);
	public void guardaChat(String reuniao, String action, String chat);
	public String downloadChat(String reuniao, String action);
	public String criarDataReuniao(String answer);
	public String consultarDatas(String reuniao);
	public String votarData(String reuniao, String data);
	public String resgisto(String username, String password);
	public String criaActionItem(String reuniao, String actionItem);
	public String consultarActionItem(String reuniao);
	public String eliminarActionItem(String reuniao, String actionItem);
	public String modificarActionItem(String reuniao, String actionItem, String novo);
    
}
