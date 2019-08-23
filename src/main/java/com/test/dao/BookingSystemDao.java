package com.test.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface BookingSystemDao {
	public List<Map<String, Object>> getCustomer(int customer_id) throws Exception;
	public void setConnection(Connection conn) throws Exception;
	public void closeStatement() throws Exception;
	public List<Map<String, Object>> getTicket(int ticket_id) throws Exception;
	public boolean insert(int customer_id, int ticket_id, int buy) throws Exception;
	public boolean updateQuantity(int ticket_id, int quantity) throws Exception;
	public int getQuantity(int ticket_id) throws Exception;
	public int getMaxOrder() throws Exception;
	public List<Map<String, Object>> getAllCustomer() throws Exception;
	public List<Map<String, Object>> getAllTicket() throws Exception;
	public List<Map<String, Object>> getListCustomer(String value) throws Exception;
	public List<Map<String, Object>> getListFilm(String value) throws Exception;
}
