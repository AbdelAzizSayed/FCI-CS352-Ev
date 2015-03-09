<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    <%@page import="com.google.appengine.api.datastore.DatastoreService"%>
    <%@page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
    <%@page import="com.google.appengine.api.datastore.Entity"%>
    <%@page import="com.google.appengine.api.datastore.FetchOptions"%>
    <%@page import="com.google.appengine.api.datastore.PreparedQuery"%>
    <%@page import="com.google.appengine.api.datastore.Query"%>
    <%@page import="com.google.appengine.api.datastore.Query"%>
    <%@page import="java.util.List"%>
    <%@page import="com.FCI.SWE.Models.User"%>
    
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Friend List</title>
</head>
<body>
<center><h1>Your Friend List</h1></center>
<%	

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Query gaeQuery = new Query("friendship");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	String currentUserID = Long.toString(User.currentActiveUser.getId());
	//out.print("<h3>You have " + pq.countEntities() + " friend requests : " + "</h3>");
	
	for (Entity entity : pq.asIterable()) 
	{
			if (entity.getProperty("RecID").toString().equals(currentUserID) &&
					entity.getProperty("isApproved").toString().equals("false"))
			{
				String sendID = entity.getProperty("SendID").toString();
				gaeQuery = new Query("users");
			    pq = datastore.prepare(gaeQuery);
				for (Entity entity2 : pq.asIterable())
				{
					if(Long.toString(entity2.getKey().getId()).equals(sendID))
					{
						out.print(entity2.getProperty("name") + " added you as a friend\t\t");
						out.print("<a href=\"/social/acceptFriendReq/"+
						Long.toString(entity2.getKey().getId()) +"/\">Confirm</a><br>");
					}
				}
			 }
	}%>
	<%	
	datastore = DatastoreServiceFactory.getDatastoreService();
	gaeQuery = new Query("friendship");
	pq = datastore.prepare(gaeQuery);
	out.println("<h3>Your friends are :</h3>");
	for (Entity entity : pq.asIterable()) 
	{
		
			if (entity.getProperty("SendID").toString().equals(currentUserID) 
					&& entity.getProperty("isApproved").toString().equals("true"))
			{
				
				String resID = entity.getProperty("RecID").toString();
				gaeQuery = new Query("users");
			    pq = datastore.prepare(gaeQuery);
				for (Entity entity2 : pq.asIterable())
				{
					if(Long.toString(entity2.getKey().getId()).equals(resID))
					{
						out.println(entity2.getProperty("name") +"<br>");
					}
				}
			}
			else if (entity.getProperty("RecID").toString().equals(currentUserID) 
					&& entity.getProperty("isApproved").toString().equals("true"))
			{		
				String sendID = entity.getProperty("SendID").toString();
				gaeQuery = new Query("users");
			    pq = datastore.prepare(gaeQuery);
				for (Entity entity2 : pq.asIterable())
				{
					if(Long.toString(entity2.getKey().getId()).equals(sendID))
					{
						out.println(entity2.getProperty("name") +"<br>");
					}
				}
			}
	}%>	
</body>
</html>