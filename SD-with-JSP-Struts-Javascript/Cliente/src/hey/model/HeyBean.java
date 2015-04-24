/**
 * Raul Barbosa 2014-11-07
 */
package hey.model;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import rmiclient.RMIClient;

public class HeyBean {
	private String naming = "//127.0.0.1:25055/calc";
	private RMIClient rmiCon;
	private String username; // username and password supplied by the user
	private String password;

	public HeyBean() throws MalformedURLException, RemoteException, NotBoundException {

		rmiCon = new RMIClient(naming);

	}

	public boolean getUserMatchesPassword() throws RemoteException {
		String temp = rmiCon.checkLogin(username, password);
		if(temp.equals("Sucesso")) return true;
		else return false;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
