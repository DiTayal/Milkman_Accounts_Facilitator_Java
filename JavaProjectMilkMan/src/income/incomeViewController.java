package income;
import  login.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class incomeViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dtpDos;

    @FXML
    private DatePicker dtpDoe;

    @FXML
    private Label lblAmount;
    
    @FXML
    private ImageView imgdos;

    @FXML
    private ImageView imgdoe;
    

    @FXML
    void doTotalIncome(ActionEvent event) throws SQLException {
    	imgdoe.setImage(null);
    	imgdos.setImage(null);
    	 Image img;
    	LocalDate ldos=dtpDos.getValue();
    	LocalDate ldoe=dtpDoe.getValue();
    	if(ldos==null)//adding feature of displaying cross
    	{
    		 img = new Image("cross.jpg");
    		imgdos.setImage(img);
    	}
    	   		
    	else if(ldoe==null)
    	{
    	    img = new Image("cross.jpg");
    		imgdoe.setImage(img);
    	}
    /*	else if(ldoe!=null)
    	{
    		imgdoe.setImage(null);
    	} 
    	else if(ldos!=null)
    	{
    		imgdos.setImage(null);
    	} */
    	else
    	{
    		java.sql.Date sqldos=java.sql.Date.valueOf(ldos);
        	java.sql.Date sqldoe=java.sql.Date.valueOf(ldoe);
        	
        	//PreparedStatement pst=con.prepareStatement("select sum(amount) from bills where DOS>=? and DOE<=? and status='1'");
        	PreparedStatement pst=con.prepareStatement("select sum(amount)  from  bills where DOS>=? and DOE<=? and status=1");
        	pst.setDate(1, sqldos);
        	pst.setDate(2, sqldoe);
        	ResultSet table=pst.executeQuery();
        	table.next();
        	
        	lblAmount.setText(table.getFloat("sum(amount)")+"");
        	//lblAmount.setText(table.getString("sum(amount)"));
    	}
    	
    	

    }
    Connection con;

    @FXML
    void initialize() {
    	con=DBConnection.doConnect();
    	//in scene builder pehle set bold and then increase size
    	
       
    }
}
