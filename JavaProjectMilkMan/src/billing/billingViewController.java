package billing;
import login.DBConnection;
import sms.SST_SMS;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//import com.mysql.jdbc.PreparedStatement;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


//if change standard value, once 1 bill found....??


public class billingViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> listCustomer;

    @FXML
    private DatePicker dtpDos;

    @FXML
    private DatePicker dtpDoe;

    @FXML
    private Label lblDays;
    @FXML
    private Label lblResult;

    @FXML
    private TextField txtCowQtyStd;

    @FXML
    private TextField txtBufQtyStd;

    @FXML
    private TextField txtCowPriceStd;

    @FXML
    private TextField txtBufPriceStd;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtCowQtyVar;

    @FXML
    private TextField txtBufQtyVar;

    @FXML
    private TextField txttotalBill;
    
    
    @FXML
    private TextField txttotalCQty;

    @FXML
    private TextField txttotalBQty;
    
    @FXML
    private Button btnGenBill;

    
    @FXML
    private ImageView imgDoe;
   
    @FXML
    private Button btnSave;

    LocalDate dos,doe;
    java.sql.Date sqldos,sqldoe;
    long days;
    String customer;
    float total,totalCQty,totalBQty,CowQtyVar,BufQtyVar,CowQtyStd,BufQtyStd,BufPriceStd,CowPriceStd;
    
    

    @FXML
    void doSelectCust(MouseEvent event) throws SQLException
    {
         customer=listCustomer.getSelectionModel().getSelectedItem();
    	//fetch data in second box
    	PreparedStatement pst=con.prepareStatement("select * from customer where cName=?" );
    	pst.setString(1, customer);
    	ResultSet table=pst.executeQuery();
    	
    	table.next();
    	
    	CowQtyStd=table.getFloat("cQty");
    	BufQtyStd=table.getFloat("bQty");
    	BufPriceStd=(table.getFloat("bPrice"));
        CowPriceStd=table.getFloat("cPrice");
        sqldos=table.getDate("DOS");
    			
    	txtBufPriceStd.setText(BufPriceStd+"");
    	txtBufQtyStd.setText(BufQtyStd+"");
    	txtCowPriceStd.setText(CowPriceStd+"");
    	txtCowQtyStd.setText(CowQtyStd+"");
    	txtMobile.setText(table.getString("mobile"));        	
    	dtpDos.getEditor().setText(sqldos.toString());
    	
    	txtBufQtyVar.setText("0.0");//default value else while calculating bill, get empty string and it cant be parsed 
    	txtCowQtyVar.setText("0.0"); 
    	lblResult.setText("");
    	btnSave.setDisable(true);
    	btnGenBill.setDisable(true);
    	
    	doe=null;//else will pick pehle vaali date,as it is global variable
    	System.out.println("here");
    	
    	//empty all boxes
    	dtpDoe.getEditor().setText("");
    	//dtpDoe.getEditor().setText(null);
    	dtpDoe.setValue(null);//set doe null,else give days wrt to earlier added date
    	//dtpDos.getEditor().setText("");
    	txttotalBQty.setText("");
    	txttotalCQty.setText("");
    	
    	lblDays.setText("");
    	txttotalBill.setText("");  
    	
    	//doe=null;//else will pick pehle vaali date,as it is global variable
    	
    	imgDoe.setImage(null);
    	System.out.println(dtpDoe.getValue());
    	
      }   

      
    @FXML
    void doFetch(ActionEvent event) throws SQLException {//arrow button
    	
    	String customer=listCustomer.getSelectionModel().getSelectedItem();
    	//fetch data in second box
    	PreparedStatement pst=con.prepareStatement("select * from customer where cName=?" );
    	pst.setString(1, customer);
    	ResultSet table=pst.executeQuery();
    	
    	table.next();
    	
    	CowQtyStd=table.getFloat("cQty");
    	BufQtyStd=table.getFloat("bQty");
    	//System.out.println("5555555555     "+BufQtyStd);
    	BufPriceStd=(table.getFloat("bPrice"));
        CowPriceStd=table.getFloat("cPrice");
    			
    	txtBufPriceStd.setText(BufPriceStd+"");
    	txtBufQtyStd.setText(BufQtyStd+"");
    	txtCowPriceStd.setText(CowPriceStd+"");
    	txtCowQtyStd.setText(CowQtyStd+"");
    	txtMobile.setText(table.getString("mobile"));        	
    	
    	
    	txtBufQtyVar.setText("0.0");//default value else while calculating bill, get empty string and it cant be parsed 
    	txtCowQtyVar.setText("0.0");   	
    	
    }

    @FXML
    void doGenerateBill(ActionEvent event) {
    	//Float total=Integer.parseInt(days-----error
    	//days*(total cow milk*price and total buf milk*price)

    	 total = totalBQty*BufPriceStd+totalCQty*CowPriceStd;
    	 txttotalBill.setText(total+"");
    	 btnSave.setDisable(false);
    }

    @FXML
    void doGetDays(ActionEvent event) throws SQLException {
    	
    	imgDoe.setImage(null);    	
    	//dos=dtpDos.getValue();------as its sql date converted to string in dtp
    	doe=dtpDoe.getValue();
    	//sqldos=java.sql.Date.valueOf(dos);--as now we have filled value in text box
    	dos=sqldos.toLocalDate();
    	if(doe==null)
    	{
    		Image img = new Image("cross.jpg");
    		imgDoe.setImage(img);
    	}
    	else if(doe.isBefore(dos))
    	{
    		Image img = new Image("cross.jpg");
    		imgDoe.setImage(img);
    	}
    	else
    	{
    		sqldoe=java.sql.Date.valueOf(doe);  		
        	
    		
        	/*System.out.println(dos);
         	System.out.println(doe);
         	System.out.println(sqldos);
         	System.out.println(sqldoe);
         	long days=ChronoUnit.DAYS.between(dos, doe);
         	System.out.println(days);*/

        	
         	doe=doe.plusDays(1);//see return type in declaration
            days=ChronoUnit.DAYS.between(dos, doe);
         	lblDays.setText(days+"");
         	
         	//fill variation data aslo
         	//	PreparedStatement pst=con.prepareStatement("select sum(cQty),sum(bQty)as 'bufQty' from variation where date between(?,?) and cName=?");
         	//between not work!!!!!!!!!!!!
        	PreparedStatement pst=con.prepareStatement("select sum(cQty),sum(bQty) as 'bufQty' from variation where date>=? and date<=? and cName=?");
         	pst.setDate(1, sqldos);
         	pst.setDate(2, sqldoe);
         	pst.setString(3,customer);
         	ResultSet table=pst.executeQuery();
         	
         	/*while(table.next())
         		{
         		System.out.println();
         		}*/
         	table.next();    	
         	
         	BufQtyVar=table.getFloat("sum(cQty)");
         	CowQtyVar=table.getFloat("bufQty");
         	//if(BufQtyVar.)
         	//System.out.println("***"+BufQtyVar);//how getting 0.0???/if no variation--bcoz sum does not return null, it return int or float
         	//System.out.println(CowQtyVar);
         	/*
         	if(BufQtyVar==0.0 && CowQtyVar==0.0)//if varaiation in even 1 milk appear in table and possible second milk 0.0 only as in it no variation--so see both as 0.0
         	{
             	txtBufQtyVar.setText("0.0");
             	txtCowQtyVar.setText("0"); 
         	}
         	else*/
         	{
         		txtBufQtyVar.setText(BufQtyVar+"");
             	txtCowQtyVar.setText(CowQtyVar+""); 
             	//get null here if no variation but do panga aage in calculation=-=-	
         	}
         	
         	
         	//totalBQty=BufQtyVar+days*BufQtyStd;//-=-=- here panga
         	//totalCQty=CowQtyVar+days*CowQtyStd;
        	//if(BufQtyVar.)
         /*	System.out.println("*&&&&&&&*"+BufQtyVar);//how getting 0.0?????????????????????????/if no variation
         	System.out.println(CowQtyVar);
         	
         	System.out.println("*&&&&&&&*"+BufQtyStd);//how getting 0.0?????????????????????????/if no variation
         	System.out.println(CowQtyStd);
         	*/
         	//totalBQty=BufQtyVar+((int)days)*BufQtyStd;//typecast days
         	
         	
         	totalBQty=BufQtyVar+(days)*BufQtyStd;//////////////////???????????????////why no error??long to float?
         	totalCQty=CowQtyVar+((int)days)*CowQtyStd;
         //see range og long and float--------------***************
         	
         	
         	txttotalBQty.setText(totalBQty+"");
         	txttotalCQty.setText(totalCQty+"");
         	
         	btnGenBill.setDisable(false); 
    	}
    	   	
    }

    @FXML
    void doSaveSms(ActionEvent event) throws SQLException { 	//bill created
    	
    	
    	
    	PreparedStatement pst=con.prepareStatement("insert into bills values(?,?,?,?,?,?,?)");
    	pst.setString(1, customer);
    	pst.setDate(2, sqldos);
    	pst.setDate(3, sqldoe);
    	pst.setFloat(4, total);
    	pst.setFloat(5, totalCQty);
    	pst.setFloat(6, totalBQty);
    	pst.setBoolean(7, false);
    	int count=pst.executeUpdate();
    	lblResult.setText(count+" record inserted successfully");
    	
    	//here change date of start in customer table
    	//doe=doe.plusDays(1);********************************
    	//no need to do plus 1 as we already did 1+ day to find correct number of days to generate bill
    	sqldos=java.sql.Date.valueOf(doe);//new date of start
    	
    	pst=con.prepareStatement("update customer set DOS=? where cName=?");
    	pst.setDate(1, sqldos);
    	pst.setString(2, customer);

    	pst.executeUpdate();
    	
    	//***************Send SMS***********************
    	//TextInputDialog dialog = new TextInputDialog("");
		//dialog.setTitle("Input Data...");
		//dialog.setContentText("Please enter Mobile number:");

		// Traditional way to get the response value.
		//Optional<String> result = dialog.showAndWait();
				
			//doAlert(result.get());
		//if(result.isPresent())
		{
			String msg="Dear "+customer+" your bill from "+sqldos+" to "+sqldoe+" is Rs."+total;
			String resp=SST_SMS.bceSunSoftSend(txtMobile.getText(), msg);	
			System.out.println(resp);			
		}
    	
    	listCustomer.getItems().remove(customer);
    }
    void fillCombo()
    {
    	ArrayList<String> custList=new ArrayList<>();    	
    	try
    	{
			PreparedStatement pst=con.prepareStatement("select cName from customer");
			ResultSet table=pst.executeQuery();
			while(table.next())
			{
				custList.add(table.getString("cName"));
			}
			listCustomer.getItems().addAll(custList);//set string type in array list <> if error
		}
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}    	
    }

    Connection con;
    @FXML
    void initialize() {
    	con=DBConnection.doConnect(); 
    	fillCombo();
    	btnSave.setDisable(true);
    	btnGenBill.setDisable(true);
    	
    	    }
    
}
