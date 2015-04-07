package com.FCI.SWE.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.NotifCommand.AcceptFriendCommand;
import com.FCI.SWE.NotifCommand.NotifCommnad;
import com.FCI.SWE.NotifCommand.ReadGroupMessageCommand;
import com.FCI.SWE.NotifCommand.ReadMessageCommand;
import com.FCI.SWE.ServicesModels.FriendshipEntity;
import com.FCI.SWE.ServicesModels.GroupMessageEntity;
import com.FCI.SWE.ServicesModels.MessageEntity;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class NotificationServices 
{
	static public String response = ""; //the returned value to jsp page
	
	@POST
	@Path("/notificationReaction")
	public String notificationsReaction(@FormParam("notID") String notID,
									@FormParam("notType") String notType)
	{
		JSONObject json = new JSONObject();
		String currentUserID = Long.toString(User.currentActiveUser.getId());
		
		NotifCommnad ncom = null ;
		if(notType.equals("1"))//accept friend request
		{
			FriendshipEntity fe = new FriendshipEntity(notID);
			ncom = new AcceptFriendCommand(fe);
			response = "Friend Request confirmed !";
		}
		else if(notType.equals("2"))//read message
		{
			MessageEntity me = new MessageEntity(notID);
			ncom = new ReadMessageCommand(me);//here the message is gonna be read inside and stored into variable respones
		}
		else if(notType.equals("3"))//read group message
		{
			GroupMessageEntity gme = new GroupMessageEntity(notID);
			ncom = new ReadGroupMessageCommand(gme);		
		}			
		
		if(ncom.excute())
		{
			json.put("Status", "OK");
			json.put("Response", response);
		}
			
		else
			json.put("Status", "Failed");
		return json.toJSONString();
	}	
	
	@POST
	@Path("/showNotifications")
	public String notifications() 
	{
		
		JSONObject object = new JSONObject();
		FriendshipEntity fe = new FriendshipEntity() ;
		MessageEntity me = new MessageEntity() ;
		Map <String, ArrayList> notifications = new HashMap();
		ArrayList <Map> messages = me.getMessages();
		ArrayList <Map> friendReq = fe.getFriendIDsInReq();
		GroupMessageEntity gme = new GroupMessageEntity() ;
		ArrayList <Map> groupMessages = gme.getGroupMessages();
		notifications.put("friendReq", friendReq);//friend req notifications
		notifications.put("messages", messages);//messages notifications 
		notifications.put("groupMessages", groupMessages);//messages notifications 
		object.put("notifications", notifications);
		System.out.println(object.toString());
		return object.toString();
	}
}
