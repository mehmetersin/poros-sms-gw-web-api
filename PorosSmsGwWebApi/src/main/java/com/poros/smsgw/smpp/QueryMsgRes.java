package com.poros.smsgw.smpp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.poros.smsgw.ws.BaseRes;

@XmlRootElement
public class QueryMsgRes extends BaseRes implements Serializable {

	private int id;
	private int messageState;
	private int sendingStatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMessageState() {
		return messageState;
	}

	public void setMessageState(int messageState) {
		this.messageState = messageState;
	}

	public int getSendingStatus() {
		return sendingStatus;
	}

	public void setSendingStatus(int sendingStatus) {
		this.sendingStatus = sendingStatus;
	}

}
