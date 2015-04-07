package com.FCI.SWE.NotifCommand;

import com.FCI.SWE.ServicesModels.FriendshipEntity;

public class AcceptFriendCommand implements NotifCommnad {

	private FriendshipEntity fe ;
	public AcceptFriendCommand(FriendshipEntity fe)
	{
		this.fe = fe ;
	}
	@Override
	public boolean excute() 
	{
		if(fe.accpetFriendReq())
			return true ;
		return false ;
	}

}
