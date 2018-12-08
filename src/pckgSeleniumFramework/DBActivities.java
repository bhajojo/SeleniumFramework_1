package pckgSeleniumFramework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBActivities {
    //*****************************************************************************************
    //*	Name		    : fConnectToXLS
    //*	Description	    : Function to make a connection to given XLS
	//*	Author		    : Anil Agarwal
    //*	Input Params	: string XLSPath - Path of the xls to query
    //*	Return Values	: Connection object having the connection
    //*****************************************************************************************
	public Connection fConnectToXLS(String XLSPath) {
		
		try {
			Connection conn = null;
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			//conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DriverId=22;Dbq= " + XLSPath + ";ReadOnly=0;");
			conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DriverId=22;Dbq= " + XLSPath + ";ReadOnly=0;");
			return conn;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		} 
	}
    //*****************************************************************************************
    //*	Name		    : fExecuteQuery
    //*	Description	    : Function to execute an query
    //*	Author		    : Anil Agarwal
    //*	Input Params	: string sSQL - SQL Query string
    //*                   Connection conn - Connection object
    //*	Return Values	: Resultset object having the results
    //*****************************************************************************************	
	public ResultSet fExecuteQuery(String sSQL, Connection conn ) {
		try {
			ResultSet rs = null;
			Statement stmnt = null;
			stmnt = conn.createStatement();            
			rs = stmnt.executeQuery(sSQL);
			return rs;

		} catch (SQLException eSQL) {
			System.err.println(eSQL);
			return null;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		} 
	}

}
