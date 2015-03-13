<img src="<%=request.getContextPath()%>/image/top.jpg" width="1350" height="80"/>
<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>	
<head>
	<link rel="stylesheet" type="text/css" href="css\style.css">
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<title>FCI SN Login</title>
</head>
<body>
<h1><center>Login To Our FCI Social Network</center></h1>
<form action="/social/home" method="post">
  <center>Name : <input type="text" name="uname" /> <br>
  Password : <input type="password" name="password" /> <br>
  <input type="submit" value="Login"><center>
  
  </form>
</body>
</html>