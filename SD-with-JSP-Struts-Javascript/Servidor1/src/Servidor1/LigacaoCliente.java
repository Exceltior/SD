package Servidor1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class LigacaoCliente implements Runnable{
	
	LigacaoCliente(){
		new Thread(this, "").start();
	}

	@Override
	public void run() {
		
		System.out.println("Servidor 1");
		
		ServerSocket serverTCP = null;
		try {
			serverTCP = new ServerSocket(9090);
		} catch (IOException e) {
			/*
			 * 
			 * Fail-Over
			 * 
			 * */
		}
		
		while(true){
			
			Socket socket = null;
			try {
				socket = serverTCP.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			new TrataCliente(serverTCP, socket);
			
		}
		
	}
	
	
	
}
