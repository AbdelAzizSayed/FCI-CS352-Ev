package com.FCI.SWE.NotifCommand;

import com.FCI.SWE.Services.NotificationServices;
import com.FCI.SWE.ServicesModels.GroupMessageEntity;


public class ReadGroupMessageCommand implements NotifCommnad {

	GroupMessageEntity me = new GroupMessageEntity();
	public ReadGroupMessageCommand(GroupMessageEntity me) 
	{
		this.me = me ;
	}

	@Override
	public boolean excute() {
		
		if(me.readGroupMessage())
		{
			NotificationServices.response = me.getMesg();
			return true ;
		}
		return false ;
	}

}
