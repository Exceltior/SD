import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;


public class ClienteComunicacao implements Runnable{
	
	Socket socket;
	PrintWriter out;
	BufferedReader input;
	Scanner sc;
	Object full, empty;
	String mensagemServidor, username, ip, ipServer2 = "192.168.1.71";
	int logout = 0, port, portServer2 = 9191;

	ClienteComunicacao(Socket socket, BufferedReader input, PrintWriter out, String ip, int port) {
		this.socket = socket;
		this.input = input;
		this.out = out;
		this.ip = ip;
		this.port = port;
		new Thread(this, "").start();
	}

	@Override
	public void run(){
		
		sc = new Scanner(System.in);
		String resposta = "Falha";
		
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		full  = new Object();
	    empty = new Object();
		
		while(resposta.equals("Falha")){
			
			int op = menuEntrada();
			
			String login = null;
			String registo = null;
			
			
			if(op == 1) login = menuLogin();
			else if(op == 2) registo = menuRegisto();
			else if(op == 0) System.exit(0);
			
			if(op == 1) out.println(login);
			if(op == 2) out.println(registo);
			
			try {
	            resposta = input.readLine();
	
	        } catch (IOException e) {
	        	if(op == 1)
	        		resposta = mantemLigacao(login);
	        	if(op == 2)
	        		resposta = mantemLigacao(registo);
	        }
		
		}
		
		if(resposta.equals("Sucesso")){
			
			while(logout == 0){
				menuPrincipal();
			}
			
			System.out.println("\nAte a proxima!");
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
		
	}
	
	private String mantemLigacao(String pedido){
		
		String resposta = null;
		int temp = 0;
		
		for(int i = 1; i < 6; i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(i);
		}
		
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
		
		out.println(pedido);
		
		try {
            resposta = input.readLine();

        } catch (IOException e) {
        	resposta = mantemLigacao(pedido);
        }
		
		return resposta;
	}

	private void menuPrincipal() {
		
		String pedido;
		String resposta = null;
		
		System.out.println("\nMenu Principal:\n");
		System.out.println("1 - Agendar reuniao\n2 - Agendar reuniao p/votacao\n3 - Consultar reuni›es\n4 - Consultar convites\n0 - Sair\n");
		System.out.print("Opcao: ");
		
		int op = sc.nextInt();
		
		String [] temp;
		
		switch (op) {
	        case 1: pedido = criaStandardReuniao();
			        full  = new Object();
				    empty = new Object();
	        		out.println(pedido);
	        		
	        		temp = pedido.split("#");
	        		
	        		try {
	    	            resposta = input.readLine();
	    	
	    	        } catch (IOException e) {
	    	        	resposta = mantemLigacao(pedido);
	    	        }
					
					if(resposta.equals("Sucesso")){
						System.out.println("\nReuniao marcada com sucesso!\n");
					}
						convidarPessoas(temp[1]);
	        		
	                break;
	                
	        case 2: pedido = criaVotadaReuniao();
			        full  = new Object();
				    empty = new Object();
		    		out.println(pedido);
		    		
		    		temp = pedido.split("#");
	        		
		    		try {
			            resposta = input.readLine();
			
			        } catch (IOException e) {
			        	resposta = mantemLigacao(pedido);
			        }
					
					if(resposta.equals("Sucesso")){
						System.out.println("\nReuniao marcada com sucesso!\n");
					}
						convidarPessoas(temp[1]);
	        		
	                break;
	                
	        case 3: pedido = consultarReunioes();
	        
			        full  = new Object();
				    empty = new Object();
		    		out.println(pedido);
		    		
		    		try {
			            resposta = input.readLine();
			
			        } catch (IOException e) {
			        	resposta = mantemLigacao(pedido);
			        }
		    		
		    		int opcao = apresentaReunioes(resposta);
		    		
		    		String [] aux = resposta.split("#");
		    		
		    		if((opcao != 0) && (opcao < (aux.length + 1))){
		    			
		    			for(int i = 0; i < aux.length; i++){
		    				if((i+1) == opcao) pedido = "Entra Reuniao" + "#" + aux[i];
		    			}
		    			
		    			out.println(pedido);
		    			
		    			try {
		    	            resposta = input.readLine();
		    	
		    	        } catch (IOException e) {
		    	        	resposta = mantemLigacao(pedido);
		    	        }
		    			
		    			entraReuniao(resposta);
		    			
		    		}
					
	                break;
	        
	        case 4: pedido = consultarConvites();
	        
			        full  = new Object();
				    empty = new Object();
		    		out.println(pedido);
		    		
		    		try {
			            resposta = input.readLine();
			
			        } catch (IOException e) {
			        	resposta = mantemLigacao(pedido);
			        }
		    		
		    		aprensentaConvites(resposta);
		    		
            		break;
            
	        case 0: logout = 1;
	                break;
		}
		
	}

	private void aprensentaConvites(String resposta) {
		
		String [] convites = resposta.split("#");
		ArrayList< String > convitesMutavel = new ArrayList< String >();
		
		System.out.println("\nTem " + convites.length + " convites novos!");
		
		for(int i = 0; i < convites.length; i++){
			convitesMutavel.add(convites[i]);
		}
		
		int opcao = 1;
		
		while(opcao != 0){
			
			if(convitesMutavel.size() < 1) break;
			
			System.out.println();
			
			for(int i = 0; i < convitesMutavel.size(); i++){
				System.out.println((i+1) + " - " +convitesMutavel.get(i));
			}
			
			String resp = null;
			
			System.out.print("\nOpcao (0 para sair): ");
			opcao = sc.nextInt();
			
			if(opcao == 0) break;
			
			sc.nextLine();
			
			if(opcao != 0){
				System.out.print("Aceitar/Recusar: ");
				resp = sc.nextLine();
			}
			
			if(resp.equals("Aceitar")){
				
				String temp = convitesMutavel.get(opcao-1);
				convitesMutavel.remove(opcao-1);
				
				String pedido = "Aceitar Convite#" + username + "#" + temp;
				out.println(pedido);
				
				try {
		            resposta = input.readLine();
		
		        } catch (IOException e) {
		        	resposta = mantemLigacao(pedido);
		        }
				
				if(resposta.equals("Sucesso")) System.out.println("Convite aceite com sucesso!");
				
			}
			else if(resp.equals("Recusar")){
				
				String temp = convitesMutavel.get(opcao-1);
				convitesMutavel.remove(opcao-1);
				
				String pedido = "Recusar Convite#" + username + "#" + temp;
				out.println(pedido);
				
				try {
		            resposta = input.readLine();
		
		        } catch (IOException e) {
		        	resposta = mantemLigacao(pedido);
		        }
				
				if(resposta.equals("Sucesso")) System.out.println("Convite recusado com sucesso!");
				
			}
			else{
				System.out.println("\nOpcao invalida!");
			}
			
		}
		
		
	}

	private String consultarConvites() {

		return "Ver Convites#" + username;
		
	}

	private void entraReuniao(String resposta) {
		
		ArrayList< String > tarefas = new ArrayList< String >();
		ArrayList< String > decisions = new ArrayList< String >();
		
		String[] reuniao = resposta.split("#");
		
		int opcao = 1;
		
		while(opcao != 0){
			
			System.out.println("\nEntrou na reuniao '" + reuniao[0] + "'!\n");
			
			System.out.println("Admin: " + reuniao[1] + "\nObjectivo: " + reuniao[6] + "\nLocal: " + reuniao[2]);
			
			if(reuniao[5].equals("false"))
				System.out.println("Data: " + reuniao[3] + "\nHora: " + reuniao[4]);
			
			System.out.println();
			
			if(reuniao[5].equals("true")){
				System.out.println("1 - Action Items\n2 - Criar Key Decision\n3 - Consultar Key Decisions\n4 - Criar Tarefa\n5 - Consultar todas as tarefas\n6 - Consultar minhas tarefas\n7 - Votar numa data\n8 - Prop™r data");
			}
			if(username.equals(reuniao[1]) && reuniao[5].equals("true")){
				System.out.println("9 - Convidar ");
			}
			else if(username.equals(reuniao[1]) && !reuniao[5].equals("true")){
				System.out.println("1 - Convidar ");
			}
			
			System.out.println("0 - Sair");
			
			System.out.print("\nOpcao: ");
			opcao = sc.nextInt();
			
			if((opcao == 1 && (username.equals(reuniao[1]) && !reuniao[5].equals("true"))) || (opcao == 9 && (username.equals(reuniao[1]) && reuniao[5].equals("true")))){
				sc.nextLine();
				convidarPessoas(reuniao[0]);
				
			}
			else if(opcao == 2 && reuniao[5].equals("true")){
				
				sc.nextLine();
				
				while(true){
					
					String keyDecision;
					
					System.out.print("Decision (escreva 'terminar' para sair): ");
					keyDecision = sc.nextLine();
					if(keyDecision.equals("terminar")) break;
					
					decisions.add(keyDecision);
					
				}
				
				String pedido = "Criar Decisions#" + reuniao[0];
				
				for(int i = 0; i < decisions.size(); i++){
					pedido += ("#"+decisions.get(i));
				}
				
				out.println(pedido);
				
				try {
		            resposta = input.readLine();
		
		        } catch (IOException e) {
		        	resposta = mantemLigacao(pedido);
		        }
				
				if(resposta.equals("Sucesso")) System.out.println("\nNova Key Decision criada!");
				
			}
			else if(opcao == 3 && reuniao[5].equals("true")){
				
				String pedido = "Consulta Decisions#" + reuniao[0];
				
				out.println(pedido);
				
				try {
		            resposta = input.readLine();
		
		        } catch (IOException e) {
		        	resposta = mantemLigacao(pedido);
		        }
				
				String [] temp = resposta.split("#");
				
				System.out.println("\nKey Decisions:\n");
				
				for(int i = 0; i < temp.length; i++){
					if(!temp[i].equals("null")) System.out.println((i+1) + " - " + temp[i]);
				}
					
				
				System.out.println("\n0 - Sair");
				
				System.out.print("\nOpcao: ");
				sc.nextInt();
				
							
			}
			else if(opcao == 4 && reuniao[5].equals("true")){
				
				sc.nextLine();
				
				while(true){
					
					String tarefa, destinatario;
					
					System.out.print("Tarefa (escreva 'terminar' para sair): ");
					tarefa = sc.nextLine();
					if(tarefa.equals("terminar")) break;
					
					System.out.print("A quem se destina: ");
					destinatario = sc.nextLine();
					
					tarefas.add(destinatario + " - " + tarefa);
					
				}
				
				String pedido = "Criar Tarefas#" + reuniao[0];
				
				for(int i = 0; i < tarefas.size(); i++){
					pedido += ("#"+tarefas.get(i));
				}
				
				out.println(pedido);
				
				try {
		            resposta = input.readLine();
		
		        } catch (IOException e) {
		        	resposta = mantemLigacao(pedido);
		        }
				
				if(resposta.equals("Sucesso")) System.out.println("\nNova Tarefa criada!");
				
			}
			else if(opcao == 5 && reuniao[5].equals("true")){
				
				String pedido = "Consulta Tarefas#" + reuniao[0];
				
				out.println(pedido);
				
				try {
		            resposta = input.readLine();
		
		        } catch (IOException e) {
		        	resposta = mantemLigacao(pedido);
		        }
				
				String [] temp = resposta.split("#");
				
				System.out.println("\nTodas as tarefas:\n");
				
				for(int i = 0; i < temp.length; i++)
					if(!temp[i].equals("null"))
						System.out.println((i+1) + " - " + temp[i]);
				
				System.out.println("\n0 - Sair");
				
				System.out.print("\nOpcao: ");
				sc.nextInt();
				
			}
			else if(opcao == 6 && reuniao[5].equals("true")){
				
				String pedido = "Consulta Tarefas#" + reuniao[0];
				
				out.println(pedido);
				
				try {
		            resposta = input.readLine();
		
		        } catch (IOException e) {
		        	resposta = mantemLigacao(pedido);
		        }
				
				String [] temp = resposta.split("#");
				
				System.out.println("\nMinhas tarefas:\n");
				int cont = 0;
				for(int i = 0; i < temp.length; i++){
					String [] temp2 = temp[i].split(" - ");
					if(temp2[0].equals(username)){
						if(!temp[i].equals("null")){
							System.out.println((cont+1) + " - " + temp[i]);
							cont++;
						}
					}
				}
					
				
				System.out.println("\n0 - Sair");
				
				System.out.print("\nConcluir Tarefa: ");
				int op = sc.nextInt();
				int passa = 1;
				if(op != 0){
					cont = 0;
					for(int i = 0; i < temp.length; i++){
						String [] temp2 = temp[i].split(" - ");
						if(temp2[0].equals(username)){
							if((cont+1) == op){
								String [] testa = temp[i].split(" ");
								if(testa[testa.length-1].equals("(Concluido)")){
									passa = 0;
									break;
								}
								out.println("Tarefa Concluida#" + reuniao[0] + "#" + temp[i]);
								break;
							}
							cont++;
						}
					}
					
					if(passa == 1){
						try {
				            resposta = input.readLine();
				
				        } catch (IOException e) {
				        	resposta = mantemLigacao(pedido);
				        }
						
						if(resposta.equals("Sucesso")) System.out.println("\nTarefa Concluida com sucesso!");
						else System.out.println("\nErro!");
					}
					else System.out.println("\nErro!");
				}
				
			}
			else if(opcao == 7 && reuniao[5].equals("true")){
				
				System.out.println("\nVotar numa data:\n");
				
				String pedido = "Consultar Datas#" + reuniao[0];
				
				out.println(pedido);
				
				try {
		            resposta = input.readLine();
		
		        } catch (IOException e) {
		        	resposta = mantemLigacao(pedido);
		        }
				
				String [] temp = resposta.split("#");
				
				for(int i = 0; i < temp.length; i++){
					if(!temp[i].equals("null"))
						System.out.println((i+1) + " - " +temp[i]+" voto(s).");
				}
				System.out.println("0 - Sair");
				
				System.out.print("\nOpcao: ");
				int op = sc.nextInt();
				
				if(op != 0){
					
					for(int i = 0; i < temp.length; i++){
						if(op == (i+1)){
							
							pedido = "Votar Data#" + reuniao[0] + "#" + temp[i];
							
							out.println(pedido);
							
							try {
					            resposta = input.readLine();
					
					        } catch (IOException e) {
					        	resposta = mantemLigacao(pedido);
					        }
							
							if(resposta.equals("Sucesso")) System.out.println("\nVoto aceite com sucesso!");
							
						}
					}
					
				}
				
			}
			else if(opcao == 8 && reuniao[5].equals("true")){
				
				sc.nextLine();
				
				System.out.print("\nPropor data (dd/mm/aaaa hh:mm): ");
				String data = sc.nextLine();
				data += " - 1";
				
				String pedido = "Criar Data#" + reuniao[0] + "#" + data;
				
				out.println(pedido);
				
				try {
		            resposta = input.readLine();
		
		        } catch (IOException e) {
		        	resposta = mantemLigacao(pedido);
		        }
				
				if(resposta.equals("Sucesso")) System.out.println("\nData inserida com sucesso!");
				else System.out.println("\nErro: data j‡ inserida!");
				
			}
			else if(opcao == 1 && reuniao[5].equals("true")){
				
				System.out.println("\n1 - Criar Novo\n2 - Consultar todos\n0 - Sair");
				
				System.out.print("\nOpcao: ");
				int op = sc.nextInt();
				
				sc.nextLine();
				
				if(op == 1){
					
					System.out.print("\nAction Item: ");
					String op2 = sc.nextLine();
					
					String pedido = "Adicionar Action Item#"+ reuniao[0] + "#" + op2;
					out.println(pedido);
					
					try {
			            resposta = input.readLine();
			
			        } catch (IOException e) {
			        	resposta = mantemLigacao(pedido);
			        }
					
					if(resposta.equals("Sucesso")) System.out.println("\nAction Item inserida com sucesso!");
					else System.out.println("\nErro: action item j‡ inserida!");
					
				}
				else if(op == 2){
					
					System.out.println();
					String pedido = "Consultar Action Item#"+ reuniao[0];
					out.println(pedido);
					
					try {
			            resposta = input.readLine();
			
			        } catch (IOException e) {
			        	resposta = mantemLigacao(pedido);
			        }
					
					String [] temp = resposta.split("#");
					
					if(!resposta.equals("Falha")){
					
						for(int i = 0; i < temp.length; i++){
							
							System.out.println((i+1) + " - " +temp[i]);
							
						}
						System.out.println("0 - Sair");
						
						System.out.print("\nOpcao: ");
						
						int op2 = sc.nextInt();
						if(op2 != 0){
							
							String action = null;
							
							for(int i = 0; i < temp.length; i++){
								
								if(op2 == (i+1)) action = temp[i];
								
							}
							
							System.out.println("\n1 - Chat\n2 - Eliminar\n3 - Modificar\n0 - Sair");
							System.out.print("\nOpcao: ");
							
							int op3 = sc.nextInt();
							
							if(op3 == 1){
								
								System.out.println("\nChat da action item "+action+":\n");
								
								sc.nextLine();
								
								out.println("Download Chat#"+reuniao[0]+"#"+action);
								Chat thread = new Chat(socket, reuniao[0], username, input, out, action);
								
								String chat = null;
								
								while(true){
									
									chat = sc.nextLine();
									
									if(chat.equals("SAIR")) break;
									
									pedido = "Chat#" + reuniao[0] + "#" + action + "#" + username + ": " +chat;
									
									out.println(pedido);
									
								}
								
								thread.corre = false;
								
								try {
									input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								resposta = null;
								
								pedido = "Chat#" + reuniao[0] + "#" + action + "#" + username + " saiu da conversa.";
								out.println(pedido);
							
								
							}
							else if(op3 == 2){
								
								pedido = "Eliminar Action Item#"+ reuniao[0]+"#"+action;
								
								out.println(pedido);
								
								try {
						            resposta = input.readLine();
						
						        } catch (IOException e) {
						        	resposta = mantemLigacao(pedido);
						        }
								
								if(resposta.equals("Sucesso")) System.out.println("Action Item eliminado com sucesso!");
								
							}
							else if(op3 == 3){
								
								sc.nextLine();
								
								System.out.print("\nNome novo: ");
								String novo = sc.nextLine();
								
								pedido = "Modificar Action Item#"+ reuniao[0]+"#"+action+"#"+novo;
								
								out.println(pedido);
								
								try {
						            resposta = input.readLine();
						
						        } catch (IOException e) {
						        	resposta = mantemLigacao(pedido);
						        }
								
								if(resposta.equals("Sucesso")) System.out.println("Action Item modificado com sucesso!");
								
							}
							
						}
					
					}
					
				}
				
			}
			
		}
		
	}

	private int apresentaReunioes(String resposta) {
		
		String [] temp = resposta.split("#");
		
		System.out.println("\nReuni›es agendadas:\n");
		
		for(int i = 0; i < temp.length; i++)
			if(!temp[i].equals("null"))
				System.out.println((i+1) + " - " +temp[i]);
		
		System.out.print("\nOpao (0 para sair): ");
		
		int op = sc.nextInt();
		
		return op;
		
	}

	private void convidarPessoas(String nomeReuniao) {
		
		String op;
		ArrayList< String > listaUsers = new ArrayList< String >();
		
		System.out.print("Deseja convidar pessoas? (S/N): ");
		op = sc.nextLine();
		
		if(op.equals("S") || op.equals("s")){
			
			System.out.println("\nIntroduza os nomes dos utilizadores (escreva 'terminar' quando acabar de convidar):\n");
			
			String nome = "";
			while(!nome.equals("terminar")){
				System.out.print("Nome: ");
				nome = sc.nextLine();
				if(!nome.equals("terminar")) listaUsers.add(nome);
			}
			
			String pedido = "Convidar#" + nomeReuniao;
			
			for(int i = 0; i < listaUsers.size(); i++){
				pedido += ("#" + listaUsers.get(i));
			}
			
			out.println(pedido);
			
			String resposta = null;
			
			try {
	            resposta = input.readLine();
	
	        } catch (IOException e) {
	        	resposta = mantemLigacao(pedido);
	        }
			
			if(resposta.equals("Sucesso")) System.out.println("Convites enviados com sucesso!");
		
		}
		
	}

	private String consultarReunioes() {
		
		return "Consultar Reunioes#" + username;
		
	}

	private String criaVotadaReuniao() {
		
		String nome, objectivo, local, opcao;
		ArrayList< String > tarefas = new ArrayList< String >();
		ArrayList< String > decisions = new ArrayList< String >();
		
		sc.nextLine();
		
		System.out.println("\nAgendar Reuniao Especial:\n");
		
		System.out.print("Nome: ");
		nome = sc.nextLine();
		
		System.out.print("Objectivo da reuniao: ");
		objectivo = sc.nextLine();
		
		System.out.print("Local: ");
		local = sc.nextLine();
		
		System.out.print("\nDeseja adicionar alguma tarefa? (S/N): ");
		opcao = sc.nextLine();
		
		if(opcao.equals("S") || opcao.equals("s")){
			
			while(true){
				
				String tarefa, destinatario;
				
				System.out.print("Tarefa (escreva 'terminar' para sair): ");
				tarefa = sc.nextLine();
				if(tarefa.equals("terminar")) break;
				
				System.out.print("A quem se destina: ");
				destinatario = sc.nextLine();
				
				tarefas.add(destinatario + " - " + tarefa);
				
			}
			
		}
		
		System.out.print("\nDeseja adicionar alguma key decision? (S/N): ");
		opcao = sc.nextLine();
		
		if(opcao.equals("S") || opcao.equals("s")){
			
			while(true){
				
				String keyDecision;
				
				System.out.print("Decision (escreva 'terminar' para sair): ");
				keyDecision = sc.nextLine();
				if(keyDecision.equals("terminar")) break;
				
				decisions.add(keyDecision);
				
			}
			
		}
		
		String tasks = null;
		String keyDecisions = null;
		
		if(tarefas.size() > 0){
			tasks = tarefas.get(0);
		
			for(int i = 1; i < tarefas.size(); i++){
				tasks += ("/"+tarefas.get(i));
			}
		}
		if(decisions.size() > 0){
			keyDecisions = decisions.get(0);
			
			for(int i = 1; i < decisions.size(); i++){
				keyDecisions += ("/"+decisions.get(i));
			}
		}
		
		return "Mutable Reuniao#" + nome + "#" + objectivo + "#" + local + "#" + tasks + "#" + keyDecisions + "#" + username; 
		
	}

	private String criaStandardReuniao() {
		
		String nome, objectivo, data, hora, local;
		sc.nextLine();
		
		System.out.println("\nAgendar Reuniao:\n");
		
		System.out.print("Nome: ");
		nome = sc.nextLine();
		
		System.out.print("Objectivo da reuniao: ");
		objectivo = sc.nextLine();
		
		System.out.print("Data (dd/mm/aaaa): ");
		data = sc.nextLine();
		
		System.out.print("Hora (hh:mm): ");
		hora = sc.nextLine();
		
		System.out.print("Local: ");
		local = sc.nextLine();
		
		return "Standard Reuniao" + "#" + nome + "#" + objectivo + "#" + data + "#" + hora + "#" + local + "#" + username;
		
	}

	private String menuRegisto() {
		
		sc = new Scanner(System.in);
		System.out.println("\nResgisto:\n");
		System.out.print("Username: ");
		String user = sc.nextLine();
		System.out.print("Password: ");
		String pass = sc.nextLine();
		
		return "Registo"+ "#" + user + "#" + pass;
		
	}

	private String menuLogin() {
		
		sc = new Scanner(System.in);
		System.out.println("\nLogin:\n");
		System.out.print("Username: ");
		String user = sc.nextLine();
		System.out.print("Password: ");
		String pass = sc.nextLine();
		username = user;
		
		return "Login"+ "#" + user + "#" + pass;
		
	}

	private int menuEntrada() {
		
		System.out.println("\nBem vindo ao sistema de meetings!\n\n1 - Login\n2 - Resgitar\n0 - Sair");
		
		System.out.print("\nOpcao: ");
		
		int op = sc.nextInt();
		
		return op; 
		
	}

}
