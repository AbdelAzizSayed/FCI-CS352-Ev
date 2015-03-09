<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Add Friend</title>
</head>
<body>
<center><h1>Add A Friend</h1></center>
<form action ="/social/sendFriendReq" method = "POST">
	Add Friend By Email : <input type = "text" name = "email"><br>
	<input type = "submit" value = "Add Friend"><br>
</form>
</body>
</html>