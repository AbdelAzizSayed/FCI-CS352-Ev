package com.FCI.SWE.Controller;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;

@Path("/")
@Produces("text/html")
public class PageController 
{
	@GET
	@Path("/pageOptions")
	public Response pageOptions() {
		return Response.ok(new Viewable("/jsp/pageOptions")).build();
	}
	
	@POST
	@Path("/createPage")
	public Response createPage() {
		return Response.ok(new Viewable("/jsp/pageCreation")).build();
	}
	@POST
	@Path("/pageInfo")
	public Response pageInfo(@FormParam("pageName") String pageName,
			@FormParam("pageType") String pageType, 
			@FormParam("pageCategory") String pageCategory) {
		
		if (User.getCurrentActiveUser() == null) 
		{
			return Response.ok(new Viewable("/jsp/error")).build();
		}
		if(pageName.equals("")  || pageType.equals("") || pageCategory.equals(""))
		{
			return null ;	
		}
		
		String urlParameters = "pageName=" + pageName + "&pageType=" + pageType + "&pageCategory=" + pageCategory;
		String retJson = Connection.connect(
				"http://localhost:8888/rest/pageInfo"
				, urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/pageOptions")).build();
	}
	
}
