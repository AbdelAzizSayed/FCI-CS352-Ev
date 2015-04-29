package com.FCI.SWE.ServicesModels;

public class UserPostBuilder implements PostBuilder 
{
	UserPostEntity post ;

	public UserPostBuilder() 
	{
		post = new UserPostEntity();
		post.setPostType("user");
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
	public void buildPostFeeling(String feeling) 
	{
		post.setFeeling(feeling);
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
}