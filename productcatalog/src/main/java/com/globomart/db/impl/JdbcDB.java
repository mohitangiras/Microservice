package com.globomart.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.globomart.db.CustomConnection;
import com.globomart.db.DB;
import com.globomart.productcatalog.Product;

public class JdbcDB implements DB<String, Product>{

	@Override
	public Product put(Product product) {
		Connection con = null;
		try {
			con = CustomConnection.getConnection();

			String productId = product.getProductId();

			String productName = product.getProductName();

			String productPrice = product.getProductId();

			PreparedStatement pStatement = con.prepareStatement("insert into product(PRODUCT_ID,PRODUCT_NAME, PRODUCT_PRICE) values(?,?,?)");

			pStatement.setString(1, productId);
			pStatement.setString(2, productName);
			pStatement.setString(3, productPrice);
			pStatement.execute();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		finally {
			try {
				if(con != null && !con.isClosed())
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public Product get(String key) {
		Connection con = null;
		Product product = null;
		try {
			con = CustomConnection.getConnection();

			PreparedStatement pStatement = con.prepareStatement("select PRODUCT_ID,PRODUCT_NAME, PRODUCT_PRICE from product where PRODUCT_ID=?");

			pStatement.setString(1, key);
			ResultSet result = pStatement.executeQuery();
			if(result.next())
			{
				String productId = result.getString("PRODUCT_ID");

				String productName = result.getString("PRODUCT_NAME");

				double productPrice = result.getDouble("PRODUCT_PRICE");
				
				product = new Product(productId, productName, productPrice);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		finally {
			try {
				if(con != null && !con.isClosed())
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return product;
	}

	@Override
	public Product remove(String key) {
		Connection con = null;
		Product product = null;
		try {
			con = CustomConnection.getConnection();

			PreparedStatement pStatement = con.prepareStatement("delete from product where PRODUCT_ID=?");

			pStatement.setString(1, key);
			pStatement.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		finally {
			try {
				if(con != null && !con.isClosed())
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return product;
	}

	@Override
	public List<Product> getAll() {
		Connection con = null;
		List<Product> products = new ArrayList<>();
		try {
			con = CustomConnection.getConnection();

			PreparedStatement pStatement = con.prepareStatement("select PRODUCT_ID,PRODUCT_NAME, PRODUCT_PRICE from product");

			ResultSet result = pStatement.executeQuery();
			while(result.next())
			{
				String productId = result.getString("PRODUCT_ID");

				String productName = result.getString("PRODUCT_NAME");

				double productPrice = result.getDouble("PRODUCT_PRICE");
				
				Product product = new Product(productId, productName, productPrice);
				products.add(product);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		finally {
			try {
				if(con != null && !con.isClosed())
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return products;
	}


}
