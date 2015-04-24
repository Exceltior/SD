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
		
		<p><b>Criar Reunião:</b></p>
		
		<s:form action="entraReuniao" method="post">
			<s:hidden name="username" value="%{#session.username}" />
			<s:hidden name="type" value="RegistarReuniao" />
			Nome: 
			<s:textfield name="nomeReuniao" /><br>
			Objectivo: 
			<s:textfield name="objectivo" /><br>
			Local: 
			<s:textfield name="local" /><br>
			Data: 
			<s:textfield name="data" /><br>
			Hora: 
			<s:textfield name="hora" /><br>
			Tarefa (Tarefa/Destinatário): 
			<s:textfield name="tarefaNova" />
			<s:textfield name="toWho" /><br>
			Key Decision: 
			<s:textfield name="decisionNova" /><br>
			<s:submit value="Criar Reunião"/>
		</s:form><br>
	
	</body>
</html>