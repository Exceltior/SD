package ServidorRMI;

import java.util.ArrayList;

public class Utilizadores {
	
	String user;
	String password;
	ArrayList< Reuniao > listaReunioes = new ArrayList< Reuniao >();
	ArrayList< Reuniao > convitesReunioes = new ArrayList< Reuniao >();
	
	Utilizadores(String user, String password){
		this.user = user;
		this.password = password;
	}
	
	public void setReuniao(Reuniao reuniao){
		listaReunioes.add(reuniao);
	}
	
	public void setConvite(Reuniao reuniao){
		convitesReunioes.add(reuniao);
	}
	
	public ArrayList< Reuniao > getConvites(){
		return convitesReunioes;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getPassword(){
		return password;
	}
	
	public ArrayList< Reuniao > getReunioes(){
		return listaReunioes;
	}

}
