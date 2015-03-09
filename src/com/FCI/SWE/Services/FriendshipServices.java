package com.FCI.SWE.Services;

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
	
	@POST
	@Path("/sendFriendReq")
	public String sendFriendReq(@FormParam("email") String email) {
		
		FriendshipEntity friendship = new FriendshipEntity();
		JSONObject json = new JSONObject();
		if(friendship.sendFriendReq(email))
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
		return json.toJSONString();
	}	
	@POST
	@Path("/acceptFriendReq")
	public String acceptFriendReq(@FormParam("friendID") String friendID) {
		FriendshipEntity friendship = new FriendshipEntity();
		JSONObject json = new JSONObject();
		
		if(friendship.accpetFriendReq(friendID))
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
		return json.toJSONString();
	}		

}
