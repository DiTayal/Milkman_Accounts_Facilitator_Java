package routineEntry;

import login.DBConnection;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.print.DocFlavor.STRING;

//import com.mysql.jdbc.PreparedStatement;
import java.sql.*;
import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class routineEntryViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> listCustomer;

    @FXML
    private TextField txtCowQtyVary;

    @FXML
    private TextField txtBufQtyVary;

    @FXML
    private Label lblCowQtyStd;

    @FXML
    private Label lblBufQtyStd;

    @FXML
    private DatePicker dtpDov;
    
    @FXML
    private CheckBox chkNotTaken;
    
    @FXML
    void doDeleteOthers(ActionEvent event) 
    {
    	//use reatin all
    	listCustomer.getItems().retainAll(listCustomer.getSelectionModel().getSelectedItems());
    	//or---save eleemts in array list as did in fill Combo
    	
    	/*ObservableList<String>selCust=listCustomer.getSelectionModel().getSelectedItems();
    	ArrayList<String>arrSelCust=new ArrayList<>(selCust);
    	listCustomer.getItems().clear();
    	listCustomer.getItems().addAll(arrSelCust);
    	when we save elements in obsList and clear list, then save elements in obsList also get cleared, 
    	because everything is reference, and on clearing list, all references lost.
    	 */
    }
    

    String customer;
    Float cowStdQty,bufStdQty;
    java.sql.Date sqlDostart;//check if date of variation iske baad
    @FXML
    void enterVariations(MouseEvent event) throws SQLException {
    	//can make on single click or make extraction button  >>
    	 if (event.getClickCount() == 2) 
    	 {
    		 customer=listCustomer.getSelectionModel().getSelectedItem();
    		 PreparedStatement pst=con.prepareStatement("select *from customer where cName=?");
    		 pst.setString(1,customer);
    		 ResultSet table=pst.executeQuery();
    		
    		 
    		 table.next();//*********************************************
    		 

    		 cowStdQty=table.getFloat("cQty");
    		 bufStdQty=table.getFloat("bQty");
    		 
    		 lblBufQtyStd.setText(bufStdQty+"");
    		 lblCowQtyStd.setText(cowStdQty+""); 
    		 sqlDostart=table.getDate("DOS");
    		 
    		 
    		 txtBufQtyVary.setText("0");
 			txtCowQtyVary.setText("0");//set default milk taken to be zero
 			dtpDov.getEditor().setText(null);
 			
       	 }
    }
    
    
    @FXML
    void doSave(ActionEvent event) throws SQLException {
    	/*function  if milkman enters kitna milk taken on variation day ,
    	 * rather than entering  the difference or surplus*/   	
    	
    	float cowVar,bufVar;
   	 
		if(chkNotTaken.isSelected())
		{
			txtBufQtyVary.setText("0");
			txtCowQtyVary.setText("0");
		}
		LocalDate dateOfVariation=dtpDov.getValue();
		
		if((txtCowQtyVary.getText().equals("")||txtBufQtyVary.getText().equals(""))||dateOfVariation==null)
		{
			Alert al=new Alert(AlertType.ERROR);//.error or warning etc
	    	 System.out.println("here");
	    	al.setTitle("DATA ERROR");
	    	al.setHeaderText("Unfilled Values");//by default- header is type of alert
	    	al.setContentText("FILL ALL FIELDS");
	    	al.show();
		}		
		else
		{
			 cowVar=Float.parseFloat(txtCowQtyVary.getText());
	   	  	 bufVar=Float.parseFloat(txtBufQtyVary.getText());    		
				System.out.println(cowVar+"  "+bufVar);
				System.out.println("*********"+cowStdQty+"  "+bufStdQty);
			java.sql.Date  sqlDate=java.sql.Date.valueOf(dateOfVariation);
			
			if(sqlDate.before(sqlDostart))
			{
				Alert al=new Alert(AlertType.ERROR);//.error or warning etc    			   	 
		    	al.setTitle("WRONG DATE");
		    	al.setHeaderText("CUSTOMER STARTED MILK AFTER   "+sqlDostart);//by default- header is type of alert
		    	al.setContentText("Choose correct date of variation");
		    	al.show();    				
			}
			else if(sqlDate.equals(sqlDostart))  //variation for selected customer already addded
			{
				Alert al=new Alert(AlertType.INFORMATION);    			   	 
		    	al.setTitle("DUPLICACY");
		    	al.setHeaderText("ALREADY UPDATED!");//by default- header is type of alert
		    	al.setContentText("Variation corresponding to this date has already been saved");
		    	al.show();    				
			}
			else
			{
				Alert al=new Alert(AlertType.CONFIRMATION);//.error or warning etc
				al.setTitle("CONFIRM");
		    	al.setHeaderText("Are you sure to save the variation?");//by default- header is type of alert
		    	al.setContentText("cow milk qty today= "+cowVar+"\nbuffalo  milk qty today= "+bufVar+"\ndate:"+dateOfVariation);
		    	Optional<ButtonType> result = al.showAndWait();

	    	if (result.get() == ButtonType.OK)
	    	{   		
	    		
	    		System.out.println("********"+(cowVar-cowStdQty)+"  "+(bufVar-bufStdQty));	    		
	    		PreparedStatement pst= con.prepareStatement("insert into variation  values(?,?,?,?)");
	        	pst.setString(1, customer);
	        	pst.setDate(2, sqlDate);
	        	pst.setFloat(3, cowVar-cowStdQty);
	        	pst.setFloat(4, bufVar-bufStdQty);
	        	pst.executeUpdate();
	        	String item=listCustomer.getSelectionModel().getSelectedItem();
	        	listCustomer.getItems().remove(item);
	        	  		
	    	}
	    	else 
	    	{
	    	  System.exit(0);
	    	}				
		}	

	}    	
    }
    
    /*this is the function if milkman saves difference or extra milk
    @FXML
    void doSave(ActionEvent event) throws SQLException 
    {
    	float cowVar,bufVar;
    	    	 
    			if(chkNotTaken.isSelected())
    			{
    				cowVar=-1*Float.parseFloat(lblCowQtyStd.getText());
    		        bufVar=-Float.parseFloat(lblBufQtyStd.getText());
    			}
    			else
    			{
    				  cowVar=Float.parseFloat(txtCowQtyVary.getText());
    		    	  bufVar=Float.parseFloat(txtBufQtyVary.getText());    		    	     		    	  
       			}
    			
    			LocalDate dateOfVariation=dtpDov.getValue();
    			java.sql.Date  sqlDate=java.sql.Date.valueOf(dateOfVariation);
    			
    			if(sqlDate.before(sqlDostart))
    			{
    				Alert al=new Alert(AlertType.ERROR);//.error or warning etc    			   	 
    		    	al.setTitle("WRONG DATE");
    		    	al.setHeaderText("CUSTOMER STARTED MILK AFTER   "+sqlDostart);//by default- header is type of alert
    		    	al.setContentText("Choose correct date of variation");
    		    	al.show();    				
    			}
    			else
    			{
    				Alert al=new Alert(AlertType.CONFIRMATION);//.error or warning etc
        			al.setTitle("CONFIRM");
    		    	al.setHeaderText("Are you sure to save the variation?");//by default- header is type of alert
    		    	al.setContentText("cow Variation= "+cowVar+"\nbuffalo variation= "+bufVar+"\ndate:"+dateOfVariation);
    		    	Optional<ButtonType> result = al.showAndWait();

    	    	if (result.get() == ButtonType.OK)
    	    	{
    	    		PreparedStatement pst= con.prepareStatement("insert into variation  values(?,?,?,?)");
    	        	pst.setString(1, customer);
    	        	pst.setDate(2, sqlDate);
    	        	pst.setFloat(3, cowVar);
    	        	pst.setFloat(4, bufVar);
    	        	pst.executeUpdate();
    	        	String item=listCustomer.getSelectionModel().getSelectedItem();
    	        	listCustomer.getItems().remove(item);
    	        	  		
    	    	}
    	    	else 
    	    	{
    	    	  System.exit(0);
    	    	}	
    		}
    			
    }
    */
    
    void fillCombo() throws SQLException
    {
    	ArrayList<String>custList=new ArrayList<>();
    	PreparedStatement pst=con.prepareStatement("select cName from customer");
    	ResultSet table=pst.executeQuery();
    	while(table.next())
    	{
    		custList.add(table.getString("cName"));//no getItems
    	}
    	listCustomer.getItems().addAll(custList);  	
    	
    }

    Connection con;
    @FXML
    void initialize() {
    	con=DBConnection.doConnect();   
    	try 
    	{
			fillCombo();//if here also throw exception and if exception comes, it remain unhandled
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    	listCustomer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
