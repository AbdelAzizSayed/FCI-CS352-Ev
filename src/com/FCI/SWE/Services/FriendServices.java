package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.ServicesModels.FriendEntity;
import com.FCI.SWE.ServicesModels.GroupEntity;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)

public class FriendServices 
{
	@POST
	@Path("/addFriend")
	public String addFriend(@FormParam("email") String friendEmail)
	{	
		FriendEntity friendEntity = new FriendEntity();
		JSONObject json = new JSONObject();
		
		if(friendEntity.addFriend(friendEmail))
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
		
		return json.toJSONString();
	}	
}
