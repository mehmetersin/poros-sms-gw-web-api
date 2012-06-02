package com.poros.smsgw.ws;

import com.poros.smsgw.util.Constants;

public class BaseRes {

	private int resultCode = Constants.RESULT_CODE_SUCCESS;
	
	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	
}
