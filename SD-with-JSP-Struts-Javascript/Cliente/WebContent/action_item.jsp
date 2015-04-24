<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Meetings</title>
	</head>
	<body><b>Bem-vindo ao action item ${session.nomeAction}, ${session.username}</b><br><br>

	
		<s:form action="entraItem" method="post">
				<s:hidden name="username" value="%{#session.username}" />
				<s:hidden name="reuniao" value="%{#session.reuniao}" />
				<s:hidden name="actionItem" value="%{#session.nomeAction}" />
				<s:hidden name="type" value="MudarNome" />
				Nome: 
				<s:textfield name="newName" />
				<s:submit value="Editar"/>
		</s:form>
		<s:form action="entraReuniao" method="post">
				<s:hidden name="username" value="%{#session.username}" />
				<s:hidden name="reuniao" value="%{#session.reuniao}" />
				<s:hidden name="actionItem" value="%{#session.nomeAction}" />
				<s:hidden name="type" value="EliminarAction" />
				<s:submit value="Eliminar Action"/>
		</s:form>
		<s:form action="entraReuniao" method="post">
				<s:hidden name="username" value="%{#session.username}" />
				<s:hidden name="reuniao" value="%{#session.reuniao}" />
				<s:hidden name="actionItem" value="%{#session.nomeAction}" />
				<s:hidden name="type" value="SairItem" />
				<s:submit value="Sair"/>
		</s:form>
		
			<!--${session.modificar}
			${session.sairMenu}-->

	</body>
	<h2>Chat</h2>
	<div>
        <input type="text" id="messageinput" name="Ze"/>
        <button type="button" onclick="doSend();" >Send</button>
    </div>
	${session.ligar}
	    
		var wsUri = "ws://192.168.1.70:8080/Hey/simple?username="+username+"&reuniao="+reuniao+"&actionItem="+actionItem;
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
	        writeToScreen("Ligado");
	    }
	
	    function onClose(evt) {
	        writeToScreen("Desligado");
	    }
	
	    function onMessage(evt) {
	        writeToScreen('<span style="color: black;">' + evt.data + '</span>');
	    }
	
	    function onError(evt) {
	        writeToScreen('<span style="color: red;">ERRO:</span> ' + evt.data);
	    }
	
	    function doSend() {
	        writeToScreen('<span style="color: grey;">Enviado: '+ document.getElementById("messageinput").value+'</span>');
	        websocket.send(document.getElementById("messageinput").value);
	        document.getElementById("messageinput").value = "";
	    }
	
	    function writeToScreen(message) {
	        var pre = document.createElement("p");
	        pre.innerHTML = message;
	        output.appendChild(pre);
	    }
	    
	    window.addEventListener("load", init, false);
	    
	</script>
<div id="output" style="overflow-y:scroll; height:400px;">${session.chat}</div>