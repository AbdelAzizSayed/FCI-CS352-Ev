package com.FCI.SWE.ServicesModels;

import com.FCI.SWE.NotifCommand.NotifCommnad;

public class PagePostBuilder implements PostBuilder 
{
	PagePostEntity post ;
	public PagePostBuilder() 
	{
		post = new PagePostEntity();
		post.setPostType("page");
		post.setIsShare(false);
	}
	@Override
	public void buildPrivacy(Privacy p) 
	{
		post.setPrivacy(p);
	}
	@Override
	public void buildPostOwner(String owner) 
	{
		post.setOwner(owner);
	}

	@Override
	public void buildPostContent(String content) 
	{
		post.setContent(content);
	}
	@Override
	public PostEntity createPost() 
	{
		return post;
	}
	@Override
	public long savePost() 
	{
		return post.savePost();
	}
	@Override
	public void buildPostFeeling(String feeling) {}
}