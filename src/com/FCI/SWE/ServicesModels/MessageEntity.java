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
}
