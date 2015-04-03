package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class FriendEntity {
	
	boolean isFriend;
	
	public Boolean addFriend(String email)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("friendship");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		long idFriend = UserEntity.getUserIDByEmail(email);
		long idUser = User.currentActiveUser.getId();
		
		Entity friend = new Entity("friendship" , list.size() + 1);
		friend.setProperty("userID", idUser);
		friend.setProperty("FriendID", idFriend);
		friend.setProperty("isApproved", false);
		
		if(datastore.put(friend).isComplete())
			return true;
		else 
			return false;
		
	}
}
