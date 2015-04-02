<img src="<%=request.getContextPath()%>/image/top.jpg" width="1350" height="80"/>
<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
   

<html>	
<head>
	<link rel="stylesheet" type="text/css" href="css\style.css"><meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Friend List</title>
</head>
<body>
<center><h1>Your Friend List</h1></center>
<c:forEach items = "${it}" var = "cur"  >
    <c:out value="${cur}"/> 
</c:forEach>
</body>
</html>