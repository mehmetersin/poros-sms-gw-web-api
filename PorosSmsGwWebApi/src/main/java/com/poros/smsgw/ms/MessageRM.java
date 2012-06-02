package com.poros.smsgw.ms;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class MessageRM implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int line) throws SQLException {
		MessageItem ms = new MessageItem();
		ms.setDestination(rs.getString("destination"));
		ms.setMessage(rs.getString("message"));
		ms.setSource(rs.getString("source"));
		ms.setId(rs.getInt("ID"));
		ms.setUserId(rs.getInt("userId"));
		ms.setMessageTransactionId(rs.getString("messageTransactionId"));
		ms.setMessageState(rs.getInt("messageState"));
		ms.setSendingStatus(rs.getInt("sendingStatus"));
		return ms;
	}

}
