package tableBilling;
import login.DBConnection;



import java.sql.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class tableBillingViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private ToggleGroup pay;

    @FXML
    private ComboBox<String> listName;

    @FXML
    private TableView<BillHistoryBean> tbl;
    
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
    void doExport(ActionEvent event) throws IOException { //sir has done this without any external jar
    	
    	playClick();
    	System.out.println("in export");
    	Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");
    	
    	/*XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Java Books");*/

       // Row row = spreadsheet.createRow(0);
    	 Row row = spreadsheet.createRow(0);

        for (int j = 0; j < tbl.getColumns().size(); j++) {
            row.createCell(j).setCellValue(tbl.getColumns().get(j).getText());
        }

        for (int i = 0; i < tbl.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < tbl.getColumns().size(); j++) {
                if(tbl.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(tbl.getColumns().get(j).getCellData(i).toString()); 
                }
                else {
                    row.createCell(j).setCellValue("");
                }   
            }
        }       
        
        
        //******************************
        FileChooser fileChooser = new FileChooser();

        //Set extension filter to .xlsx files
       // FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);

        //If file is not null, write to file using output stream.
        if (file != null) {
            try (FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath())) {
                workbook.write(outputStream);
                lblResult.setText("Exported successfully");
            }
            catch (IOException ex) {
               // Logger.getLogger(JavaFXApplication57.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //******************************

      //  FileOutputStream fileOut = new FileOutputStream("workbook.csv");
       // workbook.write(fileOut);
       // fileOut.close();

        Platform.exit();
       

    }

    @FXML
    void doShowAllBills(ActionEvent event) throws SQLException {
    	pst=con.prepareStatement("select * from  bills");
		table=pst.executeQuery();
		fetchAllRecords();
    }

    @FXML
    void doShowHistory(ActionEvent event) throws SQLException {
    	String customer=listName.getSelectionModel().getSelectedItem();
    	pst=con.prepareStatement("select * from  bills where cName=?");
    	pst.setString(1, customer);
		table=pst.executeQuery();
		fetchAllRecords();   	

    }

    @FXML
    void doShowPaid(ActionEvent event) throws SQLException {
    	pst=con.prepareStatement("select * from  bills where status='1'");
		table=pst.executeQuery();
		fetchAllRecords();      	

    }

    @FXML
    void doShowUnPaid(ActionEvent event) throws SQLException {
    	pst=con.prepareStatement("select * from  bills where status='0'");
		table=pst.executeQuery();
		fetchAllRecords(); 

    }
    
    
    void fetchAllRecords() throws SQLException{
    	table=pst.executeQuery();
    	list=FXCollections.observableArrayList();
    	while(table.next())
    	{
    		String cust=table.getString("cName");
    		java.sql.Date dos=table.getDate("DOS");
    		String stdos=dos.toString();
    		
    		java.sql.Date doe=table.getDate("DOE");
    		String stdoe=dos.toString();
    		
    		String cqty=table.getString("totalCQty");//here qty are in string
    		String bqty=table.getString("totalBQty");
    		String status=table.getString("status");
    		String amount=table.getString("amount");
    		
    		BillHistoryBean obj=new BillHistoryBean(cust, stdos, stdoe, amount, status, cqty, bqty);//automatically pane aap aajayega constructor
    		list.add(obj);    		
    	}
    	tbl.setItems(list);
    }
    
      
    Connection con;
    ResultSet table;
    PreparedStatement pst;    
    ObservableList<BillHistoryBean>list;//initialised in different way as earlier
    
    
    void fillCombo()
    {	
    	ArrayList<String>listCust=new ArrayList<>();
    	try 
    	{
			pst=con.prepareStatement("select distinct cName from bills");
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
    	TableColumn<BillHistoryBean, String> cname=new TableColumn<BillHistoryBean, String>("Customer's name");
    	cname.setCellValueFactory(new PropertyValueFactory<>("name"));//bean field;
    	
    	TableColumn<BillHistoryBean, String> dos=new TableColumn<BillHistoryBean, String>("Date of start");
    	dos.setCellValueFactory(new PropertyValueFactory<>("dos"));//bean field;
    	
    	TableColumn<BillHistoryBean, String> doe=new TableColumn<BillHistoryBean, String>("Date of end");
    	doe.setCellValueFactory(new PropertyValueFactory<>("doe"));//bean field;
    	
    	TableColumn<BillHistoryBean, String> cqty=new TableColumn<BillHistoryBean, String>("Total Variation in cow's milk");
    	cqty.setCellValueFactory(new PropertyValueFactory<>("totalcqty"));//bean field;
    	
    	TableColumn<BillHistoryBean,String> bqty=new TableColumn<BillHistoryBean, String>("Total Variation in buffalo's milk");
    	bqty.setCellValueFactory(new PropertyValueFactory<>("totalbqty"));//bean field;
    	

    	TableColumn<BillHistoryBean,String> status=new TableColumn<BillHistoryBean, String>("Status");
    	status.setCellValueFactory(new PropertyValueFactory<>("status"));//bean field;
    	
    

    	cname.prefWidthProperty().bind(tbl.widthProperty().divide(6)); // w * 1/5
    	dos.prefWidthProperty().bind(tbl.widthProperty().divide(8)); // w * 1/5
    	doe.prefWidthProperty().bind(tbl.widthProperty().divide(8)); // w * 1/5
    	cqty.prefWidthProperty().bind(tbl.widthProperty().divide(4)); // w * 1/4
    	bqty.prefWidthProperty().bind(tbl.widthProperty().divide(4)); // w * 1/3
    	status.prefWidthProperty().bind(tbl.widthProperty().divide(12)); // w * 1/3
    	
    	
    	tbl.getColumns().addAll(cname,dos,doe,cqty,bqty,status);    			
    }
    

    @FXML
    void initialize() {
    con=DBConnection.doConnect();
    fillCombo();
    addCols();

    }
}

