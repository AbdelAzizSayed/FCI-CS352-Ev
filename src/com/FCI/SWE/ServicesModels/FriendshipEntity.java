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

	boolean isApproved;
	/**
	 * 
	 * 
	 * @param email
	 * 				the friend email to be matched with the suitable ID and added to the 
	 * 				data store 
	 * @return boolean value 
	 */
	public boolean sendFriendReq(String email)
	{
		isApproved = false ;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("friendship");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity friendship = new Entity("friendship", list.size() + 1);
		
		long ResID = UserEntity.getUserIDByEmail(email);
		if(ResID == -1)
			return false ;
		
		friendship.setProperty("SendID",User.getCurrentActiveUser().getId());
		friendship.setProperty("RecID", ResID);
		friendship.setProperty("isApproved", isApproved);
		
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
	public boolean accpetFriendReq(String friendID)
	{
		isApproved = true ;
		System.out.println(friendID);
		String currentUserID = Long.toString(User.currentActiveUser.getId());
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();// connect to DB
		Query gaeQuery = new Query("friendship");// defining the Query
		PreparedQuery pq = datastore.prepare(gaeQuery);// excuting the query
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("RecID").toString().equals(currentUserID) && 
					entity.getProperty("SendID").toString().equals(friendID))
			{
				entity.setProperty("isApproved", isApproved);//set the approving flag to true
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
	/**
	 * this function is used to get the ids and names of friends in the
	 * friend request list 
	 * @return arraylist of map and each map contains user data(name and id)
	 */
	public ArrayList<Map> getFriendIDsInReq() 
	{
		ArrayList<Map> al = new ArrayList();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("friendship");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String currentUserID = Long.toString(User.currentActiveUser.getId());
		
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
						Map friend = new HashMap();
						friend.put("name",entity2.getProperty("name") );
						friend.put("id", entity2.getKey().getId());
						al.add(friend);
					}
				}
			 }
		}
		return al ;
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
		String currentUserID = Long.toString(User.currentActiveUser.getId());
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
							al.add((String) entity2.getProperty("name"));
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
							al.add((String) entity2.getProperty("name"));
						}
					}
				}
		}
		return al;
	}	
}
