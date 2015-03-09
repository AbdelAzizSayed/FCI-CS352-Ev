package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class FriendshipEntity {

	boolean isApproved;
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
}
