package com.perisic.beds.connection;
/**
 * Create Connection with the database
 * 
 * @author Kavindi De Silva
 *
 */

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {

//Method to get connection with the database
	public static  Connection connect() {
        Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=(Connection ) DriverManager.getConnection("jdbc:mysql://localhost:3306/abcdatabase?useTimezone=true&serverTimezone=UTC","root","");
			System.out.println("Connection Success");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		return conn;
	}

}
