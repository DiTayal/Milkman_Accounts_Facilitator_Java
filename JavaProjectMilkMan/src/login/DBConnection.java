package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static Connection doConnect()
	{
		//no need of loading and registering driver
		Connection  con=null;
		try
		{
			con=DriverManager.getConnection("jdbc:mysql://localhost/javaproject","root","");
			System.out.println("COnnected");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return con;
	}
		
	public static void main(String args[])
	{
		doConnect();
	}
}
