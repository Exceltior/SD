package ServidorRMI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import rmiinterface.RMIInterface;


public class RMIServer extends UnicastRemoteObject implements RMIInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static BufferedReader bufferedReader;
	static ArrayList< Utilizadores > listaUsers = new ArrayList< Utilizadores >();
	static ArrayList< Reuniao > listaReunioes = new ArrayList< Reuniao >();

	protected RMIServer() throws RemoteException {
		super();
	}
	
	public static void main(String [] args) throws IOException{
			
		RMIInterface ci = new RMIServer();
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        try {
			LocateRegistry.createRegistry(25055).rebind("calc", ci);
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Servidor RMI ligado!");
        
        loadInfo();
			
	}
	
	private static void loadInfo() throws IOException {
		
		StringBuilder stringBuilder;
		FileReader fileReader;
		File file;
		String path;
		String fileTemp;
		String line;
		
		
		/*
		 * 
		 *		LER REUNIOES 
		 * 
		 * */
		
		fileTemp = "reunioes.txt";
		
		stringBuilder = new StringBuilder();
		//stringBuilder.append("/Users/miguelpveloso/Desktop/SD Project/ServidorRMI/src/ServidorRMI/");
		stringBuilder.append(fileTemp);
		
		path = stringBuilder.toString();
			
		file = new File(path);
		
		fileReader = new FileReader(file);
		bufferedReader = new BufferedReader(fileReader);
		
		boolean aux = false;
		
		while ((line = bufferedReader.readLine()) != null) {
			
			String[] temp = line.split("#");
			
			if(temp[1].equals("true")){
				aux = true;
			}
			if(temp[1].equals("false")){
				aux = false;
			}
			
			Reuniao temp2 = new Reuniao(aux, temp[0], temp[3], temp[4], temp[5], temp[6], temp[2], temp[7], temp[8], temp[9], temp[10]);
			listaReunioes.add(temp2);
			
		}
		
		System.out.println("Load de reunioes completo!");
		
		/*
		 * 
		 *		LER USERS 
		 * 
		 * */
		
		fileTemp = "users.txt";
		
		stringBuilder = new StringBuilder();
		//stringBuilder.append("/Users/miguelpveloso/Desktop/SD Project/ServidorRMI/src/ServidorRMI/");
		stringBuilder.append(fileTemp);
		
		path = stringBuilder.toString();
			
		file = new File(path);
		
		fileReader = new FileReader(file);
		bufferedReader = new BufferedReader(fileReader);
		
		
		while ((line = bufferedReader.readLine()) != null) {
			
			String[] temp = line.split("#");
			Utilizadores Useraux = new Utilizadores(temp[0], temp[1]);
			
			for(int i = 2; i < temp.length; i++){
				
				for(int j = 0; j < listaReunioes.size(); j++){
					
					if(listaReunioes.get(j).nome.equals(temp[i]))
						Useraux.setConvite(listaReunioes.get(j));

				}
				
			}
			
			listaUsers.add(Useraux);
			
		}
		
		System.out.println("Load de users completo!");
		
		/*
		 * 
		 *		LER CHATS 
		 * 
		 * */
		
		fileTemp = "chats.txt";
		
		stringBuilder = new StringBuilder();
		//stringBuilder.append("/Users/miguelpveloso/Desktop/SD Project/ServidorRMI/src/ServidorRMI/");
		stringBuilder.append(fileTemp);
		
		path = stringBuilder.toString();
			
		file = new File(path);
		
		fileReader = new FileReader(file);
		bufferedReader = new BufferedReader(fileReader);
		
		while ((line = bufferedReader.readLine()) != null) {
			
			String[] temp = line.split("#");
			
			ArrayList< String > salaChat = new ArrayList< String >();
			
			for(int i = 2; i < temp.length; i++){
					salaChat.add(temp[i]);
			}
			
			for(int j = 0; j < listaReunioes.size(); j++){
				if(listaReunioes.get(j).nome.equals(temp[0])){
					for(int x = 0; x < listaReunioes.get(j).actionItems.size(); x++){
						if(listaReunioes.get(j).actionItems.get(x).nome.equals(temp[1])){
							listaReunioes.get(j).actionItems.get(x).salaChat = salaChat;
						}
					}
				}
			}
			
			System.out.println("Load de chat por reuniao completo!");
			
		}
		
		/*
		 * 
		 *		LER UTILIZADORES POR REUNIAO
		 * 
		 * */
		
		fileTemp = "reunioesUsers.txt";
		
		stringBuilder = new StringBuilder();
		//stringBuilder.append("/Users/miguelpveloso/Desktop/SD Project/ServidorRMI/src/ServidorRMI/");
		stringBuilder.append(fileTemp);
		
		path = stringBuilder.toString();
			
		file = new File(path);
		
		fileReader = new FileReader(file);
		bufferedReader = new BufferedReader(fileReader);
		
		while ((line = bufferedReader.readLine()) != null) {
			
			String[] temp = line.split("#");
			Reuniao temp2 = null;
			int i;
			
			for(i = 0; i < listaReunioes.size(); i++){
				if(listaReunioes.get(i).nome.equals(temp[0])){
					temp2 = listaReunioes.get(i);
					break;
				}
			}
			
			ArrayList< Utilizadores > convidados = new ArrayList< Utilizadores >();
			
			for(int x = 1; x < temp.length; x++){
				for(int j = 0; j < listaUsers.size(); j++){
					if(temp[x].equals(listaUsers.get(j).user)){
						listaUsers.get(j).setReuniao(temp2);
						convidados.add(listaUsers.get(j));
					}
				}
			}
			
			listaReunioes.get(i).setListaConvidados(convidados);
			
		}
		
		System.out.println("Load de users por reuniao completo!");


		
	}
	
	public void saveInfo() throws IOException {
		
		PrintWriter writer;
		
		/*
		 * 
		 * GRAVAR REUNIOES
		 * 
		 * */
		
		writer = new PrintWriter("reunioes.txt", "UTF-8");
		
		for(int i = 0; i < listaReunioes.size(); i++){
			
			String build;
			
			build = listaReunioes.get(i).nome;
			
			if(listaReunioes.get(i).votacao == true)
				build += "#true";
			else
				build += "#false";
			
			build += ("#" + listaReunioes.get(i).adminName);
			build += ("#" + listaReunioes.get(i).objectivo);
			
			if(listaReunioes.get(i).votacao == true){
				
				build += "#null#null";
				build += ("#" + listaReunioes.get(i).local + "#");
				for(int j = 0; j < listaReunioes.get(i).tasks.size(); j++){
					if(j == 0) build += listaReunioes.get(i).tasks.get(j);
					else build += ("/" + listaReunioes.get(i).tasks.get(j));
				}
				if(listaReunioes.get(i).tasks.size() == 0) build += "null";
				build += "#";
				for(int j = 0; j < listaReunioes.get(i).keyDecisions.size(); j++){
					if(j == 0) build += listaReunioes.get(i).keyDecisions.get(j);
					else build += ("/" + listaReunioes.get(i).keyDecisions.get(j));
				}
				if(listaReunioes.get(i).keyDecisions.size() == 0) build += "null";
				build += "#";
				for(int j = 0; j < listaReunioes.get(i).datasPropostas.size(); j++){
					if(j == 0) build += listaReunioes.get(i).datasPropostas.get(j);
					else build += (";" + listaReunioes.get(i).datasPropostas.get(j));
				}
				if(listaReunioes.get(i).datasPropostas.size() == 0) build += "null";
				build += "#";
				for(int j = 0; j < listaReunioes.get(i).actionItems.size(); j++){
					if(j == 0) build += listaReunioes.get(i).actionItems.get(j).nome;
					else build += ("/" + listaReunioes.get(i).actionItems.get(j).nome);
				}
				if(listaReunioes.get(i).actionItems.size() == 0) build += "null";
				
			}
			else{
				
				build += ("#" + listaReunioes.get(i).data);
				build += ("#" + listaReunioes.get(i).hora);
				build += ("#" + listaReunioes.get(i).local);
				build += "#null#null#null#null";
				
			}
			
			writer.println(build);
			
		}
		
		writer.close();
		
		/*
		 * 
		 * GRAVAR USERS
		 * 
		 * */
		
		writer = new PrintWriter("users.txt", "UTF-8");
		
		for(int i = 0; i < listaUsers.size(); i++){
			
			String build;
			
			build = listaUsers.get(i).user + "#" + listaUsers.get(i).password;
			
			for(int j = 0; j < listaUsers.get(i).convitesReunioes.size(); j++){
				build += ("#" + listaUsers.get(i).convitesReunioes.get(j).nome);
			}
			
			writer.println(build);
			
		}
		
		writer.close();
		
		/*
		 * 
		 * GRAVAR CHATS
		 * 
		 * */
		
		writer = new PrintWriter("chats.txt", "UTF-8");
		
		for(int i = 0; i < listaReunioes.size(); i++){
			
			String build;
			
			for(int j = 0; j < listaReunioes.get(i).actionItems.size(); j++){
				
				build = listaReunioes.get(i).nome + "#" + listaReunioes.get(i).actionItems.get(j).nome;
				
				for(int x = 0; x < listaReunioes.get(i).actionItems.get(j).salaChat.size(); x++){
					build += ("#" + listaReunioes.get(i).actionItems.get(j).salaChat.get(x));
				}
				
				writer.println(build);
				
			}
			
		}
		
		writer.close();
		
		/*
		 * 
		 * GRAVAR UTILIZADORES P/REUNIAO
		 * 
		 * */
		
		writer = new PrintWriter("reunioesUsers.txt", "UTF-8");
		
		for(int i = 0; i < listaReunioes.size(); i++){
			
			String build;
			
			build = listaReunioes.get(i).nome;
			
			for(int j = 0; j < listaReunioes.get(i).listaConvidados.size(); j++){
				build += ("#" + listaReunioes.get(i).listaConvidados.get(j).user);
			}
			
			writer.println(build);
			
		}

		writer.close();
		
	}
	
	public String marcaStandardReuniao(String nome, String objectivo, String data, String hora, String local, String admin, boolean votacao) throws RemoteException{
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(nome)){
				return "Falha";
			}
		}
		
		Reuniao nova = new Reuniao(votacao, nome, objectivo, data, hora, local, admin, null, null, null, null);
		
		for(int i = 0; i < listaUsers.size(); i++){
			if(listaUsers.get(i).user.equals(admin)){
				nova.listaConvidados.add(listaUsers.get(i));
				listaUsers.get(i).setReuniao(nova);
			}
		}
		
		listaReunioes.add(nova);
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Sucesso";
		
	}
	
	public String marcaMutableReuniao(String pedido) throws RemoteException{
		
		String [] temp = pedido.split("#");
		
		String nome = temp[1];
		String objectivo = temp[2];
		String local = temp[3];
		String tasks = temp[4];
		String keyDecisions = temp[5];
		String admin = temp[6];
		
		if(temp[6].equals("true"))
			
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(nome)){
				return "Falha";
			}
		}

		Reuniao nova = new Reuniao(true, nome, objectivo, null, null, local, admin, tasks, keyDecisions, null, null);
		
		for(int i = 0; i < listaUsers.size(); i++){
			if(listaUsers.get(i).user.equals(admin)){
				nova.listaConvidados.add(listaUsers.get(i));
				listaUsers.get(i).setReuniao(nova);
			}
		}
		
		listaReunioes.add(nova);
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Sucesso";
		
	}
	
	public String criaActionItem(String reuniao, String actionItem) throws RemoteException{

		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(reuniao)){
				for(int j = 0; j < listaReunioes.get(i).actionItems.size(); j++){
					if(listaReunioes.get(i).actionItems.get(j).nome.equals(actionItem)) return "Falha";
				}
				ActionItem newAction = new ActionItem(actionItem);
				listaReunioes.get(i).actionItems.add(newAction);
			}
		}
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Sucesso";
		
	}
	
	public String consultarActionItem(String reuniao) throws RemoteException{
		
		String resposta = null;
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(reuniao)){
				for(int j = 0; j < listaReunioes.get(i).actionItems.size(); j++){
					if(j == 0) resposta = listaReunioes.get(i).actionItems.get(j).nome;
					else resposta += ("#"+listaReunioes.get(i).actionItems.get(j).nome);
				}
				if(listaReunioes.get(i).actionItems.size() == 0) return "Sem Action Items";
			}
		}
		
		return resposta;
		
	}
	
	public String eliminarActionItem(String reuniao, String actionItem) throws RemoteException{
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(reuniao)){
				for(int j = 0; j < listaReunioes.get(i).actionItems.size(); j++){
					if(listaReunioes.get(i).actionItems.get(j).nome.equals(actionItem)){
						listaReunioes.get(i).actionItems.remove(j);
						return "Sucesso";
					}
				}
			}
		}
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Falha";
		
	}
	
	public String modificarActionItem(String reuniao, String actionItem, String novo) throws RemoteException{
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(reuniao)){
				for(int j = 0; j < listaReunioes.get(i).actionItems.size(); j++){
					if(listaReunioes.get(i).actionItems.get(j).nome.equals(actionItem)){
						listaReunioes.get(i).actionItems.get(j).nome = novo;
						return "Sucesso";
					}
				}
			}
		}
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Falha";
		
	}
	
	public String criarDecisions(String pedido) throws RemoteException{
		
		String [] temp = pedido.split("#");
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(temp[1])){
				for(int j = 2; j < temp.length; j++){
					listaReunioes.get(i).setDecision(temp[j]);
				}
			}
		}
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Sucesso";
	}
	
	public String consultaDecisions(String reuniao) throws RemoteException{
		
		String keyDecisions = null;
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(reuniao)){
				for(int j = 0; j < listaReunioes.get(i).keyDecisions.size(); j++){
					if(j == 0) keyDecisions = listaReunioes.get(i).keyDecisions.get(0);
					else keyDecisions += ("#"+listaReunioes.get(i).keyDecisions.get(j));
				}
				if(listaReunioes.get(i).keyDecisions.size() == 0) return "Sem Key Decisions";
			}
		}
		
		return keyDecisions;
	}
	
	public String criarTarefas(String pedido) throws RemoteException{
		
		String [] temp = pedido.split("#");
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(temp[1])){
				for(int j = 2; j < temp.length; j++){
					listaReunioes.get(i).setTask(temp[j]);
				}
			}
		}
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Sucesso";
	}
	
	public String criarDataReuniao(String pedido) throws RemoteException{
		
		String [] temp = pedido.split("#");
		
		String [] aux2 = temp[2].split(" - ");
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(temp[1])){
				for(int j = 0; j < listaReunioes.get(i).datasPropostas.size(); j++){
					String [] aux = listaReunioes.get(i).datasPropostas.get(j).split(" - ");
					if(aux[0].equals(aux2[0])){
						return "Falha";
					}
				}
				listaReunioes.get(i).setNewData(temp[2]);
			}
		}
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Sucesso";
	}
	
	public String consultarDatas(String reuniao) throws RemoteException{
		String answer = null;
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(reuniao)){
				for(int j = 0; j < listaReunioes.get(i).datasPropostas.size(); j++){
					if(j == 0) answer = listaReunioes.get(i).datasPropostas.get(0);
					else answer += ("#"+listaReunioes.get(i).datasPropostas.get(j));
				}
				if(listaReunioes.get(i).datasPropostas.size() == 0) return "Sem datas propostas";
			}
		}
		
		return answer;
	}
	
	public String votarData(String reuniao, String data) throws RemoteException{
		String [] temp = data.split(" - ");
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(reuniao)){
				for(int j = 0; j < listaReunioes.get(i).datasPropostas.size(); j++){
					String [] aux = listaReunioes.get(i).datasPropostas.get(j).split(" - ");
					if(temp[0].equals(aux[0])){
						int temp2 = Integer.parseInt(aux[1]);
						temp2++;
						String build = aux[0] + " - " + temp2;
						listaReunioes.get(i).datasPropostas.remove(j);
						listaReunioes.get(i).datasPropostas.add(build);
						return "Sucesso";
					}
				}
			}
		}
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Falha";
	}
	
	public String consultarTarefas(String reuniao) throws RemoteException{
		
		String tasks = null;
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(reuniao)){
				for(int j = 0; j < listaReunioes.get(i).tasks.size(); j++){
					if(j == 0) tasks = listaReunioes.get(i).tasks.get(0);
					else tasks += ("#"+listaReunioes.get(i).tasks.get(j));
				}
				if(listaReunioes.get(i).tasks.size() == 0) return "Sem tarefas";
			}
		}
		
		return tasks;
	}
	
	public String concluirTarefa(String answer) throws RemoteException{
	
		String [] aux = answer.split("#"); 
		
		int temp = 0;

		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(aux[1])){
				temp = listaReunioes.get(i).setTaskConcluir(aux[2]);
			}
		}
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		if(temp == 1)
			return "Sucesso";
		else return "Falha";
		
	}
	
	public String convidarUsers(String pedido) throws RemoteException{
		
		String [] temp = pedido.split("#");
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(temp[1])){
				Reuniao aux = listaReunioes.get(i);
				
				for(int x = 2; x < temp.length; x++){
					
					for(int j = 0; j < listaUsers.size(); j++){
						if(listaUsers.get(j).user.equals(temp[x])){
							listaUsers.get(j).setConvite(aux);
							
						}
					}
					
				}
			}
		}
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Sucesso";
		
	}
	
	public String aceitarConvite(String username, String reuniao) throws RemoteException{
		
		for(int i = 0; i < listaUsers.size(); i++){
			if(listaUsers.get(i).user.equals(username)){
				for(int j = 0; j < listaUsers.get(i).convitesReunioes.size(); j++){
					if(listaUsers.get(i).convitesReunioes.get(j).nome.equals(reuniao)){
						
						listaUsers.get(i).convitesReunioes.remove(j);
						
						for(int x = 0; x < listaReunioes.size(); x++){
							if(listaReunioes.get(x).nome.equals(reuniao)){
								Reuniao aux = listaReunioes.get(x);
								aux.listaConvidados.add(listaUsers.get(i));
								listaUsers.get(i).setReuniao(aux);
							}
						}
						
					}
				}
			}
		}
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Sucesso";
		
	}
	
	public String recusarConvite(String username, String reuniao) throws RemoteException{
		
		for(int i = 0; i < listaUsers.size(); i++){
			if(listaUsers.get(i).user.equals(username)){
				for(int j = 0; j < listaUsers.get(i).convitesReunioes.size(); j++){
					if(listaUsers.get(i).convitesReunioes.get(j).nome.equals(reuniao)){
						
						listaUsers.get(i).convitesReunioes.remove(j);
						
					}
				}
			}
		}
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Sucesso";
		
	}
	
	public String consultaReunioes(String username) throws RemoteException{
		
		ArrayList< Reuniao > temp = null;
		String reunioes = null;
		
		for(int i = 0; i < listaUsers.size(); i++){
			if(listaUsers.get(i).user.equals(username)){
				temp = listaUsers.get(i).getReunioes();
			}
		}
		
		for(int i = 0; i < temp.size(); i++){
			if(i == 0) reunioes = temp.get(i).nome;
			else reunioes += ("#"+temp.get(i).nome);
		}
		
		if(temp.size() == 0) return "Sem reunioes"; 
		
		return reunioes;
		
	}
	
	public String mostraConvites(String username) throws RemoteException{
		
		ArrayList< Reuniao > temp = null;
		String reunioes = "";
		
		for(int i = 0; i < listaUsers.size(); i++){
			if(listaUsers.get(i).user.equals(username)){
				temp = listaUsers.get(i).getConvites();
			}
		}
		
		for(int i = 0; i < temp.size(); i++){
			if(i == 0) reunioes += temp.get(i).nome;
			else reunioes += ("#"+temp.get(i).nome);
		}
		
		if(temp.size() == 0) return "Sem convites";
		
		return reunioes;
		
	}
	
	public String entraReuniao(String nome) throws RemoteException{
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(nome)){
				
				String bool;
				if(listaReunioes.get(i).votacao == true) bool = "true";
				else bool = "false";
				
				return listaReunioes.get(i).nome + "#" +listaReunioes.get(i).adminName + "#" +listaReunioes.get(i).local + "#" +listaReunioes.get(i).data  + "#" + listaReunioes.get(i).hora  + "#" + bool + "#" + listaReunioes.get(i).objectivo;
			}
		}
		
		return "Falha";
		
	}
	
	public String checkLogin(String user, String pass) throws RemoteException{
		
		for(int i = 0; i < listaUsers.size(); i++){
			if(user.equals(listaUsers.get(i).getUser())){
				if(pass.equals(listaUsers.get(i).getPassword())){
					return "Sucesso";
				}
				else return "Falha";
			}
		}
		
		return "Falha";
		
	}
	
	public String resgisto(String username, String password) throws RemoteException{
		
		for(int i = 0; i < listaUsers.size(); i++){
			if(username.equals(listaUsers.get(i).getUser())){
				return "Falha";
			}
		}
		
		Utilizadores newUser = new Utilizadores(username, password);
		
		listaUsers.add(newUser);
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
		return "Sucesso";
		
	}
	
	public void guardaChat(String reuniao, String action, String chat) throws RemoteException{
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(reuniao)){
				for(int j = 0; j < listaReunioes.get(i).actionItems.size(); j++){
					if(listaReunioes.get(i).actionItems.get(j).nome.equals(action)){
						listaReunioes.get(i).actionItems.get(j).setNewMessage(chat);
					}
				}
			}
		}
		
		try {
			saveInfo();
		} catch (IOException e) {
			System.out.println("ERRO A GRAVAR DADOS!");
		}
		
	}
	
	public String downloadChat(String reuniao, String action) throws RemoteException{
		
		for(int i = 0; i < listaReunioes.size(); i++){
			if(listaReunioes.get(i).nome.equals(reuniao)){
				for(int x = 0; x < listaReunioes.get(i).actionItems.size(); x++){
					if(listaReunioes.get(i).actionItems.get(x).nome.equals(action)){
						String chat = null;
						for(int j = 0; j < listaReunioes.get(i).actionItems.get(x).salaChat.size(); j++){
							if(j == 0) chat = listaReunioes.get(i).actionItems.get(x).salaChat.get(0);
							else chat += "#" + listaReunioes.get(i).actionItems.get(x).salaChat.get(j);
						}
						if(listaReunioes.get(i).actionItems.get(x).salaChat.size() == 0) return "Sem Conversa!";
						return chat;
					}
				}
			}
		}
		
		return null;
		
	}

}
