package customerEntry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	static Connection doConnect()
	{  
		Connection con=null;
		try {
			con=DriverManager.getConnection("jdbc:mysql://localhost/javaproject","root","");
			System.out.println("Connected");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	public static void main(String args[])
	{
		doConnect();
	}
	
}
