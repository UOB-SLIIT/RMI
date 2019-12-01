package com.perisic.beds.predefinemethods;
/**
 * Contains with methods wich are interact with DB operations
 * 
 * @author Kavindi
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.perisic.beds.connection.DBconnection;

public class DBMethods {
	
//Method to get resultSet by executing Query
    public static ResultSet getREsultSet(String aq) throws Exception {
       
        Connection conn=DBconnection.connect();
        Statement state=conn.prepareStatement(aq);
      
      return  state.executeQuery(aq);
    }
     
//Method to execute Query
    public static boolean execute(String aq) throws Exception {
       
        Connection conn=DBconnection.connect();
        Statement state=conn.createStatement();
      
      return  state.executeUpdate(aq)>0;
      
    }
   
//Method to get Prepared Statement for the query
    public static PreparedStatement prepare(String qry)throws Exception {
    	Connection conn=DBconnection.connect();
    	PreparedStatement pst=conn.prepareStatement(qry);
    	return pst;
    	
    }
}
