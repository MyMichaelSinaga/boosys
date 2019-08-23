package com.test.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.test.dao.BookingSystemDao;

import java.sql.PreparedStatement;

@Repository
public class BookingSystemDaoImpl implements BookingSystemDao {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	@Override
	public void setConnection(Connection conn) throws Exception {
		this.conn = conn;
	}

	@Override
	public void closeStatement() throws Exception {
		if(this.ps!=null){
			this.ps.close();
		}
		if(this.rs!=null){
			this.rs.close();
		}
		
	}
	
	@Override
	public List<Map<String, Object>> getCustomer(int customer_id) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.ps = this.conn.prepareStatement("select customer_id, name,phone,email from customer where customer_id = ?");
			this.ps.setInt(1, customer_id);
			this.rs = this.ps.executeQuery();
			while(this.rs.next()){
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("customer_id", this.rs.getString(1));
				map.put("name", this.rs.getString(2));
				map.put("phone", this.rs.getString(3));
				map.put("email", this.rs.getString(4));
				list.add(map);
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.closeStatement();
			
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getTicket(int ticket_id) throws Exception {
		List<Map<String, Object>> list= new ArrayList<Map<String,Object>>();
		try{
			this.ps = this.conn.prepareStatement("select * from ticket where ticket_id = ? and if (curdate() > date, 1, 0) = 0");
			this.ps.setInt(1, ticket_id);
			this.rs = this.ps.executeQuery();
			while(this.rs.next()){
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("ticket_id", this.rs.getString(1));
				map.put("film", this.rs.getString(2));
				map.put("date", this.rs.getString(3));
				map.put("start_time", this.rs.getString(4));
				map.put("finish_time", this.rs.getString(5));
				map.put("quantity", this.rs.getString(6));
				list.add(map);
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.closeStatement();
		}
		return list;
	}
	
	@Override
	public boolean insert(int customer_id, int ticket_id, int buy) throws Exception {
		boolean result = false;
		try{
			this.ps = this.conn.prepareStatement("INSERT INTO order_ticket (customer_id, ticket_id, buy) VALUES (?,?,?)");
			this.ps.setInt(1, customer_id);
			this.ps.setInt(2, ticket_id);
			this.ps.setInt(3, buy);
			this.ps.executeUpdate();
			result = true;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.closeStatement();
		}
		return result;
	}
	
	@Override
	public boolean updateQuantity(int ticket_id, int quantity) throws Exception {
		boolean result = false;
		try{
			this.ps = this.conn.prepareStatement("update ticket set quantity = ? where ticket_id = ?");
			this.ps.setInt(1, quantity);
			this.ps.setInt(2, ticket_id);
			this.ps.executeUpdate();
			result = true;
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.closeStatement();
		}
		return result;
	}
	
	@Override
	public int getQuantity(int ticket_id) throws Exception {
		int result = 0;
		try{
			this.ps = this.conn.prepareStatement("select quantity from ticket where ticket_id = ?");
			this.ps.setInt(1, ticket_id);
			this.rs = this.ps.executeQuery();
			while (this.rs.next()) {
				result = this.rs.getInt(1);
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.closeStatement();
		}
		return result;
	}
	
	@Override
	public int getMaxOrder() throws Exception {
		int result = 0;
		try{
			this.ps = this.conn.prepareStatement("select max(order_id) from order_ticket");
			this.rs = this.ps.executeQuery();
			while (this.rs.next()) {
				result = this.rs.getInt(1);
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.closeStatement();
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getAllCustomer() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try{
			this.ps = this.conn.prepareStatement("select customer_id, name,phone,email from customer");
			this.rs = this.ps.executeQuery();
			while(this.rs.next()){
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("customer_id", this.rs.getString(1));
				map.put("name", this.rs.getString(2));
				map.put("phone", this.rs.getString(3));
				map.put("email", this.rs.getString(4));
				list.add(map);
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.closeStatement();
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getListCustomer(String value) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try{
			String like = "%" +value+"%";
			this.ps = this.conn.prepareStatement("select customer_id, name from customer where customer_id like ?");
			this.ps.setString(1, like);
			this.rs = this.ps.executeQuery();
			while(this.rs.next()){
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("customer_id", this.rs.getString(1));
				map.put("name", this.rs.getString(2));
				list.add(map);
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.closeStatement();
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getListFilm(String value) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try{
			String like = "%" +value+"%";
			this.ps = this.conn.prepareStatement("select ticket_id, film from ticket where film like ?");
			this.ps.setString(1, like);
			this.rs = this.ps.executeQuery();
			while(this.rs.next()){
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("ticket_id", this.rs.getString(1));
				map.put("film", this.rs.getString(2));
				list.add(map);
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.closeStatement();
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getAllTicket() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try{
			this.ps = this.conn.prepareStatement("select * from ticket");
			this.rs = this.ps.executeQuery();
			while(this.rs.next()){
				Map<String, Object> map = new HashMap<String,Object>();
				map.put("ticket_id", this.rs.getString(1));
				map.put("film", this.rs.getString(2));
				map.put("date", this.rs.getString(3));
				map.put("start_time", this.rs.getString(4));
				map.put("finish_time", this.rs.getString(5));
				map.put("quantity", this.rs.getString(6));
				list.add(map);
			}
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.closeStatement();
		}
		return list;
	}

	
	

}
