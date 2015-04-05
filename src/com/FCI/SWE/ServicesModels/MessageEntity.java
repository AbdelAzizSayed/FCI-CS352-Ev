package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class MessageEntity 
{
	private long SenderID;
	private long RecID;
	private String Mesg;
	private boolean isRead ;
	
	public boolean createGroupChat (String name , String Emails)
	{		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("groupChatName");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		Entity groupChatName = new Entity("groupChatID", list.size() + 1);
		User Sender = User.getCurrentActiveUser();
		Emails += Sender.getEmail();
		
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
	public boolean SendMessage(String Mesg , long Sender , long Rec){

		isRead = false ;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("messages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity messages = new Entity("messages", list.size() + 1);
		
		messages.setProperty("SendID",Sender);
		messages.setProperty("RecID",Rec);
		messages.setProperty("Mesg", Mesg);
		messages.setProperty("isRead", isRead);
		
		if(datastore.put(messages).isComplete())
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}
	public Boolean getChatConversation(String chatName)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("groupChatID");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		User Sender = User.getCurrentActiveUser();
		
		for (Entity entity : pq.asIterable()) 
		{			
			String [] emails = entity.getProperty("Emails").toString().split(",");
			
			for (int i=0 ; i<emails.length ; i++)			
				if (entity.getProperty("chatName").toString().equals(chatName) && 
						emails[i].equals(Sender.getEmail())	)
				{
					return true;
				}
		}
		return false;
	}
}
