package com.poros.smsgw.job;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.poros.smsgw.cluster.ClusterListener;
import com.poros.smsgw.ms.MessageItem;
import com.poros.smsgw.ms.MessageStoreAdapterIntf;
import com.poros.smsgw.util.Constants;

public class LoadMessagesTask  extends BaseTask {


	public MessageStoreAdapterIntf messageStoreAdapter;

	public MessageStoreAdapterIntf getMessageStoreAdapter() {
		return messageStoreAdapter;
	}

	@Autowired
	public void setMessageStoreAdapter(
			MessageStoreAdapterIntf messageStoreAdapter) {
		this.messageStoreAdapter = messageStoreAdapter;
	}

	@Override
	public void run() {

		List messages = messageStoreAdapter.queryMessagesForSmsSendingProcess();
		logger.debug("Loading messages started. Message Count = {}", messages
				.size());

		for (Iterator iterator = messages.iterator(); iterator.hasNext();) {
			MessageItem ms = (MessageItem) iterator.next();
			ClusterListener.putMessageQueue(ms);
			messageStoreAdapter.updateMessageSendingStatus(ms.getId(),
					Constants.SendingStatus_Processing);

		}

		logger.debug("Loading Messages Finished");

	}
}
