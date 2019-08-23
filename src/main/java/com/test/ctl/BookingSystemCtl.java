package com.test.ctl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.test.ConnectionToDb;
import com.test.svc.BookingSystemSvc;


@Controller
public class BookingSystemCtl extends ConnectionToDb {
	
	private Connection conn;
	private java.sql.PreparedStatement ps;
	private ResultSet rs ;
	
	@Autowired
	private BookingSystemSvc bookingSystemSvc;
	
	@Value("${welcome.message:test}")
	private String message = "test";
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String checkConnection(Map<String, Object> map) throws Exception{
		
		this.conn = this.getConnection();
		this.ps = this.conn.prepareStatement("select curdate()");
		this.rs = this.ps.executeQuery();
		if(this.rs.next()){
			map.put("message", this.message);
		}
		return "boosys";
	}
	
	@RequestMapping(value = "/getCustomer", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getCustomer(@RequestBody String param) throws Exception {
		int id = 0;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		
		ObjectNode node = new ObjectMapper().readValue(param, ObjectNode.class);
		id = Integer.parseInt(node.get("customer_id").textValue());
		list = bookingSystemSvc.getCustomer(id);
		map.put("data", list);
		return list;
		
	}
	
	@RequestMapping(value = "/getTicket", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getTicket(@RequestBody String param) throws Exception {
		int id = 0;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		
		ObjectNode node = new ObjectMapper().readValue(param, ObjectNode.class);
		id = Integer.parseInt(node.get("ticket_id").textValue());
		list = bookingSystemSvc.getTicket(id);
		map.put("data", list);
		return list;
	}
	
	@RequestMapping(value = "/updateQuantityTicket", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateQuantityTicket(@RequestBody String param) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int ticket_id = 0;
		int customer_id = 0;
		int buy = 0;
		ObjectNode node = new ObjectMapper().readValue(param, ObjectNode.class);
		ticket_id = Integer.parseInt(node.get("ticket_id").textValue());
		customer_id = Integer.parseInt(node.get("customer_id").textValue());
		buy = Integer.parseInt(node.get("buy").textValue());
		result = bookingSystemSvc.insert(customer_id, ticket_id, buy);
//		map.put("status", result);
		return result;
		
	}
	
	@RequestMapping(value = "/insertOrderTicket", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertOrderTicket(@RequestBody String param) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int ticket_id = 0;
		int customer_id = 0;
		int buy = 0;
		ObjectNode node = new ObjectMapper().readValue(param, ObjectNode.class);
		ticket_id = Integer.parseInt(node.get("ticket_id").textValue());
		customer_id = Integer.parseInt(node.get("customer_id").textValue());
		buy = Integer.parseInt(node.get("buy").textValue());
		result = bookingSystemSvc.insert(customer_id, ticket_id, buy);
//		map.put("status", result);
		return result;
		
	}
	
	@RequestMapping(value = "/getAllCustomer", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getAllCustomer() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		list = bookingSystemSvc.getAllCustomer();
		map.put("data", list);
		return list;
		
	}
	
	@RequestMapping(value = "/getAllTicket", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getAllTicket() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		list = bookingSystemSvc.getAllTicket();
		map.put("data", list);
		return list;
		
	}
	
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> getList(@RequestBody String param) throws Exception {
		String value = "";
		int flag = 0;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		
		ObjectNode node = new ObjectMapper().readValue(param, ObjectNode.class);
		value = node.get("value").textValue();
		flag = node.get("flag").asInt();
		
		if(flag == 1) {
			list = bookingSystemSvc.getListCustomer(value);
		}else{
			list = bookingSystemSvc.getListFilm(value);
		}
		map.put("data", list);
		return list;
	}


}
