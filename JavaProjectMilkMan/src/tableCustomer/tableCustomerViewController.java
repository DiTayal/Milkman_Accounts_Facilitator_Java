package tableCustomer;
import login.DBConnection;
import tableVariation.milkVariationBean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;

//import com.mysql.jdbc.PreparedStatement;
import java.sql.*;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

public class tableCustomerViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dtpDos;
    @FXML
    private RadioButton radCow;

    @FXML
    private ToggleGroup type;

    @FXML
    private RadioButton radBuffalo;
    
    @FXML
    private Label lblResult;

    
    @FXML
    private TableView<CustomerBean> ShowTable;
   
    @FXML
    void doBuffaloetch(ActionEvent event) {//on radio button

    }

    @FXML
    void doCowFetch(ActionEvent event) {//on radio button

    }
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
    void doExport(ActionEvent event) throws IOException 
    {
    	playClick();
    	        Writer writer = null;
    	        try {
    	        	FileChooser chooser=new FileChooser();
    		    	
    	        	chooser.setTitle("Select Path:");
    	        	
    	        	chooser.getExtensionFilters().addAll(
    	                    new FileChooser.ExtensionFilter("All Files", "*.*"));
    	        	 File file=chooser.showSaveDialog(null);
    	        	String filePath=file.getAbsolutePath();
    	        	if(!(filePath.endsWith(".csv")||filePath.endsWith(".CSV")))
    	        	{
    	        		filePath=filePath+".csv";//if user does not write .csv then add
    	        	}
    	        	 file = new File(filePath);
    	            writer = new BufferedWriter(new FileWriter(file));//if is underlined,import java.io.*
    	            String text="Customer's name ,Mobile no.,Address,date of start,Cow's Milk Qty,Buffalo's Milk Qty,Cow's Milk Price,Buffalo's Milk Price\n";
    	            
    	            writer.write(text);
    	            for (CustomerBean p : list)
    	            {
    					text = p.getCustomerName()+ ","+p.getMobile()+ "," +p.getAddress()+ "," +p.getDos()+ "," + p.getCowQty()+ "," + p.getBuffaloQty()+ "," + p.getCowPrice()+","+p.getBuffaloPrice()+"\n";
    	                writer.write(text);
    	                lblResult.setText("Exported Successfully");
    	            }
    	        } 
    	        catch (Exception ex)
    	        {
    	            ex.printStackTrace();
    	        }
    	        finally 
    	        {
    	            writer.flush();
    	             writer.close();
    	        }
    	    }    
    
    

    @FXML
    void doFetchAfterDos(ActionEvent event) {
    	PreparedStatement pst;
		try 
		{
			LocalDate ldos=dtpDos.getValue();
			java.sql.Date sqldos=java.sql.Date.valueOf(ldos);
			pst = con.prepareStatement("select*from customer where DOS=?");
			pst.setDate(1, sqldos);
			FetchRecords( pst);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}  	
    }

    @FXML
    void doFetchOneCategoryMilkBuyer(ActionEvent event) {
    	if(radBuffalo.isSelected())
    	{
    		PreparedStatement pst;
    		try 
    		{
    			pst = con.prepareStatement("select*from customer where cQty=?");//if take both milk?????????????deal with it
    			pst.setFloat(1, 0);
    			FetchRecords( pst);
    		}
    		catch (SQLException e)
    		{
    			e.printStackTrace();
    		}
    	}
    	else if(radCow.isSelected())
    	{
    		PreparedStatement pst;
    		try 
    		{
    			pst = con.prepareStatement("select*from customer where bQty='0'");    			
    			FetchRecords( pst);
    		}
    		catch (SQLException e)
    		{
    			e.printStackTrace();
    		}
    	}

    }

    @FXML
    void doShowAll(ActionEvent event){
    	PreparedStatement pst;//can make it instance variable or global and then no need to pass as well 
		try 
		{
			pst = con.prepareStatement("select*from customer");
			FetchRecords( pst);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
    }
    
    void FetchRecords(PreparedStatement pst) throws SQLException//make separate function as else will ahve to repeat againand again, as only query change
    {
    	table=pst.executeQuery();
    	
    	list=FXCollections.observableArrayList();//as it is
		
    	while(table.next())
    	{
    		String name=table.getString("cName");
        	String address=table.getString("address");
        	String mobile=table.getString("mobile");
        	
        	java.sql.Date mysqldos=table.getDate("DOS");
        	String stdos=mysqldos.toString();

        	float cprice=table.getFloat("cPrice");
        	float bprice=table.getFloat("bprice");
        	float cQty=table.getFloat("cQty");
        	float bQty=table.getFloat("bQty");
        	System.out.println(name+"\t"+address+"\t"+mobile+"\t"+stdos+"\t"+cprice+"\t"+cQty+"\t"+bprice+"\t"+bQty);
        	CustomerBean obj=new CustomerBean(name,mobile,address,stdos,cQty,bQty,cprice,bprice);
        	list.add(obj);
    	}
    	ShowTable.setItems(list);//must---problem if show here??????????????
    //	iske baad making columns of table---code repeat - so made a function called on initialisation
	} 

    
    
    
    Connection con;
    ResultSet table;
    ObservableList<CustomerBean>list;
    
    void addColumns()
    {
    	//creating table cols
    	
    	TableColumn<CustomerBean, String> custName=new TableColumn<CustomerBean, String>("Customer's Name");
    	//Dikhava Title
    	custName.setCellValueFactory(new PropertyValueFactory<>("customerName"));//bean field name not table col. name
     	//no relation with table of DB as vahan se we have fetched values and stored in observablelist
     	//we have to write classes, so Integer and not int
    	
    	TableColumn<CustomerBean, String> mobile=new TableColumn<CustomerBean, String>("Mobile no.");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	
    	TableColumn<CustomerBean, String> address=new TableColumn<CustomerBean, String>("Address");
    	address.setCellValueFactory(new PropertyValueFactory<>("address"));
    	
    	TableColumn<CustomerBean, String> dos=new TableColumn<CustomerBean, String>("date of start");
    	dos.setCellValueFactory(new PropertyValueFactory<>("dos"));//bean vars
    	
    	TableColumn<CustomerBean, Float> cowQty=new TableColumn<CustomerBean, Float>("Cow's Milk Qty");
    	cowQty.setCellValueFactory(new PropertyValueFactory<>("cowQty"));
    	
    	TableColumn<CustomerBean, Float> buffaloQty=new TableColumn<CustomerBean, Float>("Buffalo's Milk Qty");
    	buffaloQty.setCellValueFactory(new PropertyValueFactory<>("buffaloQty"));
    	
    	TableColumn<CustomerBean, Float> cowPrice=new TableColumn<CustomerBean, Float>("Cow's Milk Price");
    	cowPrice.setCellValueFactory(new PropertyValueFactory<>("cowPrice"));
    	
    	TableColumn<CustomerBean, Float> buffaloPrice=new TableColumn<CustomerBean, Float>("Buffalo's Milk Price");
    	buffaloPrice.setCellValueFactory(new PropertyValueFactory<>("buffaloPrice"));

    	//inserting cols
    	//?????????????????/clear
    	ShowTable.getColumns().addAll(custName,mobile,address,dos,cowQty,buffaloQty,cowPrice,buffaloPrice);	
    }

    @FXML
    void initialize() {
    	con=DBConnection.doConnect();
    	addColumns();      

    }
}
