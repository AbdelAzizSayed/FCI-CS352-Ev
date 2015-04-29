package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class UserPostEntity extends PostEntity
{
	private String feeling ;
	private String Custom ;
	private String postedBy ;
	
	public UserPostEntity() {}
	public void setFeeling(String feeling) 
	{
		this.feeling = feeling ;
	}
	public long savePost()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
		Entity post = new Entity("posts");
		post.setProperty("owner" ,postOwner );//who added the post
		post.setProperty("postContent",postContent );
		post.setProperty("privacy", privacy.getPrivacy());
		post.setProperty("feeling", feeling);
		post.setProperty("likes", 0);	
		post.setProperty("likers", "");//who liked the page
		if(privacy.getPrivacy().equals("Custom"))// if the privacy is Custom			
			post.setProperty("Custom",privacy.getCustom());
		post.setProperty("isShare", isShare);//here it's not a shared page
		post.setProperty("postType", postType);
		post.setProperty("postedBy", "");
		
		if(datastore.put(post).isComplete())
		{
			return post.getKey().getId();
		}
		else
			return -1 ;		
	}
	public ArrayList<Map> getTimeLine(String currentEmail)
	{
		ArrayList<Map> al = new ArrayList<Map>() ;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query ("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		for(Entity e : pq.asIterable())
		{
			if(e.getProperty("owner").toString().equals(currentEmail))
			{
				if(e.getProperty("isShare").toString().equals("true")) 
						                                               // for shared posts
				{
					String sharedPostID = e.getProperty("sharedPostID").toString();
					for(Entity e2 : pq.asIterable())
					{
						if(Long.toString(e2.getKey().getId()).equals(sharedPostID) && e.getProperty("postType").toString().equals("user") )
						{
							UserEntity ue = new UserEntity();
							Map <String ,String>  post = new HashMap();
							String theUserWhoShared = ue.getUserNameByEmail(currentEmail);
							String postowner = ue.getUserNameByEmail((e2.getProperty("owner").toString()));
							post.put("postowner", theUserWhoShared + " Shared " + postowner + " 's post");
							post.put("postContent", e2.getProperty("postContent").toString());
							if(!e2.getProperty("feeling").toString().equals("--"))//if there's a feeling
							{
								post.put("feeling", "__ feeling " + e2.getProperty("feeling"));
							}
							else
								post.put("feeling", "");
							
							post.put("postID", Long.toString(e.getKey().getId()));//this is used for the the like and share link
							post.put("likes",e.getProperty("likes").toString() + " people liked this");
							al.add(post);
						}
						else if(Long.toString(e2.getKey().getId()).equals(sharedPostID) && e.getProperty("postType").toString().equals("page"))
						{
							UserEntity ue = new UserEntity();
							Map <String ,String>  post = new HashMap();
							String theUserWhoShared = ue.getUserNameByEmail(currentEmail);
							String postowner = (e2.getProperty("owner").toString());							
							post.put("postowner",   theUserWhoShared + " Shared "+ postowner + " page's post ");
							post.put("postContent", e2.getProperty("postContent").toString());
							post.put("postID", Long.toString(e.getKey().getId()));//this is used for the the like and share link
							post.put("likes", e.getProperty("likes").toString() + " people liked this");
							al.add(post);
						}
					}
				}
				else // normal posts
				{
					UserEntity ue = new UserEntity();
					Map <String ,String>  post = new HashMap();
					String postowner = ue.getUserNameByEmail((e.getProperty("owner").toString()));
					
					if(!e.getProperty("postedBy").toString().equals(""))
					{
						String posterdBy = ue.getUserNameByEmail((e.getProperty("postedBy").toString()));
						post.put("postowner", " " + posterdBy + " posted this to your timeline" );
					}
					else
						post.put("postowner", postowner + " posted this.");
					post.put("postContent", e.getProperty("postContent").toString());
					if(!e.getProperty("feeling").toString().equals("--"))//if there's a feeling
					{
						post.put("feeling", "__ feeling " + e.getProperty("feeling"));
					}
					else
						post.put("feeling", "");
					
					post.put("postID", Long.toString(e.getKey().getId()));//this is used for the the like and share link
					post.put("likes",e.getProperty("likes").toString() + " people liked this");
					al.add(post);
				}
			}
		}
		return al ;			
	}
	public ArrayList<Map> getAnAccountTimeLine(String currentEmail,
			String accountEmail) 
	{
		ArrayList<Map> al = new ArrayList<Map>() ;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query ("posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for(Entity e : pq.asIterable())
		{	
			if(e.getProperty("owner").toString().equals(accountEmail) )
			{
				FriendshipEntity fe = new FriendshipEntity();
				if(e.getProperty("isShare").toString().equals("true")) 	//for shared posts
				{
					String sharedPostID = e.getProperty("sharedPostID").toString();
					for(Entity e2 : pq.asIterable())
					{
						String owner = e2.getProperty("owner").toString();
						if(Long.toString(e2.getKey().getId()).equals(sharedPostID) )
						{
							if(e.getProperty("postType").toString().equals("user") && (e2.getProperty("privacy").toString().equals("Public") 
									||(e2.getProperty("privacy").toString().equals("Private")&& fe.isFriend(accountEmail, currentEmail)
							||(e2.getProperty("privacy").toString().equals("Custom")&& e2.getProperty("Custom").toString().contains(currentEmail)))))
							{
								UserEntity ue = new UserEntity();
								Map <String ,String>  post = new HashMap();
								String theUserWhoShared = ue.getUserNameByEmail((e.getProperty("owner").toString()));
								String postowner = ue.getUserNameByEmail(currentEmail);
								post.put("postowner", theUserWhoShared + " Shared " + postowner + " 's post");
								post.put("postContent", e2.getProperty("postContent").toString());
								if(!e2.getProperty("feeling").toString().equals("--"))//if there's a feeling
								{
									post.put("feeling", "__ feeling " + e2.getProperty("feeling"));
								}
								else 
									post.put("feeling", "");
								
								post.put("postID", Long.toString(e.getKey().getId()));//this is used for the the like and share link
								post.put("likes",e.getProperty("likes").toString() + " people liked this");
								al.add(post);
							}
							else if(e.getProperty("postType").toString().equals("page"))
							{
								Query gaeQuery2 = new Query ("pages");
								PreparedQuery pq2 = datastore.prepare(gaeQuery2);
								String pageLikers = "";//for Private post privacy
								for(Entity e3: pq2.asIterable())
								{
									if(e3.getProperty("pageName").toString().equals(owner) )
									{
										pageLikers = e3.getProperty("likers").toString();
									}
									break ;
								}
								if(e2.getProperty("privacy").toString().equals("Public") 
										|| (e2.getProperty("privacy").toString().equals("Private") && pageLikers.contains(currentEmail)))
								{
									UserEntity ue = new UserEntity();
									Map <String ,String>  post = new HashMap();
									String theUserWhoShared = ue.getUserNameByEmail((e.getProperty("owner").toString()));
									post.put("postowner",   theUserWhoShared + " Shared "+ owner + " page's post ");
									post.put("postContent", e2.getProperty("postContent").toString());

									post.put("postID", Long.toString(e.getKey().getId()));//this is used for the the like and share link
									post.put("likes",e.getProperty("likes").toString() + " people liked this");
									al.add(post);
								}
							}
						}
					}
				}
				else // normal posts
				{
					if(e.getProperty("privacy").toString().equals("Public") 
							||(e.getProperty("privacy").toString().equals("Private")&& fe.isFriend(accountEmail, currentEmail)
					||(e.getProperty("privacy").toString().equals("Custom")&& e.getProperty("Custom").toString().contains(currentEmail))))
					{
						UserEntity ue = new UserEntity();
						Map <String ,String>  post = new HashMap();
						String postowner = ue.getUserNameByEmail((e.getProperty("owner").toString()));
						if(!e.getProperty("postedBy").toString().equals(""))
						{
							String posterdBy = ue.getUserNameByEmail((e.getProperty("postedBy").toString()));
							post.put("postowner", " " + posterdBy + " posted this to " + postowner );
						}
						else
							post.put("postowner", postowner + " posted this.");
						post.put("postContent", e.getProperty("postContent").toString());
						if(!e.getProperty("feeling").toString().equals("--"))//if there's a feeling
						{
							post.put("feeling", "__ feeling " + e.getProperty("feeling"));
						}
						else
							post.put("feeling", "");
						
						post.put("postID", Long.toString(e.getKey().getId()));//this is used for the the like and share link
						post.put("likes", e.getProperty("likes").toString() + " people liked this");
						al.add(post);
					}
				}
			}
		}
		return al ;
	}
	public long createPostToFriend(String currentEmail, String postContent, String friendEmail, String feeling) 
	{
		postType = "user" ;
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
		Entity post = new Entity("posts");
		post.setProperty("owner", friendEmail );
		post.setProperty("postContent",postContent );
		post.setProperty("privacy", "Public");
		post.setProperty("feeling", feeling);
		post.setProperty("likes", 0);	
		post.setProperty("likers", "");//who liked the page
		post.setProperty("isShare", false);//here it's not a shared page
		post.setProperty("postType", postType);
		post.setProperty("postedBy",currentEmail );
		
		if(datastore.put(post).isComplete())
		{
			return post.getKey().getId();
		}
		else
			return -1 ;
	}
	public ArrayList<Map> getNewsFeed(String currentEmail) 
	{
		ArrayList <Map> al = new ArrayList();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaQuery = new Query("posts");
		PreparedQuery pq = datastore.prepare(gaQuery);
		for(Entity post : pq.asIterable())
		{
				FriendshipEntity fe = new FriendshipEntity();
				if(post.getProperty("isShare").toString().equals("true")) 	//for shared posts
				{
					String sharedPostID = post.getProperty("sharedPostID").toString();
					for(Entity sharedPost : pq.asIterable())
					{
						String owner = sharedPost.getProperty("owner").toString();
						if(Long.toString(sharedPost.getKey().getId()).equals(sharedPostID) )
						{
							if(post.getProperty("postType").toString().equals("user") && (sharedPost.getProperty("privacy").toString().equals("Public") 
									||(sharedPost.getProperty("privacy").toString().equals("Private")&& fe.isFriend(owner, currentEmail)
							||(sharedPost.getProperty("privacy").toString().equals("Custom")&& sharedPost.getProperty("Custom").toString().contains(currentEmail)))))
							{
								UserEntity ue = new UserEntity();
								Map <String ,String>  newsFeedPost = new HashMap();
								String theUserWhoShared = ue.getUserNameByEmail((post.getProperty("owner").toString()));
								String postowner = ue.getUserNameByEmail(currentEmail);
								newsFeedPost.put("postowner", theUserWhoShared + " Shared " + postowner + " 's post");
								newsFeedPost.put("postContent", sharedPost.getProperty("postContent").toString());
								if(!sharedPost.getProperty("feeling").toString().equals("--"))//if there's a feeling
								{
									newsFeedPost.put("feeling", "__ feeling " + sharedPost.getProperty("feeling"));
								}
								else 
									newsFeedPost.put("feeling", "");
								
								newsFeedPost.put("postID", Long.toString(post.getKey().getId()));//this is used for the the like and share link
								newsFeedPost.put("likes",post.getProperty("likes").toString() + " people liked this");
								al.add(newsFeedPost);
							}
							else if(post.getProperty("postType").toString().equals("page"))
							{
								Query gaeQuery2 = new Query ("pages");
								PreparedQuery pq2 = datastore.prepare(gaeQuery2);
								String pageLikers = "";//for Private post privacy
								for(Entity page: pq2.asIterable())
								{
									if(page.getProperty("pageName").toString().equals(owner) )
									{
										pageLikers = page.getProperty("likers").toString();
									}
									break ;
								}
								if(sharedPost.getProperty("privacy").toString().equals("Public") 
										|| (sharedPost.getProperty("privacy").toString().equals("Private") && pageLikers.contains(currentEmail)))
								{
									UserEntity ue = new UserEntity();
									Map <String ,String>  newsFeedPost = new HashMap();
									String theUserWhoShared = ue.getUserNameByEmail((post.getProperty("owner").toString()));
									newsFeedPost.put("postowner",   theUserWhoShared + " Shared "+ owner + " page's post ");
									newsFeedPost.put("postContent", sharedPost.getProperty("postContent").toString());

									newsFeedPost.put("postID", Long.toString(post.getKey().getId()));//this is used for the the like and share link
									newsFeedPost.put("likes",post.getProperty("likes").toString() + " people liked this");
									al.add(newsFeedPost);
								}
							}
						}
					}
				}
				else // normal posts
				{
					if(post.getProperty("postType").toString().equals("user"))
					{
						String owner = post.getProperty("owner").toString();
						if(post.getProperty("privacy").toString().equals("Public") 
								||(post.getProperty("privacy").toString().equals("Private")&& fe.isFriend(owner, currentEmail)
						||(post.getProperty("privacy").toString().equals("Custom")&& post.getProperty("Custom").toString().contains(currentEmail))))
						{
							UserEntity ue = new UserEntity();
							Map <String ,String>  newsFeedPost = new HashMap();
							String postowner = ue.getUserNameByEmail((post.getProperty("owner").toString()));
							if(!post.getProperty("postedBy").toString().equals(""))
							{
								String posterdBy = ue.getUserNameByEmail((post.getProperty("postedBy").toString()));
								newsFeedPost.put("postowner", " " + posterdBy + " posted this to " + postowner );
							}
							else
								newsFeedPost.put("postowner", postowner + " posted this.");
							newsFeedPost.put("postContent", post.getProperty("postContent").toString());
							if(!post.getProperty("feeling").toString().equals("--"))//if there's a feeling
							{
								newsFeedPost.put("feeling", "__ feeling " + post.getProperty("feeling"));
							}
							else
								newsFeedPost.put("feeling", "");
							
							newsFeedPost.put("postID", Long.toString(post.getKey().getId()));//this is used for the the like and share link
							newsFeedPost.put("likes", post.getProperty("likes").toString() + " people liked this");
							al.add(newsFeedPost);
						}						
					}
					else if(post.getProperty("postType").toString().equals("page"))
					{
							Query gaeQuery2 = new Query ("pages");
							PreparedQuery pq2 = datastore.prepare(gaeQuery2);
							String pageLikers = "";//for Private post privacy
							String owner = post.getProperty("owner").toString();
							for(Entity page: pq2.asIterable())
							{
								if(page.getProperty("pageName").toString().equals(owner) )
								{
									pageLikers = page.getProperty("likers").toString();
								}
								break ;
							}
							if(post.getProperty("privacy").toString().equals("Public") && pageLikers.contains(currentEmail)
									|| (post.getProperty("privacy").toString().equals("Private") && pageLikers.contains(currentEmail)))
							{
								if(!post.getProperty("seeners").toString().contains(currentEmail))
								{
									int nOfSeen = Integer.parseInt(post.getProperty("nOfSeen").toString());
									String seeners = post.getProperty("seeners").toString();
									
									post.setProperty("seeners" , seeners + currentEmail + "," );
									post.setProperty("nOfSeen" , nOfSeen + 1);
									datastore.put(post);
								}								
								Map <String ,String>  newsFeedPost = new HashMap();
								newsFeedPost.put("postowner",   owner + " posted this.");
								newsFeedPost.put("postContent", post.getProperty("postContent").toString());

								newsFeedPost.put("postID", Long.toString(post.getKey().getId()));//this is used for the the like and share link
								newsFeedPost.put("likes",post.getProperty("likes").toString() + " people liked this");
								al.add(newsFeedPost);
							}
						}						
					}
				}
				return al;
	}
}