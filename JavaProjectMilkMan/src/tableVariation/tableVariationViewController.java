package tableVariation;

import login.DBConnection;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//import com.mysql.jdbc.PreparedStatement;
import java.sql.*;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

public class tableVariationViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> listName;

    @FXML
    private DatePicker dtpDos;

    @FXML
    private DatePicker dtpDoe;

    @FXML
    private TableView<milkVariationBean> tbl;
    

    @FXML
    private Label lblResult;
    
    
    
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
    	            String text="Customer's name ,Date of variation,Variation in cow's milk,Variation in buffalo's milk\n";

    	            writer.write(text);
    	            for (milkVariationBean p : list)
    	            {
    					text = p.getCname()+ "," + p.getDov()+ "," + p.getCqty()+ "," + p.getBqty()+"\n";
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
    void findVarName(ActionEvent event) throws SQLException {
    	String customer=listName.getSelectionModel().getSelectedItem();
    	pst=con.prepareStatement("select * from variation where cname=?");
    	pst.setString(1, customer);
    	fetchALlRecords();
    }

    @FXML
    void showAllVarTotal(ActionEvent event) throws SQLException {
    	String customer=listName.getSelectionModel().getSelectedItem();
    	pst=con.prepareStatement("select * from variation ");
    	fetchALlRecords();
    }

    @FXML
    void showVarAllDate(ActionEvent event) throws SQLException {
    	LocalDate lods=dtpDos.getValue();
    	java.sql.Date sqldos=java.sql.Date.valueOf(lods);
    	
    	LocalDate lode=dtpDoe.getValue();
    	java.sql.Date sqldoe=java.sql.Date.valueOf(lode);
    	
    	pst=con.prepareStatement("select * from variation where date>=? and date<=?");
    	pst.setDate(1, sqldos);
    	pst.setDate(2, sqldoe);
    	fetchALlRecords();

    }

    @FXML
    void showVarNameDate(ActionEvent event) throws SQLException {
    	LocalDate lods=dtpDos.getValue();
    	java.sql.Date sqldos=java.sql.Date.valueOf(lods);
    	
    	LocalDate lode=dtpDoe.getValue();
    	java.sql.Date sqldoe=java.sql.Date.valueOf(lode);
    	
    	
    	String customer=listName.getSelectionModel().getSelectedItem();
    	pst=con.prepareStatement("select * from variation where date>=? and date<=? and cName=?");
    	pst.setDate(1, sqldos);
    	pst.setDate(2, sqldoe);
    	pst.setString(3, customer);
    	fetchALlRecords();

    }
       
    void fetchALlRecords() throws SQLException{
    	table=pst.executeQuery();
    	list=FXCollections.observableArrayList();
    	while(table.next())
    	{
    		String cust=table.getString("cName");
    		java.sql.Date dov=table.getDate("date");
    		String stdate=dov.toString();
    		
    		Float cqty=table.getFloat("cQty");
    		Float bqty=table.getFloat("bQty");
    		
    		milkVariationBean obj=new milkVariationBean(cqty, bqty, stdate, cust);//automatically pane aap aajayega constructor
    		list.add(obj);    		
    	}
    	tbl.setItems(list);
    }
    
    
    
    Connection con;
    ResultSet table;
    PreparedStatement pst;    
    ObservableList<milkVariationBean>list;//initialised in different way as earlier
    
    
    void fillCombo()
    {	
    	ArrayList<String>listCust=new ArrayList<>();
    	try 
    	{
			pst=con.prepareStatement("select distinct cName from variation");
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
    void addCols()
    {
    											//object name						//table to be made ka col name
    	TableColumn<milkVariationBean, String> cname=new TableColumn<milkVariationBean, String>("Customer's name");
    	cname.setCellValueFactory(new PropertyValueFactory<>("cname"));//bean field;
    	
    	TableColumn<milkVariationBean, String> dov=new TableColumn<milkVariationBean, String>("Date of variation");
    	dov.setCellValueFactory(new PropertyValueFactory<>("dov"));//bean field;
    	
    	TableColumn<milkVariationBean, Float> cqty=new TableColumn<milkVariationBean, Float>("Variation in cow's milk");
    	cqty.setCellValueFactory(new PropertyValueFactory<>("cqty"));//bean field;
    	
    	TableColumn<milkVariationBean,Float> bqty=new TableColumn<milkVariationBean, Float>("Variation in buffalo's milk");
    	bqty.setCellValueFactory(new PropertyValueFactory<>("bqty"));//bean field;
    	
    	
    	/*cname.prefWidthProperty().bind(tbl.widthProperty().divide(4)); // w * 1/4
    	dov.prefWidthProperty().bind(tbl.widthProperty().divide(2)); // w * 1/2
    	cqty.prefWidthProperty().bind(tbl.widthProperty().divide(4)); // w * 1/4
    	bqty.prefWidthProperty().bind(tbl.widthProperty().divide(4)); // w * 1/4
*/    	
    	
    	cname.prefWidthProperty().bind(tbl.widthProperty().divide(4)); // w * 1/5
    	dov.prefWidthProperty().bind(tbl.widthProperty().divide(5)); // w * 1/5
    	cqty.prefWidthProperty().bind(tbl.widthProperty().divide(4)); // w * 1/4
    	bqty.prefWidthProperty().bind(tbl.widthProperty().divide(3)); // w * 1/3
    	
    	
    	tbl.getColumns().addAll(cname,dov,cqty,bqty);
    			
    }
    

    @FXML
    void initialize() {
    con=DBConnection.doConnect();
    fillCombo();
    addCols();

    }
}
