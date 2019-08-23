package com.test.svc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.ConnectionToDb;
import com.test.dao.BookingSystemDao;
import com.test.svc.BookingSystemSvc;

@Service
public class BookingSystemSvcImpl extends ConnectionToDb implements BookingSystemSvc {
	
	@Autowired
	private BookingSystemDao bookingSystemDao;
	
	@Override
	public List<Map<String, Object>> getCustomer(int customer_id) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.conn = this.getConnection();
			bookingSystemDao.setConnection(this.conn);
			list = bookingSystemDao.getCustomer(customer_id);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();;
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getTicket(int ticket_id) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.conn = this.getConnection();
			bookingSystemDao.setConnection(this.conn);
			list = bookingSystemDao.getTicket(ticket_id);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();;
		}
		return list;
	}
	
	@Override
	public Map<String, Object> insert(int customer_id, int ticket_id, int buy) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean insert = false;
		int quantity = 0;
		int max = 0;
		try{
			this.conn = this.getConnection();
			bookingSystemDao.setConnection(this.conn);
			quantity = bookingSystemDao.getQuantity(ticket_id);
			
			quantity -= buy;
			if(quantity < 0) {
				result.put("message", "Number of movie tickets not available for " + buy + " tickets!");
			}else{
				insert = bookingSystemDao.insert(customer_id, ticket_id, buy);
				if(insert){
					if(bookingSystemDao.updateQuantity(ticket_id, quantity)){
						max = bookingSystemDao.getMaxOrder();
						result.put("message", true);
						result.put("order_max", max);
					}
				}else{
					result.put("message", false);
				}
			}
		}catch (Exception e) {
			if(this.conn != null){
				this.conn.rollback();
			}
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();;
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getAllCustomer() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.conn = this.getConnection();
			bookingSystemDao.setConnection(this.conn);
			list = bookingSystemDao.getAllCustomer();
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();;
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getAllTicket() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.conn = this.getConnection();
			bookingSystemDao.setConnection(this.conn);
			list = bookingSystemDao.getAllTicket();
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();;
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getListCustomer(String value) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.conn = this.getConnection();
			bookingSystemDao.setConnection(this.conn);
			list = bookingSystemDao.getListCustomer(value);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();;
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getListFilm(String value) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.conn = this.getConnection();
			bookingSystemDao.setConnection(this.conn);
			list = bookingSystemDao.getListFilm(value);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();
		}
		return list;
	}
	

}
