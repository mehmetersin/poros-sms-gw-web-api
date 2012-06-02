package com.poros.smsgw.ms;

import java.io.Serializable;
import java.sql.Timestamp;

import com.poros.smsgw.smpp.SmsMsgReq;
import com.poros.smsgw.util.Constants;

public class MessageItem implements Serializable {

	private String message;
	private String destination;
	private String source;
	private int sendingStatus = Constants.SendingStatus_New;
	private int messageState = Constants.MessageState_NoInfo;
	private int userId;
	private String messageTransactionId;
	private int id;
	private int retryCount = 0;

	public MessageItem(SmsMsgReq request) {
		setMessage(request.getMessage());
		setDestination(request.getDestination());
		setSource(request.getSource());
	}

	public MessageItem() {
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessageTransactionId() {
		return messageTransactionId;
	}

	public void setMessageTransactionId(String messageTransactionId) {
		this.messageTransactionId = messageTransactionId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getSendingStatus() {
		return sendingStatus;
	}

	public void setSendingStatus(int sendingStatus) {
		this.sendingStatus = sendingStatus;
	}

	public int getMessageState() {
		return messageState;
	}

	public void setMessageState(int messageState) {
		this.messageState = messageState;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String toString() {
		return "Id:" + getId() + "Source:" + source + "Message:" + message
				+ "Destination:" + destination;
	}

}
