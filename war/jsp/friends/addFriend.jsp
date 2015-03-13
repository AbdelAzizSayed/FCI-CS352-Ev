<img src="<%=request.getContextPath()%>/image/top.jpg" width="1350" height="80"/>
<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>	
<head>
	<link rel="stylesheet" type="text/css" href="css\style.css">
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
	<title>Add Friend</title>
</head>
<body>
<center><h1>Add A Friend</h1></center>
<form action ="/social/sendFriendReq" method = "POST">
	<center>Add Friend By Email : <input type = "text" name = "email"><br>
	<input type = "submit" value = "Add Friend"><br></center>
</form>
</body>
</html>