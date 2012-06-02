package com.poros.smsgw.rest;

import com.poros.smsgw.smpp.QueryMsgReq;
import com.poros.smsgw.smpp.QueryMsgRes;
import com.poros.smsgw.smpp.SmsMsgRes;

public interface RestSmsServiceIntf {

	public SmsMsgRes sendSms(String username, String password,String source,String destination,String message
		);
	QueryMsgRes queryMessageState(String username, String password, String id);
}
