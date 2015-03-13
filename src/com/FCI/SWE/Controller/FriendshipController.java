package com.FCI.SWE.Controller;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;

@Path("/")
@Produces("text/html")
public class FriendshipController {

	/**
	 * Action function to render add friend page of application, this page contains a
	 * form with add friend button and a text field to take the friend email as an input 
	 * 
	 * @return add friend page
	 */
	@GET
	@Path("/addFriend")
	public Response addFriendPage(){
		
		if (User.getCurrentActiveUser() == null) {
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		return Response.ok(new Viewable ("/jsp/friends/addFriend")).build();
	}
	/**
	 * Action function to render friends page that contains all friend requests and friend list 
	 * 
	 * @return friends page
	 */	
	@GET
	@Path("/friendListAndReq")
	public Response showList(){
		
		if (User.getCurrentActiveUser() == null) {
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		return Response.ok(new Viewable ("/jsp/friends/friends")).build();
	}	
	/**
	 * a controller that calls the send friend request service and provides it with the 
	 * suitable parameters 
	 * @param email 
	 * 				the friend email to add
	 * @return JSON status
	 */
	@POST
	@Path("/sendFriendReq")
	public Response friendReq (@FormParam("email") String email){
		String serviceUrl = "http://swe2project15.appspot.com/rest/sendFriendReq";
		String urlParameters = "email=" + email;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return Response.ok("Friend Request Sent!").build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok("Error sending friend Request !").build();
	}
	/**
	 * a controller that calls the accept friend request service and provides it with the 
	 * suitable parameters
	 * @param friendID
	 * 				the friend ID to accept his friend request
	 * @return JSON status
	 */
	@GET
	@Path("/acceptFriendReq/{friendID}")
	public Response acceptFriendReq(@PathParam("friendID") String friendID)
	{
		String serviceUrl = "http://swe2project15.appspot.com/rest/acceptFriendReq";
		String urlParameters = "friendID=" + friendID ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser ();
		Object obj ;
		try
		{
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject)obj ;
			if(object.get("Status").equals("OK"))
				return Response.ok("Friend Request Confirmed!").build();
			
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
