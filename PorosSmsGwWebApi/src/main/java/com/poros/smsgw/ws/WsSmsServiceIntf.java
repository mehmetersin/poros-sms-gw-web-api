package com.poros.smsgw.ws;

import javax.jws.WebService;

import com.poros.smsgw.smpp.QueryMsgReq;
import com.poros.smsgw.smpp.QueryMsgRes;
import com.poros.smsgw.smpp.SmsMsgReq;
import com.poros.smsgw.smpp.SmsMsgRes;

@WebService
public interface WsSmsServiceIntf {

    public SmsMsgRes sendSms(SmsMsgReq request);
    public QueryMsgRes queryMessageState(QueryMsgReq request);
}
