package com.poros.smsgw.ws;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.poros.smsgw.engine.MessageSenderEngineIntf;
import com.poros.smsgw.ms.MessageItem;
import com.poros.smsgw.ms.MessageStoreAdapterIntf;
import com.poros.smsgw.smpp.QueryMsgReq;
import com.poros.smsgw.smpp.QueryMsgRes;
import com.poros.smsgw.smpp.SmsMsgReq;
import com.poros.smsgw.smpp.SmsMsgRes;
import com.poros.smsgw.user.User;
import com.poros.smsgw.user.UserAdapterIntf;
import com.poros.smsgw.util.Constants;

@WebService(endpointInterface = "com.poros.smsgw.ws.WsSmsServiceIntf")
public class SmsServiceWsImpl implements WsSmsServiceIntf {

	public UserAdapterIntf userAdapter;
	public MessageStoreAdapterIntf messageStoreAdapter;
	public MessageSenderEngineIntf senderEngine;

	public MessageSenderEngineIntf getSenderEngine() {
		return senderEngine;
	}

	@Autowired
	public void setSenderEngine(MessageSenderEngineIntf senderEngine) {
		this.senderEngine = senderEngine;
	}

	public MessageStoreAdapterIntf getMessageStoreAdapter() {
		return messageStoreAdapter;
	}

	@Autowired
	public void setMessageStoreAdapter(
			MessageStoreAdapterIntf messageStoreAdapter) {
		this.messageStoreAdapter = messageStoreAdapter;
	}

	public UserAdapterIntf getUserAdapter() {
		return userAdapter;
	}

	@Autowired
	public void setUserAdapter(UserAdapterIntf userAdapter) {
		this.userAdapter = userAdapter;
	}

	@Override
	public SmsMsgRes sendSms(SmsMsgReq request) {

		SmsMsgRes response = new SmsMsgRes();

		boolean check = getUserAdapter().checkUser(request.getUsername(),
				request.getPassword());

		if (!check) {
			response.setResultCode(Constants.RESULT_INVALID_USER);
			return response;
		}
		User user = getUserAdapter().getUser(request.getUsername());

		MessageItem message = new MessageItem(request);
		message.setUserId(user.getUserId());

		int mtid = getMessageStoreAdapter().insertMessage(message);
		response.setId(mtid);

		message.setId(mtid);
		senderEngine.sendSMS(message);

		return response;
	}

	@Override
	public QueryMsgRes queryMessageState(QueryMsgReq request) {

		QueryMsgRes response = new QueryMsgRes();

		boolean check = getUserAdapter().checkUser(request.getUsername(),
				request.getPassword());

		if (!check) {
			response.setResultCode(Constants.RESULT_INVALID_USER);
			return response;
		}

		User user = getUserAdapter().getUser(request.getUsername());

		MessageItem item = null;
		try {
			item = messageStoreAdapter.queryMessage(request.getMessageId());
			if (item == null) {
				response.setResultCode(Constants.RESULT_INVALID_MESSAGE_ID);
				return response;
			}
		} catch (Exception e) {
			response.setResultCode(Constants.RESULT_INVALID_MESSAGE_ID);
			return response;
		}

		if (item.getUserId() != user.getUserId()) {
			response.setResultCode(Constants.RESULT_INVALID_MESSAGE_ID);
			return response;
		}

		response.setSendingStatus(item.getSendingStatus());
		response.setMessageState(item.getMessageState());
		response.setId(item.getId());

		return response;
	}

}
