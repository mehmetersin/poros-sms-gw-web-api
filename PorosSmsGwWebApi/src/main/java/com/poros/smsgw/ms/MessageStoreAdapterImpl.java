package com.poros.smsgw.ms;

import java.sql.Timestamp;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.poros.smsgw.util.Constants;

public class MessageStoreAdapterImpl implements MessageStoreAdapterIntf {

	private SimpleJdbcInsert insertTemplate;
	private JdbcTemplate selectTemplate;

	private String selectForProcess = "select * from MESSAGES where sendingStatus="
			+ Constants.SendingStatus_New
			+ " or sendingStatus="
			+ Constants.SendingStatus_InRetry + " LIMIT ? ";

	private String updateSingleMessageState = "update MESSAGES set sendingStatus=?,messageTransactionId=? where ID =?";
	private String updateSingleMessageError = "update MESSAGES set sendingStatus=?,retryCount=retryCount+1 where ID =?";

	private String updateMessageProcessing = "update MESSAGES set sendingStatus=?,processingTimestamp=? where ID =?";
	
	private String updateMessageState = "update MESSAGES set messageState=? where messageTransactionId =?";

	private String selectMessageItem = "select * from MESSAGES where ID=?";
	
	private String updateFixPendingMessageItem = "update MESSAGES set sendingStatus=? where processingTimestamp<?";
	

	public void setDataSource(DataSource dataSource) {
		this.insertTemplate = new SimpleJdbcInsert(dataSource);
		if (insertTemplate.getTableName() != "MESSAGES") {
			insertTemplate.setTableName("MESSAGES");
			insertTemplate.setGeneratedKeyName("ID");
		}
		insertTemplate.compile();
		selectTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int insertMessage(MessageItem message) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(
				message);

		keyHolder = insertTemplate.executeAndReturnKeyHolder(namedParameters);
		return keyHolder.getKey().intValue();
	}

	@Override
	public java.util.List queryMessagesForSmsSendingProcess() {
		return (java.util.List) selectTemplate.query(selectForProcess,
				new Object[] { 500 }, new MessageRM());
	}

	@Override
	public void fixPendingMessages() {
	Date now = new Date();
	now.setHours(now.getHours()-2);
		
	 selectTemplate.update(updateFixPendingMessageItem,
				new Object[] { Constants.SendingStatus_InRetry,new Timestamp(now.getTime())});
	}
	
	@Override
	public MessageItem queryMessage(int id) {
		return (MessageItem) selectTemplate.queryForObject(selectMessageItem,
				new Object[] { id }, new MessageRM());
	}

	@Override
	public void updateMessageState(int id, int state, String messageTi,
			boolean isError) {
		if (isError) {
			selectTemplate.update(updateSingleMessageError, new Object[] {
					state, id });
		} else {
			selectTemplate.update(updateSingleMessageState, new Object[] {
					state, messageTi, id });
		}

	}

	@Override
	public void updateMessageSendingStatus(int id, int status) {
		Date now = new Date();
		now.setMinutes(now.getMinutes() + 10);
		selectTemplate.update(updateMessageProcessing, new Object[] { status,
				new Timestamp(now.getTime()), id });
	}
	
	@Override
	public void updateFinalMessageState(int id, int state) {
		int a =selectTemplate.update(updateMessageState, new Object[] {state, id
				 });
	}

}
