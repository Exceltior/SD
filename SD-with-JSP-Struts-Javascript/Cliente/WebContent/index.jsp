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
	<s:form action="login" method="post">
		<s:hidden name="type" value="Login" />
		Username: 
		<s:textfield name="username" />
		Password:
		<s:textfield name="password" />
		<s:submit value="Login"/>
	</s:form>
	<s:form action="login" method="post">
		<s:hidden name="type" value="Registo" />
		Username: 
		<s:textfield name="username" />
		Password:
		<s:textfield name="password" />
		<s:submit value="Registar"/>
	</s:form>
	<form action="Google" method="get">
			
			<button type="submit" name="action" value="login">Google</button>
			
		</form>
</body>
</html>