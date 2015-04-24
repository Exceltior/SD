package Servidor1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class PingServer implements Runnable{
	
	DatagramSocket clientSocket = null;
	InetAddress IPAddress = null;
	byte[] sendData = new byte[1024];     
	byte[] receiveData = new byte[1024];
	DatagramPacket sendPacket;
	DatagramPacket receivePacket;
	String stateSecond;

	PingServer(){
		new Thread(this, "").start();
	}

	@Override
	public void run() {
		
		ping();
		
		while(true){
			checkMessage();
			//System.out.println(stateSecond);
		}
		
	}
	
	public void ping(){
		
		
		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		
		
		try {
			IPAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
		
		String sentence = "teste";
		
		sendData = sentence.getBytes();      
		sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);  
		
		try {
			clientSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		receivePacket = new DatagramPacket(receiveData, receiveData.length);  
		try {
			clientSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		stateSecond = new String(receivePacket.getData());
		
	}
	
	public void checkMessage(){
		receivePacket = new DatagramPacket(receiveData, receiveData.length);  
		try {
			clientSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		stateSecond = new String(receivePacket.getData());
	}
	
}
