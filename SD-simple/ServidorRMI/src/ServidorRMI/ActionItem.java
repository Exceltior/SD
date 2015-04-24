package ServidorRMI;

import java.util.ArrayList;

public class ActionItem {
	
	String nome;
	ArrayList< String > salaChat = new ArrayList< String >();
	
	ActionItem(String nome){
		this.nome = nome;
	}
	
	public void setNewMessage(String message){
		salaChat.add(message);
	}
	
}
