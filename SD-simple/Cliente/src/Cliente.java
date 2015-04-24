import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Cliente{
	
	Socket socket;
	BufferedReader input;
	PrintWriter out;
	int portServer2 = 9191;
	String ipServer2 = "127.0.0.1";
	
	Cliente(String ip, int port){
		
		int temp = 0;
		
		try {
			socket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			System.out.println("Erro desconhecido!");
		} catch (IOException e) {
			System.out.println("Servidor 1 indispon’vel. Vai ligar ao 2!");
			temp = 1;
		}
		
		if(temp == 1){
			
			try {
				socket = new Socket(ipServer2, portServer2);
			} catch (UnknownHostException e) {
				System.out.println("Erro desconhecido!");
			} catch (IOException e) {
				System.out.println("Servidor 2 indispon’vel. Erro de Servidores, vai desligar!");
				System.exit(0);
			}
			
		}
		
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		new ClienteComunicacao(socket, input, out, ip, port);
		
	}
	
	public static void main(String [] args){
			
		Scanner sc = new Scanner(System.in);
		
		System.out.print("IP: ");
		String ip = sc.nextLine();
		System.out.print("Port: ");
		int port = sc.nextInt();
		
		new Cliente(ip, port);

		
	
	}
	
}
