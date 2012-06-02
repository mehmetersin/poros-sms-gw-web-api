package com.poros.smsgw.ms;

import java.util.List;

public interface MessageStoreAdapterIntf {

	public int insertMessage(MessageItem message);

	public List<MessageItem> queryMessagesForSmsSendingProcess();

	public void updateMessageState( int id,int state,String messageTi,boolean isError);

	public MessageItem queryMessage(int id);

	public void updateMessageSendingStatus(int id, int status);

	public void updateFinalMessageState(int id, int state);

	public void fixPendingMessages();

}
