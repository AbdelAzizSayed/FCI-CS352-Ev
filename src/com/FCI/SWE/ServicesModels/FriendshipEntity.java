package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class FriendshipEntity {

	String currentUserEmail;
	String friendEmail;
	

	public FriendshipEntity(){}
	public FriendshipEntity(String friendEmail) 
	{
		this.friendEmail = friendEmail ;
	}
	public FriendshipEntity(String currentUserEmail , String friendEmail )
	{
		this.currentUserEmail = currentUserEmail ;
		this.friendEmail = friendEmail ;
	}
	/**
	 * 
	 * 
	 * @param email
	 * 				the friend email to be matched with the suitable ID and added to the 
	 * 				data store 
	 * @return boolean value 
	 */
	public boolean sendFriendReq()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity friendship = new Entity("notifications");//the id in db will be a random number
		friendship.setProperty("notiClass" , "AcceptFriendCommand"); //name of class to handle reaction
		friendship.setProperty("notifID", currentUserEmail);
		friendship.setProperty("RecEmail" ,friendEmail );//friendEmail
		
		if(datastore.put(friendship).isComplete())
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}
	/**
	 * this function takes a friend ID and accept the friend req. with him by setting the
	 * is approved flag to true value
	 * @param friendID
	 * 					the friendID the user wanna be a friend with
	 * @return true or false 
	 */
	public boolean accpetFriendReq()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();// connect to DB
			Entity entity = new Entity("friendship");
			entity.setProperty("SendEmail", friendEmail);
			entity.setProperty("RecEmail", User.currentActiveUser.getEmail());
			datastore.put(entity);
			datastore = DatastoreServiceFactory.getDatastoreService();
			Query gaeQuery = new Query("notifications");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			for (Entity entity2 : pq.asIterable())
			{
				if (entity2.getProperty("RecEmail").toString().equals(User.currentActiveUser.getEmail())
						&& entity2.getProperty("notifID").toString().equals(friendEmail) )
				{
					datastore.delete(entity2.getKey());
				}
			}
			return true ;

	}
	/**
	 * this function is used to get the names if friends in the
	 * friend request list 
	 * @return arraylist of string each represents user name
	 */	
	public ArrayList<String> getFriendsNameList() 
	{
		ArrayList<String> al = new ArrayList();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("friendship");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String currentUserEmail = User.currentActiveUser.getEmail();
		for (Entity entity : pq.asIterable()) 
		{
				if (entity.getProperty("SendEmail").toString().equals(currentUserEmail) 
					||	entity.getProperty("RecEmail").toString().equals(currentUserEmail) )
				{
					
					String SenderEmail = entity.getProperty("SendEmail").toString();
					String RecEmail = entity.getProperty("RecEmail").toString();
					gaeQuery = new Query("users");
				    pq = datastore.prepare(gaeQuery);
					for (Entity entity2 : pq.asIterable())
					{
						if (entity.getProperty("SendEmail").toString().equals(currentUserEmail) 
								&&(entity2.getProperty("email").toString().equals(RecEmail)))
						{
							al.add((String) entity2.getProperty("name"));
						}
						else if (entity2.getProperty("email").toString().equals(SenderEmail) 
								&&(entity.getProperty("RecEmail").toString().equals(currentUserEmail)))
						{
							al.add((String) entity2.getProperty("name"));
						}
					}
				}
		}
		return al;
	}	
}
