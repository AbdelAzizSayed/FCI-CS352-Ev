package com.FCI.SWE.NotifCommand;

import com.FCI.SWE.Services.NotificationServices;
import com.FCI.SWE.ServicesModels.MessageEntity;

public class ReadMessageCommand implements NotifCommnad {

	MessageEntity me = new MessageEntity();
	public ReadMessageCommand(MessageEntity me) 
	{
		this.me = me ;
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
