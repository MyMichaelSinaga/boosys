package com.test.svc;

import java.util.List;
import java.util.Map;

public interface BookingSystemSvc {
	public List<Map<String, Object>> getCustomer(int customer_id) throws Exception;
	public List<Map<String, Object>> getTicket(int ticket_id) throws Exception;
	public Map<String, Object> insert(int customer_id, int ticket_id, int buy) throws Exception;
	public List<Map<String, Object>> getAllCustomer() throws Exception;
	public List<Map<String, Object>> getAllTicket() throws Exception;
	public List<Map<String, Object>> getListCustomer(String value) throws Exception;
	public List<Map<String, Object>> getListFilm(String value) throws Exception;
}
