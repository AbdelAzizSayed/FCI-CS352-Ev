package com.FCI.SWE.NotifCommand;

import com.FCI.SWE.Services.NotificationServices;
import com.FCI.SWE.ServicesModels.GroupMessageEntity;


public class ReadGroupMessageCommand implements NotifCommnad {

	private GroupMessageEntity me ;
	public ReadGroupMessageCommand(String notID) 
	{
		me = new GroupMessageEntity(notID); 
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
