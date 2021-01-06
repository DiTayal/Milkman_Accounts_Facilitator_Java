/**
 * Sample Skeleton for 'SignupView.fxml' Controller Class
 */

package Signup;
import login.DBConnection;
import java.net.URL;
import java.util.ResourceBundle;

//import com.mysql.jdbc.PreparedStatement;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;

public class SignupViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private TextField txtUid;

    @FXML
    private PasswordField txtPwd;

    @FXML
    void doSave(ActionEvent event) 
    {
    	ResultSet table = null;//widen scope
    	String uid=txtUid.getText();
    	PreparedStatement pst;
		try
		   {			
			pst = con.prepareStatement("select * from user where uid=?");
			pst.setString(1, uid);
	        table= pst.executeQuery();
	        //System.out.println(table);
		   }
		catch (SQLException e1)
			{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			}
    	 try 
    	 {
			if(table.next())//can use same thing in  myjdbc vaal project
			{
				Alert al=new Alert(AlertType.ERROR);//.error or warning etc       	 
				al.setTitle("Invalid Username");
				al.setHeaderText("Change UserName");//by default- header is type of alert
				al.setContentText(" USERNAME ALREDY EXISTS");
				al.show();
			}
			else
			{	
				try {
					String pwd=txtPwd.getText();
					System.out.println(pwd);
					pst=con.prepareStatement("insert into user values(?,?)");
					pst.setString(1, uid);
					pst.setString(2, pwd);
					pst.executeUpdate();
					
					Alert al=new Alert(AlertType.INFORMATION);//.error or warning etc       	 
			    	al.setTitle("Status ");
			    //	al.setHeaderText("Change UserName");//by default- header is type of alert
			    	al.setContentText("Successfully signed in");
			    	al.show();
					
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    Connection con;
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	con=DBConnection.doConnect();
       
    }
}
