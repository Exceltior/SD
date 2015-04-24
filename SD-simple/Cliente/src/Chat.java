import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class Chat implements Runnable{
	
	Socket socket;
	boolean corre;
	String reuniao, username, ip;
	BufferedReader input;
	PrintWriter out;
	Scanner sc;
	int port;
	String action;
	
	public Chat(Socket socket, String reuniao, String username, BufferedReader input, PrintWriter out, String action) {
		this.reuniao = reuniao;
		this.username = username;
		this.input = input;
		this.out = out;
		this.action = action;
		new Thread(this, "").start();
	}
	
	@Override
	public synchronized void run() {
		
		corre = true;
		sc = new Scanner(System.in);
		
		String answer = null;
		
		//out.println("Download Chat#"+reuniao);
		
		try {
            answer = input.readLine();

        } catch (IOException e) {
                    // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		ArrayList< String > salaChat = new ArrayList< String >();
		
		String [] chatCompleto = answer.split("#");
		
		for(int i = 0; i < chatCompleto.length; i++){
			salaChat.add(chatCompleto[i]);
			System.out.println(chatCompleto[i]);
		}

		while(corre){
			if(corre != false){
			
				out.println("Download Chat#"+reuniao+"#"+action);
				
		        try {
		            answer = input.readLine();
		
		        } catch (IOException e) {
		                    // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		        
		        String [] temp = answer.split("#");
		        
		        if(!answer.equals("Falha") && !temp[0].equals("Falha")){
			        
			        for(int i = 0; i < temp.length; i++){
			        	int testa = 0;
			        	for(int j = 0; j < salaChat.size(); j++){
			        		if(temp[i].equals(salaChat.get(j))){
			        			testa = 1;
			        		}
			        	}
			        	if(testa == 0){
			        		salaChat.add(temp[i]);
			        		String [] aux = temp[i].split(":");
			        		if(!username.equals(aux[0]) && temp[i] != null){
			        			System.out.println(temp[i]);
			        		}
			        	}
			        }
		        }
			}
			else return;
		}
	}
	public void shutdown() {corre=false;}

}

