<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Notifications</title>
</head>
<body>
<h1><center>Notifications</center></h1>

<c:forEach items = "${it}" var="cur" >
    <c:out value="${cur.name}"/> added you as a friend
    <a href=/social/acceptFriendReq/<c:out value="${cur.id}"/>>Confirm</a><br>
</c:forEach>
</body>
</html>