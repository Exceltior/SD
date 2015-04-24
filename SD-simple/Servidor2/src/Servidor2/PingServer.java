package Servidor2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class PingServer implements Runnable {
	
	byte[] sendData = new byte[1024];
	int port;
	InetAddress IPAddress;
	DatagramSocket serverSocket = null;
	DatagramPacket sendPacket;
	
	PingServer(){
		new Thread(this, "").start();
	}
	
	@Override
	public void run() {
		
		ping();
		
		while(true){
			sendMessage();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void ping(){
		
		try {
			serverSocket = new DatagramSocket(9876);
		} catch (SocketException e1) {
			e1.printStackTrace();
		} 
		
		byte[] receiveData = new byte[1024];               
			
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);      
		try {
			serverSocket.receive(receivePacket);
		} catch (IOException e) {
			e.printStackTrace();
		}            
	                      
		IPAddress = receivePacket.getAddress();        
		port = receivePacket.getPort();                
		String capitalizedSentence = "Segundo servidor ligado!";        
		sendData = capitalizedSentence.getBytes();                
		sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);    
		
		try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendMessage(){
		
		sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);    
		
		try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
