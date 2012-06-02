package com.poros.smsgw.smpp;

import com.poros.smsgw.ms.MessageItem;

public interface SmppAdapterIntf {

	public String sendSMS(MessageItem message);
	

	public int queryMessage(MessageItem request);
}
