package rmiclient;


import rmiinterface.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClient {
	
	String naming /*= "//127.0.0.1:25055/calc"*/;
	RMIInterface ci;

	
	public RMIClient(String naming){
		this.naming = naming;

		try {
			ci = (RMIInterface) Naming.lookup(naming);
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

	public String checkLogin(String user, String pass) {
		
		String temp = null;

		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}

		
		try {
			temp = ci.checkLogin(user, pass);;
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.checkLogin(user, pass);;
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}
	
	public String marcaStandardReuniao(String nome, String objectivo, String data, String hora, String local, String admin, boolean votacao) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		
		try {
			temp = ci.marcaStandardReuniao(nome, objectivo, data, hora, local, admin, votacao);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.marcaStandardReuniao(nome, objectivo, data, hora, local, admin, votacao);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String consultaReunioes(String username) {
		
		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.consultaReunioes(username);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.consultaReunioes(username);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String entraReuniao(String nome) {
		
		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.entraReuniao(nome);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.entraReuniao(nome);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String mostraConvites(String username) {
		
		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.mostraConvites(username);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.mostraConvites(username);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String aceitarConvite(String username, String reuniao) {
		
		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.aceitarConvite(username, reuniao);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.aceitarConvite(username, reuniao);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String recusarConvite(String username, String reuniao) {
		
		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.recusarConvite(username, reuniao);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.recusarConvite(username, reuniao);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String convidarUsers(String answer) {
		
		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.convidarUsers(answer);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.convidarUsers(answer);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String marcaMutableReuniao(String answer, String data, String hora) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.marcaMutableReuniao(answer, data, hora);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.marcaMutableReuniao(answer, data, hora);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String criarDecisions(String answer) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.criarDecisions(answer);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.criarDecisions(answer);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String consultaDecisions(String reuniao) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.consultaDecisions(reuniao);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.consultaDecisions(reuniao);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String criarTarefas(String answer) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.criarTarefas(answer);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.criarTarefas(answer);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String consultarTarefas(String reuniao) {
		
		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.consultarTarefas(reuniao);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.consultarTarefas(reuniao);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String criaData(String answer) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.criarDataReuniao(answer);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.criarDataReuniao(answer);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String concluirTarefa(String answer) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.concluirTarefa(answer);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.concluirTarefa(answer);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String guardaChat(String reuniao, String action, String chat) {
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			ci.guardaChat(reuniao, action, chat);
		} catch (RemoteException e) {
			keepAlive();
			try {
				ci.guardaChat(reuniao, action, chat);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return null;
		
	}

	public String downloadChat(String reuniao, String action) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.downloadChat(reuniao, action);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.downloadChat(reuniao, action);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String consultarDatas(String reuniao) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.consultarDatas(reuniao);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.consultarDatas(reuniao);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String votarData(String reuniao, String data) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.votarData(reuniao, data);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.votarData(reuniao, data);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String registo(String username, String password) {
		
		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.resgisto(username, password);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.resgisto(username, password);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String criaActionItem(String reuniao, String actionItem) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.criaActionItem(reuniao, actionItem);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.criaActionItem(reuniao, actionItem);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String consultarActionItem(String reuniao) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.consultarActionItem(reuniao);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.consultarActionItem(reuniao);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String eliminarActionItem(String reuniao, String actionItem) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.eliminarActionItem(reuniao, actionItem);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.eliminarActionItem(reuniao, actionItem);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}

	public String modificarActionItem(String reuniao, String actionItem, String novo) {

		String temp = null;
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e2) {
			keepAlive();
		} catch (RemoteException e2) {
			keepAlive();
		} catch (NotBoundException e2) {
			keepAlive();
		}
		
		try {
			temp = ci.modificarActionItem(reuniao, actionItem, novo);
		} catch (RemoteException e) {
			keepAlive();
			try {
				temp = ci.modificarActionItem(reuniao, actionItem, novo);
			} catch (RemoteException e1) {
				System.out.println("Perdeu ligacao com RMI!");
				System.exit(0);
			}
		}
		
		return temp;
		
	}
	
	public void keepAlive () {
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			ci = (RMIInterface) Naming.lookup(naming);
		} catch (MalformedURLException e) {
			System.out.println("Ligação com o RMI perdida");
			System.exit(0);
		} catch (RemoteException e) {
			System.out.println("Ligação com o RMI perdida");
			System.exit(0);
		} catch (NotBoundException e) {
			System.out.println("Ligação com o RMI perdida");
			System.exit(0);
		}
		
	}

}
	
	
	