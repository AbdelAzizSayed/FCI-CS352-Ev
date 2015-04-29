package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.ws.rs.FormParam;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.cloud.sql.jdbc.PreparedStatement;


public class PostEntity 
{
		protected String postContent ;
		Privacy privacy ;
		protected boolean isShare; // shared post or not
		protected String postType;
		protected String postOwner;
		public void setPrivacy(Privacy privacy) 
		{
			this.privacy = privacy ;
		}
		public void setOwner(String postOwner) 
		{
			this.postOwner = postOwner ;
		}
		public void setContent(String content) 
		{
			this.postContent = content ;
		}
		public void setPostType(String postType) 
		{			
			this.postType = postType ;
		}
		public void setIsShare(Boolean isShare) 
		{			
			this.isShare = isShare ;
		}			
		public boolean sharePost(String postID, String currentEmail) 
		{
				isShare = true ;
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Query gaQuery = new Query("posts");
				PreparedQuery pq = datastore.prepare(gaQuery);
				for(Entity e : pq.asIterable())
				{
					if(e.getKey().getId() == Long.parseLong(postID))
					{
						postType = e.getProperty("postType").toString();
						if(e.getProperty("isShare").toString().equals("true"))//share a shared Post
						{
							postID = e.getProperty("sharedPostID").toString();
						}
						break ;
					}
				}
				Entity sharedPost = new Entity ("posts");
				sharedPost.setProperty("owner" ,currentEmail );//who added the post or shared it
				sharedPost.setProperty("sharedPostID", postID);
				sharedPost.setProperty("likes", 0);	
				sharedPost.setProperty("likers", "");//who liked the post
				sharedPost.setProperty("isShare", isShare);//here it's a shared post		
				sharedPost.setProperty("postType", postType);
				if(datastore.put(sharedPost).isComplete())
				{
					return true;
				}
				else
					return false ;						
		}
		public boolean likePost(String postID ,String currentEmail) //we send the liker email so as not to like twice
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Query gaeQuery = new Query("posts");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			String postOwnerEmail = "" ;
			for(Entity e : pq.asIterable())
			{
				if(Long.toString(e.getKey().getId()).equals(postID))
				{
					if(e.getProperty("likers").toString().contains(currentEmail))
					{
						return false;
					}
					if(e.getProperty("postType").toString().equals("page"))
					{
						DatastoreService datastore2 = DatastoreServiceFactory.getDatastoreService();
						Query gaeQuery2 = new Query("pages");
						PreparedQuery pq2 = datastore.prepare(gaeQuery2);
						for(Entity page : pq.asIterable())
						{
							if(page.getProperty("pageName").toString().equals(e.getProperty("owner").toString()))
							{
								postOwnerEmail = page.getProperty("createdEmail").toString();
								break ;
							}
						}
					}
					else
					{
						postOwnerEmail = e.getProperty("owner").toString();
					}
					int likes =Integer.parseInt(e.getProperty("likes").toString());
					String likers = (String)e.getProperty("likers");
					e.setProperty("likes", likes + 1);
					e.setProperty("likers", likers + "," + currentEmail);
					datastore.put(e);
					break ;
				}
			}
			UserEntity ue = new UserEntity();
			String actionPerformerName = ue.getUserNameByEmail(currentEmail);
			Entity notification = new Entity ("notifications") ;
			notification.setProperty("RecEmail" , postOwnerEmail);
			notification.setProperty("actionPerformer" , actionPerformerName); // who did the like
			notification.setProperty("notiClass" , "DiscardPostLikeCommand" );
			notification.setProperty("notifID" , postID );
			datastore.put(notification);
			return true ;
		}
}