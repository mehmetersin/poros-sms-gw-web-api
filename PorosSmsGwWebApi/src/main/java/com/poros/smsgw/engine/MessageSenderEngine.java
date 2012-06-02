package com.poros.smsgw.engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.poros.smsgw.ms.MessageItem;
import com.poros.smsgw.ms.MessageStoreAdapterIntf;
import com.poros.smsgw.smpp.SmppAdapterIntf;
import com.poros.smsgw.util.Constants;

public class MessageSenderEngine implements MessageSenderEngineIntf {

	public MessageStoreAdapterIntf messageStoreAdapter;
	private SmppAdapterIntf smppAdapter;

	public MessageStoreAdapterIntf getMessageStoreAdapter() {
		return messageStoreAdapter;
	}

	@Autowired
	public void setMessageStoreAdapter(
			MessageStoreAdapterIntf messageStoreAdapter) {
		this.messageStoreAdapter = messageStoreAdapter;
	}

	public SmppAdapterIntf getSmppAdapter() {
		return smppAdapter;
	}

	@Autowired
	public void setSmppAdapter(SmppAdapterIntf smppAdapter) {
		this.smppAdapter = smppAdapter;
	}

	@Async
	@Override
	public void sendSMS(MessageItem message) {
		try {
			String ti = smppAdapter.sendSMS(message);
			messageStoreAdapter.updateMessageState(message.getId(),
					Constants.SendingStatus_Finished_Success, ti, false);
		} catch (Exception e) {
			messageStoreAdapter.updateMessageState(message.getId(),
					Constants.SendingStatus_InRetry, null, true);
		}

	}



}
