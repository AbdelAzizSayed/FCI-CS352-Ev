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

public class MessageEntity 
{
	private String SenderID;
	private String RecID;
	private String Mesg;
	private boolean isRead ;
	private String messageID ;
	
	public MessageEntity() {}
	public MessageEntity(String messageID) 
	{
		this.messageID = messageID ;
	}
	public String getMesg()
	{
		return Mesg ;
	}
	public MessageEntity(String SenderID, String RecID, String Mesg, boolean isRead)
	{
		this.SenderID = SenderID ;
		this.RecID = RecID ;
		this.Mesg = Mesg ;
		this.isRead = isRead ;
	}
	public boolean SendMessage()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("messages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity messages = new Entity("messages", list.size() + 1);
		
		messages.setProperty("SendID",SenderID);
		messages.setProperty("RecID",RecID);
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
	public boolean readMessage()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();// connect to DB
		Query gaeQuery = new Query("messages");// defining the Query
		PreparedQuery pq = datastore.prepare(gaeQuery);// excuting the query
		for (Entity entity : pq.asIterable()) 
		{
			if (Long.toString(entity.getKey().getId()).equals(messageID))
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
	
	public ArrayList<Map> getMessages() 
	{
		ArrayList<Map> al = new ArrayList();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("messages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String currentUserID = Long.toString(User.currentActiveUser.getId());
		
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("RecID").toString().equals(currentUserID) &&
					entity.getProperty("isRead").toString().equals("false"))
			{
				String sendID = entity.getProperty("SendID").toString();
				gaeQuery = new Query("users");
			    pq = datastore.prepare(gaeQuery);
				for (Entity entity2 : pq.asIterable())
				{
					if(Long.toString(entity2.getKey().getId()).equals(sendID))
					{
						Map message = new HashMap();
						message.put("name",entity2.getProperty("name") );
						message.put("id", entity.getKey().getId());//the id of the message record
						al.add(message);
					}
				}
			 }
		}
		return al ;
	}

}
