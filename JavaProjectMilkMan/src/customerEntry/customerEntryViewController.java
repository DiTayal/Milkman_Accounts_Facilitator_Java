package customerEntry;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

//import com.mysql.jdbc.PreparedStatement;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class customerEntryViewController {
	@FXML
	private ImageView imgcq;

	@FXML
	private ImageView imgbq;

	@FXML
	private ImageView imgcp;

	@FXML
	private ImageView imgbp;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ComboBox<String> comboName;

	@FXML
	private TextField txtMobile;

	@FXML
	private TextField txtAddress;

	@FXML
	private ImageView profilePic;

	@FXML
	private TextField txtBuffaloQty;

	@FXML
	private TextField txtCowQty;

	@FXML
	private TextField txtCowPrice;

	@FXML
	private TextField txtBuffaloPrice;

	@FXML
	private DatePicker dtpDos;

	@FXML
	private Label lblPath;

	@FXML
	private Label lblResult;

	@FXML
	void doBrowse(ActionEvent event) 
	{
		lblPath.setText("");//so that when click on browsw pehle vaala next not displayed

		FileChooser fc=new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("Image only","*.png","*.jpg","*.gif"));
		//extension filter is static method

		//fc.setInitialDirectory(new File("Pictures"));====wrong
		//can set default folder path which should be opened

		fc.setInitialDirectory(new File("C:\\Users\\dishi\\OneDrive\\Pictures"));
		//copy path from right click ->properties and not from URL and replace \ with \\ as they have special meaning 
		fc.setTitle("Choose image");//set title of dialog box
		File selFile=fc.showOpenDialog(null);

		if(selFile!=null)//means file chosen
		{
			lblPath.setText(selFile.getPath());//save path as need to fetch also

			//???????????????/////path vs abspath??????????????????????
			//can get nameo f file only too
			///???????????????????????????????/copy file in project??????????????///////
			String imageFile;
			try 
			{
				imageFile = selFile.toURI().toURL().toString();/////???????????????????????????????????/
				Image image = new Image(imageFile);
				profilePic.setImage(image);
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			}            
		}
		else
		{
			lblPath.setText("Image file selection cancelled.");
			String noImg="F:\\workspace-java\\JavaProjectMilkMan\\src\\customerEntry\\noImageAvail.jpg ";
			String imageFile;
			try 
			{
				imageFile = new File(noImg).toURI().toURL().toString();
				Image image = new Image(imageFile);
				profilePic.setImage(image);
			}
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			}
		}	
	}

	@FXML
	void doFetch(ActionEvent event) 
	{
		String customer=comboName.getSelectionModel().getSelectedItem();//here since Primary key is string, so no chakkar of parsing and on
		try 
		{
			PreparedStatement pst=con.prepareStatement("select * from customer where cName=?");//this way no chakkar of invalid entry
			pst.setString(1, customer);

			ResultSet table=pst.executeQuery();

			table.next();//must else cursor in table pointing to head of tables

			txtAddress.setText(table.getString("address"));
			txtMobile.setText(table.getString("mobile"));

			float cprice=table.getFloat("cPrice");

			txtCowPrice.setText(cprice+"");
			txtBuffaloPrice.setText((table.getFloat("bPrice"))+"");
			txtBuffaloQty.setText((table.getFloat("bQty"))+"");
			txtCowQty.setText((table.getFloat("cQty"))+"");

			//date fetch---------------
			java.sql.Date sqldos=table.getDate("DOS");//String d=table.getDate("DOS");----error
			dtpDos.getEditor().setText(sqldos.toString());

			//image fetch----------------
			String picpath=table.getString("picpath");
			String imageFile;
			try 
			{
				imageFile = new File(picpath).toURI().toURL().toString();
				Image image = new Image(imageFile);
				profilePic.setImage(image);
			}
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

	}



	@FXML
	void doLeft(ActionEvent event) throws SQLException 
	{    	
		String customer=comboName.getSelectionModel().getSelectedItem();
		doFetch(null);//need to give action ,but want to simply call ,so give null event

		Alert al=new Alert(AlertType.CONFIRMATION);//.error or warning etc

		al.setTitle("Delete Record");
		al.setHeaderText("WARNING!  RECHECK ");//by default- header is type of alert
		al.setContentText("Are you confirm to delete the customer data");

		/*al.show();----if show box, then will not get button clicked, so remove it  
    	--need to get response, so use shpwAndWait and not show  */	

		Optional<ButtonType> result = al.showAndWait();

		if (result.get() == ButtonType.OK)
		{
			PreparedStatement pst= con.prepareStatement("delete from customer where cName=?");
			pst.setString(1, customer);
			pst.executeUpdate();
			doNew(null);//called to empty the textfields????????????????????????????????
			lblResult.setText(customer+"'s data successfully cleared");    		
		}
		else 
		{
			System.exit(0);
		}	
	}



	@FXML
	void doUpdate(ActionEvent event) //first user fetch all data and then click on update, so need to call fetch
	{
		//changes in save code
		PreparedStatement pst;
		lblResult.setText("");
		String custName=comboName.getSelectionModel().getSelectedItem();
		//cant change name as it is PK??????
		String mobile=txtMobile.getText();
		String address=txtAddress.getText();
		float cQty=Float.parseFloat(txtCowQty.getText());
		float bQty=Float.parseFloat(txtBuffaloQty.getText());
		float cPrice=Float.parseFloat(txtCowPrice.getText());
		float bPrice=Float.parseFloat(txtBuffaloPrice.getText());

		LocalDate ldos=dtpDos.getValue();    
		String stdos;
		java.sql.Date sqldos;//to convert to sql form to insert in db
		if(ldos==null)//if(dtpDos.getValue()==null)i.e value not upadted
		{
			stdos=dtpDos.getEditor().getText();
			ldos=LocalDate.parse(stdos);
		}
		sqldos=java.sql.Date.valueOf(ldos);


		//pic updated using browse    		    	

		String picpath = lblPath.getText();
		//System.out.println("picpath pehle "+picpath);

		/*FileChooser fc=new FileChooser();
    		    	fc.getExtensionFilters().addAll(new ExtensionFilter("Image only","*.png","*.jpg","*.gif"));
    		    	fc.setInitialDirectory(new File("C:\\Users\\dishi\\OneDrive\\Pictures")); 
    		    	fc.setTitle("Choose image");//set title of dialog box
    		    	File selFile=fc.showOpenDialog(null);will make upadte button to be browse button*/


		if((picpath.equals("Image file selection cancelled."))||(picpath.equals("")))//means does not want to change,galti se clicked on browse and didnt find good pic ||  //no update//pic not updated
		{//in if stataement use brackets everywhere, else error    		    		
			try 
			{
				pst=con.prepareStatement("select picpath from customer where cName=?");
				pst.setString(1,custName);
				ResultSet row=pst.executeQuery();

				row.next();//to take cursor to data from headings of column
				picpath=row.getString("picpath");//save in db
				//System.out.println("&&&&&&&&&&"+picpath+"************\n");
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}		    		
		}	
		else//means file chosen and pic updated
			;  		


		if(mobile.equals(""))
			showMsg("Fill mobile number");
		else if(sqldos.equals(""))
			showMsg("Choose date of start");
		else	
			try 
		{
				pst = con.prepareStatement("update customer set mobile=?,address=?,cqty=?,cPrice=?,bQty=?,bPrice=?,DOS=?,picpath=? where cName=?");

				pst.setString(9,custName);
				pst.setString(1,mobile);
				pst.setString(2,address);
				pst.setFloat(3,cQty);
				pst.setFloat(4,cPrice);//take care of order of cols in table
				pst.setFloat(5,bQty);
				pst.setFloat(6,bPrice);
				pst.setDate(7,sqldos);
				pst.setString(8, picpath);

				int count=pst.executeUpdate();
				lblResult.setText(count+" records successfully updated");
		}
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}	    						
	}

	void fillCombo()
	{
		comboName.getItems().clear();
		//clear combo so because after adding new item and cicking on new, some names repeat because usi me append ho jata
		//comboName.getItems().clear();--need in new button
		ArrayList<String>custList=new ArrayList<>();
		//	ObservableList<String>custList=new ObservableList<>();-it is interface
		try 
		{
			PreparedStatement pst=con.prepareStatement("select distinct cName from customer");

			/*distinct ki vaise no need because cust name is primary key but when i click on new, 
			then fill  combo is called and  some cust names repeat because naye vaale append in old 
			vaale, so clear combo...jab close app and then no repeat as combo is filled from db
			---better clear combo in new function 
			 */

			ResultSet table= pst.executeQuery();
			while(table.next())
			{
				String cname=table.getString("cName");
				custList.add(cname);//add auto increments index
			}
			comboName.getItems().addAll(custList);
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		}

	}
	@FXML
	void doNew(ActionEvent event)
	{
		txtAddress.setText("");
		txtBuffaloPrice.setText("0");
		txtBuffaloQty.setText("0");
		txtCowPrice.setText("0");
		txtCowQty.setText("0");
		txtMobile.setText("");
		lblResult.setText("");
		lblPath.setText("");
		dtpDos.getEditor().setText("");
		//dtpDos.setValue(null);???????????????

		comboName.getItems().clear();//remove this and add new cust and click new and then the new cust added ,do not appear
		fillCombo();
		//clean image selected

		lblPath.setText("");
		String imageFile;
		try 
		{
			imageFile = new File("F:\\workspace-java\\JavaProjectMilkMan\\src\\customerEntry\\userinfo.png").toURI().toURL().toString();
			//give location of image,even if it is in same project
			Image image = new Image(imageFile);
			profilePic.setImage(image);
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}       	
	}

	@FXML
	void doSave(ActionEvent event) 
	{
		imgcq.setImage(null);imgbq.setImage(null);imgcp.setImage(null);imgbp.setImage(null);
		//if data with same cust name saved-duplicate key error on console
		PreparedStatement pst;
		lblResult.setText("");
		String custName=comboName.getSelectionModel().getSelectedItem();
		try {
			pst=con.prepareStatement("select*from customer where cName=?");
			pst.setString(1,custName);
			ResultSet table=pst.executeQuery();
			if(table.next())
			{
				Alert al=new Alert(AlertType.ERROR);//.error or warning etc       	 
				al.setTitle("Change Customer name");
				al.setHeaderText("Change UserName");//by default- header is type of alert
				al.setContentText(" CUSTOMER NAME ALREDY EXISTS");
				al.show();

			}
			else//name does not exist
			{
				float cQty=0,bQty=0,cPrice = 0,bPrice=0;
				Image img= new Image("cross.jpg");
				String mobile=txtMobile.getText();
				String address=txtAddress.getText();//while entering address dont use comma els ethat value will go to next field in excel file
				if(txtCowQty.getText().equals(""))
				{
					imgcq.setImage(img);
					return;
				}
				else      		
					cQty=Float.parseFloat(txtCowQty.getText());

				if(txtBuffaloQty.getText().equals(""))
				{imgbq.setImage(img);
				return;
				}
				else
					bQty=Float.parseFloat(txtBuffaloQty.getText());

				if(txtCowPrice.getText().equals(""))
				{imgcp.setImage(img);
				return;
				}
				else		    	
					cPrice=Float.parseFloat(txtCowPrice.getText());


				if(txtBuffaloPrice.getText().equals(""))
				{imgbp.setImage(img);
				return;
				}
				else
					bPrice=Float.parseFloat(txtBuffaloPrice.getText());
				//System.out.println(cQty);0.0 saved if no value entered
				//System.out.println(bQty);

				LocalDate ldos=dtpDos.getValue();//*********************

				if(ldos==null)//if(dtpDos.getValue()==null)
					showMsg("Choose date of start");


				//else//why give error though used alert before-----observation--because common place
				//vaali statements run and then alert display---try writing neeche vaali statements without try and fill only 
				//cust name and date,get errror fill mobile, and when fill all, give error cust name alredy exists

				else
				{
					//checked date first ,else cant convert null to sql date
					java.sql.Date sqlDos=java.sql.Date.valueOf(ldos);//converted to sql date to save in DBase
					//must fill date

					String picname=lblPath.getText();
					//if browse button not clicked then lblPath  is "", so noimage is to be saved in thesse 2 cases

					//System.out.println("ONSAVE "+picname );

					if(picname.equals("Image file selection cancelled.")||picname.equals(""))
						//this is different if from alert vaale ifs
						picname="F:\\workspace-java\\JavaProjectMilkMan\\src\\customerEntry\\noImageAvail.jpg";   
					//save full path, because need to  pics also
					System.out.println(picname);

					if(mobile.equals(""))
						showMsg("Fill mobile number");
					else if(sqlDos.equals(""))
						showMsg("Choose date of start");
					/*if(address.equals(""))
		    		showMsg("Fill address");
		    		if(custName.equals(""))
		    		showMsg("Fill name");
					 */
					else 
					{
						try 
						{		    			
							pst = con.prepareStatement("insert into customer values(?,?,?,?,?,?,?,?,?)");

							pst.setString(1,custName);
							pst.setString(2,mobile);
							pst.setString(3,address);
							pst.setFloat(4, cQty);
							pst.setFloat(5,cPrice);//take care of order of cols in table
							pst.setFloat(6,bQty);
							pst.setFloat(7,bPrice);
							pst.setDate(8,sqlDos);
							pst.setString(9, picname);
							int count=pst.executeUpdate();
							lblResult.setText(count+" record entered successfully");
							fillCombo();
						}
						catch (SQLException e)
						{
							e.printStackTrace();
						} 
					}

				}		      	
			}
		}
		catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	void showMsg(String str)//ALERT IS INBUILT CLASS....... COPY CODE
	{
		Alert al=new Alert(AlertType.ERROR);//.error or warning etc

		al.setTitle("DATA ERROR");
		al.setHeaderText("Unfilled Values");//by default- header is type of alert
		al.setContentText(str);
		al.show();
	}

	Connection con;

	@FXML
	void initialize() {
		con=DBConnection.doConnect();
		//set default value 0
		txtCowPrice.setText(0+"");
		txtCowQty.setText(String.valueOf(0));
		txtBuffaloPrice.setText(0+"");
		txtBuffaloQty.setText(0+"");
		fillCombo();      
	}
}
