package com.poros.smsgw.rest;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.poros.smsgw.engine.MessageSenderEngineIntf;
import com.poros.smsgw.ms.MessageItem;
import com.poros.smsgw.ms.MessageStoreAdapterIntf;
import com.poros.smsgw.smpp.QueryMsgRes;
import com.poros.smsgw.smpp.SmsMsgReq;
import com.poros.smsgw.smpp.SmsMsgRes;
import com.poros.smsgw.user.User;
import com.poros.smsgw.user.UserAdapterIntf;
import com.poros.smsgw.util.Constants;

public class SmsServiceRestImpl implements RestSmsServiceIntf {

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

	@POST
	@Path("sendSms")
	public SmsMsgRes sendSms(@MatrixParam("username") String username,
			@MatrixParam("password") String password,
			@MatrixParam("source") String source,
			@MatrixParam("destination") String destination,
			@MatrixParam("message") String message) {
		SmsMsgReq request = new SmsMsgReq();
		request.setUsername(username);
		request.setPassword(password);
		request.setDestination(destination);
		request.setMessage(message);
		request.setSource(source);
		SmsMsgRes response = new SmsMsgRes();

		boolean check = getUserAdapter().checkUser(request.getUsername(),
				request.getPassword());

		if (!check) {
			response.setResultCode(Constants.RESULT_INVALID_USER);
			return response;
		}
		User user = getUserAdapter().getUser(request.getUsername());

		MessageItem messageItem = new MessageItem(request);
		messageItem.setUserId(user.getUserId());

		int mtid = getMessageStoreAdapter().insertMessage(messageItem);
		response.setId(mtid);

		messageItem.setId(mtid);
		senderEngine.sendSMS(messageItem);
		return response;
	}

	@GET
	@Path("queryMessageState")
	public QueryMsgRes queryMessageState(
			@MatrixParam("username") String username,
			@MatrixParam("password") String password,
			@MatrixParam("id") String id) {
		QueryMsgRes response = new QueryMsgRes();

		boolean check = getUserAdapter().checkUser(username, password);

		if (!check) {
			response.setResultCode(Constants.RESULT_INVALID_USER);
			return response;
		}

		User user = getUserAdapter().getUser(username);

		MessageItem item = null;
		try {
			item = messageStoreAdapter.queryMessage(Integer.valueOf(id));
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
