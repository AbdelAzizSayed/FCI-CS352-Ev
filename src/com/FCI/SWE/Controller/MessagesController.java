package com.FCI.SWE.Controller; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sun.org.mozilla.javascript.internal.ast.ForInLoop;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.FriendshipEntity;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.urlfetch.HTTPRequest;

@Path("/")
@Produces("text/html")
public class MessagesController {
	@GET
	@Path("/sendMessage")
	public Response sendMesg() 
	{		
		if (User.getCurrentActiveUser() == null) {
				return Response.ok(new Viewable("/jsp/error")).build();
	}
		return Response.ok(new Viewable("/jsp/Messages")).build();
	}
	
	@POST
	@Path("/sendMesg")
	@Produces("text/html")
	public Response sendMessage(@FormParam("message") String Message,
			@FormParam("recEmail") String RecEmail) {
		
		//if there's no message typed do nothing
		if(Message.equals("")  || RecEmail.equals(""))
		{
			return null ;	
		}
		String urlParameters = "message=" + Message + "&recEmail=" + RecEmail;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/sendMessage"
				, urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok("Message Sent Successfully!").build();
	}
	
	@GET
	@Path("/sendGroupMessage")
	public Response sendGroupMessage()
	{
		if (User.getCurrentActiveUser() == null) {
			return Response.ok(new Viewable("/jsp/error")).build();
		}		
		return Response.ok(new Viewable("/jsp/GroupMessages")).build();
	}
	
	@POST
	@Path("/createMessageGroup")
	public Response createMessageGroup()
	{
		if (User.getCurrentActiveUser() == null) {
			return Response.ok(new Viewable("/jsp/error")).build();
		}		
		return Response.ok(new Viewable("/jsp/createMessageGroup")).build();
	}
	
	@POST
	@Path("/groupMsgEmails")
	@Produces("text/html")
	public Response createGroupMessage(@FormParam("chatName") String chatName,
			@FormParam("recEmails") String recEmails)
		{
			if (User.getCurrentActiveUser() == null) {
				return Response.ok(new Viewable("/jsp/error")).build();
			}		
		//if there's no message typed do nothing
			if(chatName.equals("")  || recEmails.equals(""))
			{
				return null ;	
			}
		String urlParameters = "chatName=" + chatName + "&recEmails=" + recEmails;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/groupMesgEmails"
				, urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/GroupMessages")).build();
	}
	
	@POST
	@Path("/groupMesg")
	@Produces("text/html")
	public Response sendGroupMessage(@FormParam("message") String message,
			@FormParam("chatName") String chatName) 
	{		
		//if there's no message typed do nothing
		if(message.equals("") || chatName.equals(""))
		{
			return null ;	
		}
		if (User.getCurrentActiveUser() == null) {
			return Response.ok(new Viewable("/jsp/error")).build();
		}		
		String urlParameters = "message=" + message + "&chatName=" + chatName;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/groupMesg"
				, urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok("Message Sent Successfully!").build();
	}
}
