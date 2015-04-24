<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Meetings</title>
	</head>
	<body>
		<p><b>Reunião:</b> ${session.nomeReuniao} ${session.sairMenu}
		
		<s:form action="login" method="post">
				<s:hidden name="username" value="%{#session.username}" />
				<s:hidden name="type" value="SairReuniao" />
				<s:submit value="Sair"/>
		</s:form>
		
		<br><b>Administrador:</b> ${session.nomeAdmin}
		<br><b>Local:</b> ${session.nomeLocal}
		<br><b>Data:</b> ${session.data}
		<br><b>Hora:</b> ${session.hora}
		<br><b>Objectivo:</b> ${session.objectivo}</p>

	
	<p><b>Action Items</b><br>
		
		<div>
			Nome: 
	        <input type="text" id="messageinputAction" name="Ze"/>
	        <button type="button" onclick="sendNewAction();" >Criar</button>
	    </div><br>
		
		${session.listaItems}<br>
		
		<s:form action="entraItem" method="post">
				<s:hidden name="username" value="%{#session.username}" />
				<s:hidden name="reuniao" value="%{#session.reuniao}" />
				<s:hidden name="type" value="EntrarItem" />
				Opção: 
				<s:textfield name="actionItem" />
				<s:submit value="Entra"/>
		</s:form>
	</p>
	
	<p><b>Key Decisions</b>
		
		<div>
			Decision: 
	        <input type="text" id="messageinputDecision" name="Ze"/>
	        <button type="button" onclick="sendNewDecision();" >Criar</button>
	    </div><br>
		
		${session.listaDecisions}
	
	</p>
	
	<p><b>Tasks</b><br>
		
		
		<div>
			Tarefa (Destinatario/Task):
	        <input type="text" id="messageinputTaskto" name="Ze"/>
	        <input type="text" id="messageinputTaskOnly" name="Ze"/>
	        <button type="button" onclick="sendNewTask();" >Criar</button>
	    </div><br>
	
		Todas as tarefas:
		${session.listaTarefas}
		<br>
	    Suas tarefas:
	    ${session.minhasTarefas}
	    <br>
	    
	    <s:form action="entraReuniao" method="post">
				<s:hidden name="username" value="%{#session.username}" />
				<s:hidden name="reuniao" value="%{#session.reuniao}" />
				<s:hidden name="type" value="concluirTarefa" />
				Tarefa:
				<s:textfield name="tarefa" />
				<s:submit value="Concluir"/>
		</s:form>
	
	</p>
	
	<p><b>Convidar</b>
		<div>
	        <input type="text" id="messageinput" name="Ze"/>
	        <button type="button" onclick="doSend();" >Convidar</button>
	    </div>
	</p>
	<div id="output" style="overflow-y:scroll; height:250px;"></div>
		
	<!-- Convidar, Chat, Tasks, Decisions, Action Item -->
</body>
${ligar}
	    
		var wsUri = "ws://192.168.1.70:8080/Hey/menu?username="+username+"&type=inside&reuniao="+reuniao;
	    var output;
	
	    function init() {
	        output = document.getElementById("output");
	        testWebSocket();
	    }
	
	    function testWebSocket() {
	    	websocket = new WebSocket(wsUri);
	        
	    	websocket.onopen = function(evt) {
	            onOpen(evt)
	        };
	        
	        websocket.onmessage = function(evt) {
	            onMessage(evt)
	        };
	        websocket.onerror = function(evt) {
	            onError(evt)
	        };
	    }
	
	    function onOpen(evt) {
	        //writeToScreen("Ligado");
	    }
	
	    function onClose(evt) {
	        //writeToScreen("Desligado");
	    }
	
	    function onMessage(evt) {
	        writeToScreen('<span style="color: black;">' + evt.data + '</span>');
	    }
	
	    function onError(evt) {
	        //writeToScreen('<span style="color: red;">ERRO:</span> ' + evt.data);
	    }
	    
	    function doSend() {
	        //writeToScreen('<span style="color: grey;">Enviado: '+ document.getElementById("messageinput").value+"#"+reuniao+'</span>');
	        websocket.send(document.getElementById("messageinput").value+"#"+reuniao+"#"+"convite");
	        document.getElementById("messageinput").value = "";
	    }
	    
	    function sendNewAction() {
	    	websocket.send(document.getElementById("messageinputAction").value+"#"+reuniao+"#"+"item");
	    }
	    
	    function sendNewTask() {
	    	websocket.send(document.getElementById("messageinputTaskOnly").value+" - "+document.getElementById("messageinputTaskto").value+"#"+reuniao+"#"+"tarefa");
	    }
	    
	    function sendNewDecision() {
	    	websocket.send(document.getElementById("messageinputDecision").value+"#"+reuniao+"#"+"decision");
	    }
	
	    function writeToScreen(message) {
	        var pre = document.createElement("p");
	        pre.innerHTML = message;
	        output.appendChild(pre);
	    }
	    
	    window.addEventListener("load", init, false);
	    
	</script>