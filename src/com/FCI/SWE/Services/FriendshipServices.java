package com.FCI.SWE.Services;

import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.ServicesModels.FriendshipEntity;
import com.FCI.SWE.ServicesModels.GroupEntity;
@Path("/")
@Produces(MediaType.TEXT_PLAIN)

public class FriendshipServices {
	/**
	 * Send Friend Request is used to send a request to anotehr user
	 * 
	 * @param email
	 *            provide the email of the user to send friend request to 
	 * @return Status json
	 */
	@POST
	@Path("/sendFriendReq")
	public String sendFriendReq(@FormParam("email") String email) {
		
		FriendshipEntity friendship = new FriendshipEntity();
		JSONObject json = new JSONObject();
		if(friendship.sendFriendReq(email))
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
//		System.out.println(json.toJSONString());
		return json.toJSONString();
	}	
	/**
	 * Accept Friend Request is used to accept a friend request send by another user
	 * 
	 * @param friendID
	 *            provide the friend ID to accept friend request with
	 * @return Status json
	 */
	@POST
	@Path("/acceptFriendReq")
	public String acceptFriendReq(@FormParam("friendID") String friendID) {
		FriendshipEntity friendship = new FriendshipEntity();
		JSONObject json = new JSONObject();
		
		if(friendship.accpetFriendReq(friendID))
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
	//	System.out.println(json.toJSONString());
		return json.toJSONString();
	}
	/**
	 * A Service that gets the list of friends through calling
	 * an entity function
	 * @return ArrayList Of Friends Name
	 */
	@POST
	@Path("/friendList")
	public String friendList() {
		JSONObject object = new JSONObject();
		FriendshipEntity fe = new FriendshipEntity();
		ArrayList <String> friendList = fe.getFriendsNameList();
		object.put("friendList", friendList);
		return object.toString();
	}	

}
