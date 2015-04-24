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
		<p>Menu Principal:</p>
	
		<s:form action="criarReuniao" method="post">
			<s:hidden name="username" value="%{#session.username}" />
			<s:hidden name="type" value="CriarReuniao" />
			<s:submit value="Criar Reuniao"/>
		</s:form><br>
		
		Reuniões:	
	    	<p>${session.message}</p>
			<s:form action="entraReuniao" method="post">
				<s:hidden name="username" value="%{#session.username}" />
				<s:hidden name="type" value="EntraReuniao" />
				Opção: 
				<s:textfield name="reuniao" />
				<s:submit value="Entra"/>
			</s:form><br>
		
		Convites:
		<div style="overflow-y:scroll; height:100px;" id="output">
				<p>${session.convites}</p>
	    </div>
	    
	    <s:form action="login" method="post">
			<s:hidden name="username" value="%{#session.username}" />
			<s:hidden name="type" value="aceitaConvite" />
			Opção: 
			<s:textfield name="reuniao" />
			<s:submit value="Aceitar"/>
		</s:form><br>
	    
	</body>
	
	${session.ligar}
	    
		var wsUri = "ws://192.168.1.70:8080/Hey/menu?username="+username+"&type=outside&reuniao=null";
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
	        writeToScreen("Desligado");
	    }
	
	    function onMessage(evt) {
	        writeToScreen('<span style="color: black;">' + evt.data + '</span>');
	    }
	
	    function onError(evt) {
	       writeToScreen('<span style="color: red;">ERRO:</span> ' + evt.data);
	    }
	
	    function writeToScreen(message) {
	    	if(isThere<1){
	    		document.getElementById("output").innerHTML = "";
	    		var pre = document.createElement("p");
	        	pre.innerHTML = message;
	        	output.appendChild(pre);
	    	}else{
	        	var pre = document.createElement("p");
	        	pre.innerHTML = message;
	        	output.appendChild(pre);
			}
	        
	    }
	    
	    window.addEventListener("load", init, false);
	    
	</script>
</html>