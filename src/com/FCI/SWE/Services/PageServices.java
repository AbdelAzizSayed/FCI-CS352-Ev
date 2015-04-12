package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.PageEntity;


@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class PageServices {
	
	@POST
	@Path("/pageInfo")
	public String pageInfo(@FormParam("pageName") String pageName,
			@FormParam("pageType") String pageType, 
			@FormParam("pageCategory") String pageCategory) {
		
		JSONObject object = new JSONObject();
		
		User currentUser = User.getCurrentActiveUser();
		String createdEmail = currentUser.getEmail();
		
		PageEntity page = new PageEntity(pageName, pageType, pageCategory, createdEmail);
		
		JSONObject json = new JSONObject();
		if(page.createPage())
			json.put("Status", "OK");
		else
			json.put("Status", "Failed");
		
		return json.toJSONString();
	}
}
