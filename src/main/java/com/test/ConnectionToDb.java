package com.test;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

public class ConnectionToDb {
	
	@Autowired
	private DataSource dataSource;
	
	protected  Connection conn = null;
	
	public void connect() throws Exception{
		try {
			this.conn = dataSource.getConnection();
		} catch ( Exception e){
			e.printStackTrace();
			throw new Exception(e);
		} 
	}
	
	public Connection getConnection() throws Exception{
		try {
			if(this.conn == null)
				this.connect();
			else if(this.conn.isClosed())
				this.connect();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this.conn;
	}
	
	public void closeConnection() throws Exception{
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
