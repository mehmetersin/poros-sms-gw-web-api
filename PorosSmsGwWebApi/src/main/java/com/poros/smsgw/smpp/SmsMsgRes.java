package com.poros.smsgw.smpp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.poros.smsgw.ws.BaseRes;

@XmlRootElement
public class SmsMsgRes extends BaseRes implements Serializable {

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
