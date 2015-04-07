package com.FCI.SWE.Controller;

import java.util.Map;

import javax.ws.rs.GET;
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
public class NotificationController 
{
	@GET
	@Path("/notificationReaction/{notID}/{notType}")
	//notifcicationID is messageID or friendID depending on the type of the notificacion 
	public Response notificationReaction(@PathParam("notID") String notID, @PathParam("notType") String notType)
	{
		if (User.getCurrentActiveUser() == null) {
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		String serviceUrl = "http://localhost:8888/rest/notificationReaction";
		String urlParameters = "notID=" + notID + "&notType=" + notType ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser ();
		Object obj ;
		try
		{
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject)obj ;
			if(object.get("Status").equals("OK"))
					return Response.ok(object.get("Response")).build();
			
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@GET
	@Path("/showNotifications")
	@Produces("text/html")
	public Response Notifications() {
		
		if (User.getCurrentActiveUser() == null) {
				return Response.ok(new Viewable("/jsp/error")).build();		
			}
		String retJson = Connection.connect(
				
				"http://localhost:8888/rest/showNotifications", ""
				,"POST"
						+ "", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			Map notifications = (Map)object.get("notifications");
			return Response.ok(new Viewable("/jsp/notifications", notifications)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	

}
