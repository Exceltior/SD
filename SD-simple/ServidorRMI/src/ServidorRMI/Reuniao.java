package ServidorRMI;

import java.util.ArrayList;

public class Reuniao {
	
	boolean votacao;
	ArrayList< Utilizadores > listaConvidados = new ArrayList< Utilizadores >();
	ArrayList< String > datasPropostas = new ArrayList< String >();
	ArrayList< String > salaChat = new ArrayList< String >();
	ArrayList< String > tasks = new ArrayList< String >();
	ArrayList< String > keyDecisions = new ArrayList< String >();
	ArrayList< ActionItem > actionItems = new ArrayList< ActionItem >();
	String nome, objectivo, data, hora, local, adminName;
	
	Reuniao(boolean votacao, String nome, String objectivo, String data, String hora, String local, String admin, String tasks, String keyDecisions, String datasPropostas, String actionItems){
		this.votacao = votacao;
		this.nome = nome;
		this.objectivo = objectivo;
		this.data = data;
		this.hora = hora;
		this.local = local;
		this.adminName = admin;
		
		if(votacao == true){
			String [] temp;
			
			if(tasks != null){
				temp = tasks.split("/");
				
				for(int i = 0; i < temp.length; i++){
					this.tasks.add(temp[i]);
				}
			}
			
			if(keyDecisions != null){
				temp = keyDecisions.split("/");
				
				for(int i = 0; i < temp.length; i++){
					this.keyDecisions.add(temp[i]);
				}
			}
			
			if(datasPropostas != null){
				temp = datasPropostas.split(";");
				
				for(int i = 0; i < temp.length; i++){
					this.datasPropostas.add(temp[i]);
				}
			}
			
			if(actionItems != null){
				
				temp = actionItems.split("/");
				
				for(int i = 0; i < temp.length; i++){
					ActionItem newAction = new ActionItem(temp[i]);
					this.actionItems.add(newAction);
				}
				
			}
			
		}
	}
	
	public void setDecision(String decision){
		keyDecisions.add(decision);
	}
	
	public void setNewMessage(String message){
		salaChat.add(message);
	}
	
	public void setNewData(String data){
		datasPropostas.add(data);
	}
	
	public void setTask(String task){
		tasks.add(task);
	}
	
	public int setTaskConcluir(String task){
		int temp = 0;

		for(int i = 0; i < tasks.size(); i++){
			if(tasks.get(i).equals(task)){
				task += " (Concluido)";
				tasks.remove(i);
				tasks.add(task);
				temp = 1;
			}
		}
		
		return temp;
	}

	public ArrayList< Utilizadores > getListaConvidados() {
		return listaConvidados;
	}

	public void setListaConvidados(ArrayList< Utilizadores > listaConvidados) {
		this.listaConvidados = listaConvidados;
	}
	
}
