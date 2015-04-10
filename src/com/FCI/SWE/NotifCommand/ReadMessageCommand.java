package com.FCI.SWE.NotifCommand;

import com.FCI.SWE.Services.NotificationServices;
import com.FCI.SWE.ServicesModels.MessageEntity;

public class ReadMessageCommand implements NotifCommnad {

	MessageEntity me;
	public ReadMessageCommand(String netID) 
	{
		me = new MessageEntity(netID);
	}

	@Override
	public boolean excute() {
		
		if(me.readMessage())
		{
			NotificationServices.response = me.getMesg();
			return true ;
		}
		return false ;
	}

}
