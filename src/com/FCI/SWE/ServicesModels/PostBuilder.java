package com.FCI.SWE.ServicesModels;

public interface PostBuilder 
{
	 void buildPrivacy(Privacy p);
	 void buildPostOwner(String owner);
	 void buildPostContent(String content);
	 void buildPostFeeling(String feeling);
	 PostEntity createPost();
	 long savePost();
	
}