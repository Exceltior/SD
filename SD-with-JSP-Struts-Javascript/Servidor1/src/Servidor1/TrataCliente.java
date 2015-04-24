package Servidor1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmiclient.RMIClient;


public class TrataCliente implements Runnable{
	
	ServerSocket serverTCP;
	Socket socket;
	
	TrataCliente(ServerSocket serverTCP, Socket socket){
		this.serverTCP = serverTCP;
		this.socket = socket;
		new Thread(this, "").start();
	}
	
	@Override
	public void run() {
		
		System.out.println("Cliente conectou-se!");
		
		BufferedReader input = null;
		
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PrintWriter out = null;
		
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String answer;
		
		while (true) {
			
			try {
				answer = input.readLine();
			} 
			catch (IOException e) {
				System.out.println("Cliente desconectou-se!");
				break;
			}
			
			if(answer != null)
				answer = Interpreta(answer);
			else{
				System.out.println("Cliente desconectou-se!");
				break;
			}
			
			if(answer != null)
				out.println(answer);
			
        }
		
		// TODO Auto-generated method stub
		
	}

	private String Interpreta(String answer) {
		
		RMIClient rmiconn = new RMIClient();
		String [] temp = answer.split("#");
		String RMIanswer = null;
		
		if(temp[0].equals("Login")){
		
			try {
				RMIanswer = rmiconn.checkLogin(temp[1], temp[2]);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(temp[0].equals("Registo")){
			
			try {
				RMIanswer = rmiconn.registo(temp[1], temp[2]);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Standard Reuniao")){
			
			try {
				RMIanswer = rmiconn.marcaStandardReuniao(temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], false);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Consultar Reunioes")){
		
			try {
				RMIanswer = rmiconn.consultaReunioes(temp[1]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Entra Reuniao")){
			
			try {
				RMIanswer = rmiconn.entraReuniao(temp[1]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Ver Convites")){
			
			try {
				RMIanswer = rmiconn.mostraConvites(temp[1]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Aceitar Convite")){
			
			try {
				RMIanswer = rmiconn.aceitarConvite(temp[1], temp[2]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Recusar Convite")){
			
			try {
				RMIanswer = rmiconn.recusarConvite(temp[1], temp[2]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Convidar")){
			
			try {
				RMIanswer = rmiconn.convidarUsers(answer);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Mutable Reuniao")){
			
			try {
				RMIanswer = rmiconn.marcaMutableReuniao(answer);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Criar Decisions")){
			
			try {
				RMIanswer = rmiconn.criarDecisions(answer);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Consulta Decisions")){
			
			try {
				RMIanswer = rmiconn.consultaDecisions(temp[1]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Criar Tarefas")){
			
			try {
				RMIanswer = rmiconn.criarTarefas(answer);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Consulta Tarefas")){
			
			try {
				RMIanswer = rmiconn.consultarTarefas(temp[1]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Tarefa Concluida")){
			
			try {
				RMIanswer = rmiconn.concluirTarefa(answer);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Criar Data")){
			
			try {
				RMIanswer = rmiconn.criaData(answer);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Chat")){
			
			try {
				RMIanswer = rmiconn.guardaChat(temp[1], temp[2], temp[3]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Download Chat")){
			
			try {
				RMIanswer = rmiconn.downloadChat(temp[1], temp[2]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Consultar Datas")){
			
			try {
				RMIanswer = rmiconn.consultarDatas(temp[1]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Votar Data")){
			
			try {
				RMIanswer = rmiconn.votarData(temp[1], temp[2]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Adicionar Action Item")){
			
			try {
				RMIanswer = rmiconn.criaActionItem(temp[1], temp[2]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Consultar Action Item")){
			
			try {
				RMIanswer = rmiconn.consultarActionItem(temp[1]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Eliminar Action Item")){
			
			try {
				RMIanswer = rmiconn.eliminarActionItem(temp[1], temp[2]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(temp[0].equals("Modificar Action Item")){
			
			try {
				RMIanswer = rmiconn.modificarActionItem(temp[1], temp[2], temp[3]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return RMIanswer;
		
	}

}
