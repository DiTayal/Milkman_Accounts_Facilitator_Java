package paymentCollection;

import login.DBConnection;
import sms.SST_SMS;

import java.sql.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Alert.AlertType;

public class paymentCollectionViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> listName;

    @FXML
    private Label lblDoe;

    @FXML
    private Label lblCQty;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblDos;

    @FXML
    private Label lblBQty;
    URL url;
   	Media media;
   	MediaPlayer mediaplayer;
   	AudioClip audio;
       void playClick()
       {
       	
       	url=getClass().getResource("sound.mp3");
   		media=new Media(url.toString());
   		mediaplayer=new MediaPlayer(media);
   		mediaplayer.play();
   		
       }

    @FXML
    void doReceive(ActionEvent event) throws SQLException {
    	//confirm???r u sure,payment done??????????????????????????/
    	playClick();
    	pst=con.prepareStatement("update bills set status='1' where cName=? and status='0' and DOS=?and DOE=?");
    	pst.setString(1, customer);	
    	pst.setDate(2, sqldos);
    	pst.setDate(3, sqldoe);
    	pst.executeUpdate();
    	
    	
    	Alert al=new Alert(AlertType.INFORMATION);//.error or warning etc       	 
    	al.setTitle("Status ");
    	al.setHeaderText("Successful payment!");//by default- header is type of alert
    	al.setContentText("Payment of Rs."+TotalAmount+" successfully received for '"+customer+"'");
    	al.show(); 	

    	//************************Send sms *************************    	
    	
			String msg="Dear "+customer+" your payment of  bill from "+sqldos+" to "+sqldoe+" accounting for cow qty: "+TotalCowQty+" ; buffalo qty: "+TotalBufQty+" and total  Rs."+TotalAmount+" has been successfully received";
			String resp=SST_SMS.bceSunSoftSend(mobile, msg);	
			System.out.println(resp);	//can try various types of response--invalid mobile number,internet connection not there, successful message and can print in console using string includes......		
		
    	
    	
    	
    	
    	listName.getItems().remove(customer);
    	lblAmount.setText("*");
    	lblBQty.setText("*");
    	lblCQty.setText("*");
    	lblDoe.setText("*");
    	lblDos.setText("*");
    }
    

    @FXML
    void doSelect(ActionEvent event) throws SQLException {
    	customer=listName.getSelectionModel().getSelectedItem();
    	pst=con.prepareStatement("select  * from bills where cName=?");
    	pst.setString(1, customer);		
		table=pst.executeQuery();
		
		table.next();
    	
    	TotalCowQty=table.getFloat("totalCQty");///?????????????error
    	 TotalBufQty=table.getFloat("totalBQty");
        TotalAmount=table.getFloat("amount");
    	sqldos=table.getDate("DOS");
    	 dos=sqldos.toString();
    	 sqldoe=table.getDate("DOE");
    	 doe=sqldoe.toString();
    	 
    	pst=con.prepareStatement("select  * from customer where cName=?");
     	pst.setString(1, customer);		
 		table=pst.executeQuery();
 		
 		table.next();    	
 		
    	mobile=table.getString("mobile");
    	   
    	lblAmount.setText(TotalAmount+"");
    	lblBQty.setText(TotalBufQty+"");
    	lblCQty.setText(TotalCowQty+"");
    	lblDoe.setText(doe);
    	lblDos.setText(dos);		
    }
    
    
    Connection con;
    ResultSet table;
    PreparedStatement pst;  
    String customer,dos,doe,mobile;
    java.sql.Date sqldos,sqldoe;
    float TotalAmount, TotalCowQty,TotalBufQty;
    
    void fillCombo()
    {	
    	ArrayList<String>listCust=new ArrayList<>();
    	try 
    	{
			pst=con.prepareStatement("select  cName from bills where status='0'");
			//assume bill taken and then only service continued...bill cant be kept pending else get 2 names if pending bills
			table=pst.executeQuery();
			
			while(table.next())
	    	{
	    		String cust=table.getString("cName");
	    		listCust.add(cust);
	    	}
	    	listName.getItems().addAll(listCust);
	    	System.out.println(listCust);
			
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		} 		
    }
    
    
    
    @FXML
    void initialize() {
    	con=DBConnection.doConnect();
        fillCombo();

    }
}
