package Web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rmiclient.RMIClient;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	RequestDispatcher view;
	String pedido, forward, RMIanswer, naming = "//127.0.0.1:25055/calc";
	RMIClient rmiCon;
	HttpServletRequest requestReuniao;
	HttpServletResponse responseReuniao;
	
	public Servlet() {
        super();
        rmiCon = new RMIClient(naming);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hey!");
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		@SuppressWarnings("rawtypes")
		Map parameters = request.getParameterMap();
		
	    if (parameters.containsKey("action")){
	    	
	    	pedido = request.getParameter("action");
	    	
	    	if(pedido.equals("login")){
	    		
	    		RMIanswer = rmiCon.checkLogin(request.getParameter("username"), request.getParameter("password"));
	    		
	    		if(RMIanswer.equals("Sucesso")){
	    			
	    			String username = request.getParameter("username");
	    			
	    			request = abreMainMenu(request, username);
	    			
	    			forward = "main_menu.jsp";
	    			view = request.getRequestDispatcher(forward);
	    		    view.forward(request, response);
		    	    
	    		}
	    		else{
	    			forward = "Index.jsp";
		    		view = request.getRequestDispatcher(forward);
		    	    view.forward(request, response);
	    		}
	    	    
	    	}
	    	else if(pedido.equals("registo")){
	    		
	    		RMIanswer = rmiCon.registo(request.getParameter("username"), request.getParameter("password"));
	    		
	    		if(RMIanswer.equals("Sucesso")){
	    			
	    			String username = request.getParameter("username");
	    			
	    			request = abreMainMenu(request, username);
	    			
	    			forward = "main_menu.jsp";
		    		view = request.getRequestDispatcher(forward);
		    	    view.forward(request, response);
		    	    
	    		}
	    		else{
	    			forward = "Index.jsp";
		    		view = request.getRequestDispatcher(forward);
		    	    view.forward(request, response);
	    		}
	    	    
	    	}
	    	
	    }
	    else if(parameters.containsKey("entraMenu")){
	    	
	    	String username = request.getParameter("username");
	    	
	    	System.out.println(username);
	    	
	    	request = abreMainMenu(request, username);
			
			forward = "main_menu.jsp";
    		view = request.getRequestDispatcher(forward);
    	    view.forward(request, response);
	    	
	    }
	    else if(parameters.containsKey("entraReuniao")){
	    	
	    	requestReuniao = request;
	    	responseReuniao = response;
	    	
	    	pedido = request.getParameter("entraReuniao");
	    	
	    	String[] temp = pedido.split("#");
	    	
	    	request = abreReuniao(temp[0], request, temp[1]);
	    	
	    	forward = "reuniao.jsp";
    		view = request.getRequestDispatcher(forward);
    	    view.forward(request, response);
	    	
	    }
	    else if(parameters.containsKey("aceitarConvite")){
	    	
	    	String reuniao = request.getParameter("aceitarConvite");
	    	
	    	String[] temp = reuniao.split("#");
	    	
	    	rmiCon.aceitarConvite(temp[1], temp[0]);
	    	
	    	request = abreReuniao(temp[0], request, temp[1]);
	    	
	    	forward = "reuniao.jsp";
    		view = request.getRequestDispatcher(forward);
    	    view.forward(request, response);
	    	
	    }
	    else if(parameters.containsKey("recusarConvite")){
	    	
	    	String reuniao = request.getParameter("recusarConvite");
	    	
	    	String[] temp = reuniao.split("#");
	    	
	    	rmiCon.recusarConvite(temp[1], temp[0]);
	    	
	    	request = abreMainMenu(request, temp[1]);
			
			forward = "main_menu.jsp";
			view = request.getRequestDispatcher(forward);
		    view.forward(request, response);
	    	
	    }
	    else if(parameters.containsKey("menuCriaReuniao")){
	    	
	    	String username = request.getParameter("menuCriaReuniao");
	    	
	    	String criaButton = "<button type="+"submit"+" name="+"criarReuniao"+" value="+username+">Criar Reuni‹o</button>";
	    	
	    	request.setAttribute("criaButton", criaButton);
    		
    		forward = "criar_reuniao.jsp";
    		view = request.getRequestDispatcher(forward);
    	    view.forward(request, response);
    	    
    	}
	    else if(parameters.containsKey("criarReuniao")){
    		
	    	String username = request.getParameter("criarReuniao");
	    	
	    	System.out.println(username);
	    	
    		String reuniao = request.getParameter("reuniao");
	    	String objectivo = request.getParameter("objectivo");
	    	String local = request.getParameter("local");
	    	String data = request.getParameter("data");
	    	String hora = request.getParameter("hora");
	    	
	    	String task1 = request.getParameter("tarefa1");
	    	String toWho1 = request.getParameter("toWho1");
	    	
	    	String decision1 = request.getParameter("decision1");
	    	
			String tasks = null;
			
			if(!task1.equals("") && !toWho1.equals("")) tasks = toWho1 + " - " + task1;
			
			String decisions = null;
			
			if(!decision1.equals("")) decisions = decision1;
	    	
			String answer = "null#" + reuniao + "#" + objectivo + "#" + local;
			
			if(tasks != null) answer += "#" + tasks;
			else answer += "#null";
			if(decisions != null) answer += "#" + decisions;
			else answer += "#null";
			
			answer += "#" + username;
	    	
    		String resposta = rmiCon.marcaMutableReuniao(answer, data, hora);
    		
    		System.out.println(resposta);
    		
    		if(resposta.equals("Sucesso")){
    			
    			System.out.println("Hey!");
    			
    			request = abreMainMenu(request, username);
    			
    			forward = "main_menu.jsp";
	    		view = request.getRequestDispatcher(forward);
	    	    view.forward(request, response);
	    	    
    		}
    		else{
    			
    			forward = "criar_reuniao.jsp";
	    		view = request.getRequestDispatcher(forward);
	    	    view.forward(request, response);
    			
    		}
    	    
    	}

	    
	}
	
	public HttpServletRequest abreMainMenu(HttpServletRequest request, String username){

		String reunioes = rmiCon.consultaReunioes(username);
		String convites = rmiCon.mostraConvites(username);
		
		String[] temp = reunioes.split("#");
		String[] aux = convites.split("#");
		
		String variablesJavaScript = "<script language="+"javascript"+" type="+"text/javascript"+">"
				+" var username='"+username+"'; ";
		
		String reunioesImprime = "";
		String convitesImprime = "";
		
		String criaReuniao = "<p><li><button type="+"submit"+" name="+"menuCriaReuniao"+" value="+username+">Criar Reuni‹o</button></li></p>";
		
		for(int i = 0; i < temp.length; i++){
			if(!temp[i].equals("Sem reunioes")){
				reunioesImprime += ("<li>" + "<button type="+"submit"+" name="+"entraReuniao"+" value=" + temp[i]+"#"+username + ">Entrar</button>  " + temp[i] + "</li>");
			}
		}
		
		if(reunioesImprime.equals("")){
			reunioesImprime = "<li>Sem Reunioes</li>";
		}
		
		for(int i = 0; i < aux.length; i++){
			if(!aux[i].equals("Sem convites"))
				convitesImprime += ("<li>" + "<button type="+"submit"+" name="+"aceitarConvite"+" value=" + aux[i]+"#"+username + ">Aceitar</button>  " + "<button type="+"submit"+" name="+"recusarConvite"+" value=" + aux[i]+"#"+username + ">Recusar</button>  " + aux[i] + "</li>");
		}
		
		if(convitesImprime.equals("")){
			convitesImprime = "<li>Sem Convites</li>";
		}
		
		request.setAttribute("criaReuniao", criaReuniao);
		request.setAttribute("message", reunioesImprime);
		request.setAttribute("convites", convitesImprime);
		request.setAttribute("ligar", variablesJavaScript);
		
		return request;
		
	}
	
	public HttpServletRequest abreReuniao(String pedido, HttpServletRequest request, String username){
		
		String reuniaoinfo = rmiCon.entraReuniao(pedido);
    	String actionItems = rmiCon.consultarActionItem(pedido);
    	String keyDecisions = rmiCon.consultaDecisions(pedido);
    	String tasks = rmiCon.consultarTarefas(pedido);
    	System.out.println(pedido + " - " +actionItems);
    	String[] info = reuniaoinfo.split("#");
    	String[] listaItems = actionItems.split("#");
    	String[] listaDecisions = keyDecisions.split("#");
    	String[] listaTarefas = tasks.split("#");
    	
    	String criaItem = "Nome Action: <input type="+"text"+" name="+"newAction"+"><button type="+"submit"+" name="+"criarAction"+" value="+info[0]+"#"+username+">Criar novo</button>";
    	String criaDecision = "Key Decision: <input type="+"text"+" name="+"newDecision"+"><button type="+"submit"+" name="+"criarDecision"+" value="+info[0]+"#"+username+">Criar nova</button>";
    	String criaTarefa = "Task: <input type="+"text"+" name="+"newTask"+"><input type="+"text"+" name="+"toWho"+"><button type="+"submit"+" name="+"criarTask"+" value="+info[0]+"#"+username+">Criar nova</button>";
    	String sairMenu = "<button type="+"submit"+" name="+"sairMainMenu"+" value="+username+">Sair</button>";
    	String variablesJavaScript = "<script language="+"javascript"+" type="+"text/javascript"+">"
				+" var username='"+username+"'; "
				+" var reuniao='"+info[0]+"'; ";
    	
    	String itemsImprime = "";
    	String decisionsImprime = "";
    	String tarefasImprime = "";
    	String minhasTasks = "";
		
		for(int i = 0; i < listaItems.length; i++)
			if(!listaItems[i].equals("null") && !listaItems[i].equals("Sem Action Items"))
				itemsImprime += ("<li>" + "<button type="+"submit"+" name="+"entraItem"+" value=" + i+"#"+info[0]+"#"+username + ">Entrar</button>  " + listaItems[i] + "</li>");
		for(int i = 0; i < listaDecisions.length; i++)
			if(!listaDecisions[i].equals("null") && !listaDecisions[i].equals("Sem Key Decisions"))
				decisionsImprime += ("<li>" + "<button type="+"submit"+" name="+"apagaDecision"+" value=" + listaDecisions[i]+"#"+username + ">Eliminar</button>  " + listaDecisions[i] + "</li>");
		for(int i = 0; i < listaTarefas.length; i++)
			if(!listaTarefas[i].equals("null") && !listaTarefas[i].equals("Sem tarefas"))
				tarefasImprime += ("<li>" + listaTarefas[i] + "</li>");
		for(int i = 0; i < listaTarefas.length; i++)
			if(!listaTarefas[i].equals("null") && !listaTarefas[i].equals("Sem tarefas")){
				String[] temp = listaTarefas[i].split(" - ");
				if(temp[0].equals(username)){
					String[] aux = temp[temp.length-1].split(" ");
					if(aux[aux.length-1].equals("(Concluido)"))
						minhasTasks += ("<li>" + "<button type="+"submit"+" name="+"concluirTarefa"+" value=" + listaTarefas[i] + " disabled>Concluir</button>  " + listaTarefas[i] + "</li>");
					else
						minhasTasks += ("<li>" + "<button type="+"submit"+" name="+"concluirTarefa"+" value=" + i+"#"+info[0]+"#"+username + " >Concluir</button>  " + listaTarefas[i] + "</li>");
				}
			}
		
		if(itemsImprime.equals("")) itemsImprime = "<li> Sem Action Items </li>";
		if(decisionsImprime.equals("")) decisionsImprime = "<li> Sem Key Decisions </li>";
		if(tarefasImprime.equals("")) tarefasImprime = "<li> Sem Tarefas </li>";
		if(minhasTasks.equals("")) minhasTasks = "<li> Sem Tarefas </li>";
		
		//reuniao = info[0];
		
		request.setAttribute("listaItems", itemsImprime);
		request.setAttribute("listaDecisions", decisionsImprime);
		request.setAttribute("listaTarefas", tarefasImprime);
		request.setAttribute("minhasTarefas", minhasTasks);
		request.setAttribute("criarItem", criaItem);
		request.setAttribute("criarDecision", criaDecision);
		request.setAttribute("criarTarefa", criaTarefa);
    	request.setAttribute("nomeReuniao", info[0]);
    	request.setAttribute("nomeAdmin", info[1]);
    	request.setAttribute("nomeLocal", info[2]);
    	request.setAttribute("data", info[3]);
    	request.setAttribute("hora", info[4]);
    	request.setAttribute("tipo", info[5]);
    	request.setAttribute("objectivo", info[6]);
    	request.setAttribute("sairMenu", sairMenu);
    	request.setAttribute("ligar", variablesJavaScript);
    	
    	return request;
		
	}

}
