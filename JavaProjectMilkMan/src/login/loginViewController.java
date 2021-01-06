/**
 * Sample Skeleton for 'loginView.fxml' Controller Class
 */

package login;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

//import com.mysql.jdbc.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.*;
public class loginViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtUid"
    private TextField txtUid; // Value injected by FXMLLoader
    
    @FXML
    private Button btnLogiinId;

    @FXML // fx:id="txtPwd"
    private PasswordField txtPwd; // Value injected by FXMLLoader

    
    URL url;
   	Media media;
   	MediaPlayer mediaplayer;
   	AudioClip audio;
       void playSong()
       {
       	
       	url=getClass().getResource("sound.mp3");
   		media=new Media(url.toString());
   		mediaplayer=new MediaPlayer(media);
   		mediaplayer.play();
   		
       }
       void playSound(){
       	url=getClass().getResource("crash.wav");
   		audio=new AudioClip(url.toString());
   		audio.play();
       }

       
    @FXML
    void doLogin(ActionEvent event) {
    	
    	playSong();
    	
    	String uid=txtUid.getText();// at times we convert stirng to int to send in db or compare with db data
    	String pwd=txtPwd.getText();
    	//System.out.println(uid+" "+pwd);
    	PreparedStatement pst;
		try {
			pst = con.prepareStatement("select*from user where uid=? and pwd=?");
			pst.setString(1, uid);
			pst.setString(2, pwd);
	    	//if give error import java.sql.* and remove myjdbc vaali prepared import
	    	ResultSet table=pst.executeQuery();
	    	if(table.next())
	    	{
	    		Alert al=new Alert(AlertType.INFORMATION);//.error or warning etc       	 
		    	al.setTitle("Status ");
		    //	al.setHeaderText("Change UserName");//by default- header is type of alert
		    	al.setContentText("Successfully logged in");
		    	
		    	Optional<ButtonType> result = al.showAndWait();
		    	
		    	if (result.get() == ButtonType.OK)
		    	{		    	  	
			    	Parent root;
					try
					{
						root = FXMLLoader.load(getClass().getClassLoader().getResource("dashboard/dashboardView.fxml"));
						Scene scene = new Scene(root);//created local Scene object
						Stage stage=new Stage();
						stage.setScene(scene);
						
						stage.show();
						
						Scene scene1=(Scene)btnLogiinId.getScene();//give id to button, on whose click getting new window
						   scene1.getWindow().hide();
						
						
					} 
					catch (IOException e)
					{
						e.printStackTrace();
					}    		
		    	}
		    	else //even if close the alert box- open dashboard?????????????????????
		    	{
		    	  System.exit(0);
		    	}	
		    	
		    	
		    	//to hide the opened window, ie window that further opened comboview window
				
				  /* Scene scene1=(Scene)loginView.getScene();
				   scene1.getWindow().hide();
			*/    	
		    	
	    	}
	    	else
	    	{
	    		Alert al=new Alert(AlertType.ERROR);//.error or warning etc       	 
		    	al.setTitle("Status ");
		    //	al.setHeaderText("Change UserName");//by default- header is type of alert
		    	al.setContentText("Sorry!We couldn't identify you");
		    	al.show();
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
