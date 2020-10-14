package com.globomart.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.globomart.config.Config;

public class CustomConnection {
	
	public static java.sql.Connection getConnection()
	{
		java.sql.Connection conn = null;
		try {
			Config configs = Config.getInstance();
			DriverManager.registerDriver(new org.h2.Driver());
			conn = DriverManager.getConnection (configs.getValue("db.url"), configs.getValue("db.user"),configs.getValue("db.password")); 
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return conn;
	}
	
	
	//Poor practice but for just making sure we have things ready for this demo app
	static {
		Statement st = null;
		Connection conn = null;
		try {
			conn = getConnection();
			st = conn.createStatement();
			st.execute("CREATE TABLE IF NOT EXISTS PRODUCT(PRODUCT_ID Number, PRODUCT_NAME varchar(255), PRODUCT_PRICE Number)");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
				try {
					if(!st.isClosed())
						st.close();
					if(conn != null && !conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		}
	}

}
