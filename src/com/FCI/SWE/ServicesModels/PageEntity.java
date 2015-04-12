package com.FCI.SWE.ServicesModels;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class PageEntity
{
	private String pageName;
	private String pageType;
	private String pageCategory;
	private int pagelikes;
	private int pagereach;
	private String createdEmail;
	
	public PageEntity() {}
	
	public PageEntity(String pageName, String pageType, String pageCategory, String createdEmail) 
	{
		this.pageName = pageName ;
		this.pageType = pageType ;
		this.pageCategory = pageCategory ;
		pagelikes = 0;
		pagereach = 0;
		this.createdEmail = createdEmail;
	}
	
	public boolean createPage()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity page = new Entity("pages");//the id in db will be a random number
	
		page.setProperty("pageName" , pageName);
		page.setProperty("pageType", pageType);
		page.setProperty("pageCategory" , pageCategory );
		page.setProperty("pagelikes" , pagelikes );
		page.setProperty("pagereach" , pagereach );
		page.setProperty("createdEmail" , createdEmail );
		
		datastore.put(page);
			
		return true ;
	}
}
