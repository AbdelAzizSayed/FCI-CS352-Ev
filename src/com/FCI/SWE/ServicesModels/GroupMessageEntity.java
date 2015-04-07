package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class GroupMessageEntity 
{
	private String SenderID;
	private String RecID;
	private String Mesg;
	private boolean isRead ;
	private String groupMessageID ;
	
	public GroupMessageEntity() {}
	public GroupMessageEntity(String groupMessageID) 
	{
		this.groupMessageID = groupMessageID ;
	}
	public String getMesg()
	{
		return Mesg ;
	}
	public GroupMessageEntity(String SenderID, String RecID, String Mesg, boolean isRead)
	{
		this.SenderID = SenderID ;
		this.RecID = RecID ;
		this.Mesg = Mesg ;
		this.isRead = isRead ;
	}
	public boolean sendGroupMessage(String chatName , String message)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("groupChatNames");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		String Emails="";
		boolean check = false;
 
		for (Entity entity : pq.asIterable()) 
		{
			if (((String)entity.getProperty("chatName")).equals(chatName))
			{				
				Emails = (String) entity.getProperty("Emails");
				check = true;
				break;
			}
		}
		if (check == false)
			return false;
 
		User Sender = User.getCurrentActiveUser();
		String sender = Sender.getEmail();
 
		isRead = false; 
		Scanner s = new Scanner(Emails);
 
		s.useDelimiter(",");
		datastore = DatastoreServiceFactory
				.getDatastoreService();
		gaeQuery = new Query("groupMessages");
		pq = datastore.prepare(gaeQuery);		
		List<Entity> list2 = pq.asList(FetchOptions.Builder.withDefaults());
		int i = list2.size();
		while (s.hasNext())
		{
			String RecEmail = s.next();
			if(RecEmail.equals(sender))
				break ;
			i++;
			datastore = DatastoreServiceFactory
					.getDatastoreService();
			gaeQuery = new Query("groupMessages");
			pq = datastore.prepare(gaeQuery);
 
			Entity messages = new Entity("groupMessages", i);
			messages.setProperty("chatName" , chatName);
			messages.setProperty("SendEmail" , sender);		
			messages.setProperty("RecEmail" , RecEmail);
			messages.setProperty("Mesg", message);
			messages.setProperty("isRead", isRead);
			datastore.put(messages);
		}
		return true ;
	}
	public boolean createGroupChat (String name , String Emails)
	{		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("groupChatNames");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("chatName").equals(name))
			{
				return false;
			}	
		}		
		Entity groupChatName = new Entity("groupChatNames", list.size() + 1);
		User Sender = User.getCurrentActiveUser();
		Emails += "," + Sender.getEmail();
		
		groupChatName.setProperty("chatName", name);
		groupChatName.setProperty("Emails", Emails);
		
		if(datastore.put(groupChatName).isComplete())
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}
	public boolean readGroupMessage()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();// connect to DB
		Query gaeQuery = new Query("groupMessages");// defining the Query
		PreparedQuery pq = datastore.prepare(gaeQuery);// excuting the query
		for (Entity entity : pq.asIterable()) 
		{
			if (Long.toString(entity.getKey().getId()).equals(groupMessageID))
			{
				this.Mesg = (String) entity.getProperty("Mesg");
				entity.setProperty("isRead", true);//set the approving flag to true
				if(datastore.put(entity).isComplete())
				{
					return true ;
				}
				else
				{
					return false ;
				}
			}	
		}
		return false ;
	}
	public ArrayList<Map> getGroupMessages() 
	{
		ArrayList<Map> al = new ArrayList();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("groupMessages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String currentUserEmail = User.currentActiveUser.getEmail();
		
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("RecEmail").toString().equals(currentUserEmail) &&
					entity.getProperty("isRead").toString().equals("false"))
			{
				
				String chatName = entity.getProperty("chatName").toString();
				String SenderEmail = entity.getProperty("SendEmail").toString();				
				UserEntity ue = new UserEntity();
				String senderID = Long.toString(ue.getUserIDByEmail(SenderEmail));
				gaeQuery = new Query("users");
			    pq = datastore.prepare(gaeQuery);
				for (Entity entity2 : pq.asIterable())
				{
					if(Long.toString(entity2.getKey().getId()).equals(senderID))
					{
						Map message = new HashMap();
						message.put("senderName", entity2.getProperty("name"));
						message.put("id", entity.getKey().getId());//the id of the message record
						message.put("chatName",chatName );
						al.add(message);
					}
				}				 
			 }
		}
		return al ;
	}		
}
