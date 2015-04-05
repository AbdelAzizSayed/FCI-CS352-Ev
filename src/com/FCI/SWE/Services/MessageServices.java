package com.FCI.SWE.Services;

import java.io.BufferedReader;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.FriendshipEntity;
import com.FCI.SWE.ServicesModels.GroupEntity;
import com.FCI.SWE.ServicesModels.MessageEntity;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.google.appengine.api.urlfetch.HTTPRequest;


@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class MessageServices {

	@POST
	@Path("/sendMessage")
	public String SendMesg(@FormParam("message") String Messages,
			@FormParam("recEmail") String Email) {
		
		JSONObject object = new JSONObject();
		User Sender = User.getCurrentActiveUser();
		long SenderID = Sender.getId();
		UserEntity Rec = null;
		long recID = Rec.getUserIDByEmail(Email);
		MessageEntity Mesg = new MessageEntity() ;
		JSONObject json = new JSONObject();
		if(Mesg.SendMessage(Messages , SenderID,recID))
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
		return json.toJSONString();
	}
	
	@POST
	@Path("/groupMesgEmails")
	public String createGroupMesg(@FormParam("chatName") String chatName,
			@FormParam("recEmails") String Emails) 
	{
		System.out.println(chatName + " " + Emails);
		JSONObject object = new JSONObject();
		
		MessageEntity Mesg = new MessageEntity() ;
		JSONObject json = new JSONObject();
		
		if(Mesg.createGroupChat(chatName, Emails))
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
		
		return json.toJSONString();
	}
	@POST
	@Path("/searchConversation")
	public String SendGroupMesg(@FormParam("chatName") String chatName)
	{
		JSONObject object = new JSONObject();
		
		MessageEntity Mesg = new MessageEntity() ;
		JSONObject json = new JSONObject();
		
		if(Mesg.getChatConversation(chatName))
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
		
		return json.toJSONString();
	}
}
