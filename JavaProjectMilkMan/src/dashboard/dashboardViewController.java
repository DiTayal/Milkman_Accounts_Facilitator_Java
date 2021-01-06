package dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;





import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.stage.Stage;


public class dashboardViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView viewCustomer;

    @FXML
    private ImageView viewVariation;

    @FXML
    private ImageView viewBill;

    @FXML
    private ImageView viewIncome;
    
    
    @FXML
    void doviewBill(MouseEvent event) {
    	playClick();
    	Parent root;
		try
		{
			root = FXMLLoader.load(getClass().getClassLoader().getResource("tableBilling/tableBillingView.fxml"));
			Scene scene = new Scene(root);//created local Scene object
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 	

    }

    @FXML
    void doviewCustomer(MouseEvent event) {
    	playClick();
    	Parent root;
		try
		{
			root = FXMLLoader.load(getClass().getClassLoader().getResource("tableCustomer/tableCustomerView.fxml"));
			Scene scene = new Scene(root);//created local Scene object
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 

    }

    @FXML
    void doviewIncome(MouseEvent event) {
    	playClick();
    	Parent root;
		try
		{
			root = FXMLLoader.load(getClass().getClassLoader().getResource("income/incomeView.fxml"));
			Scene scene = new Scene(root);//created local Scene object
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 	

    }

    @FXML
    void doviewVariation(MouseEvent event) {
    	
    	playClick();
    	Parent root;
		try
		{
			root = FXMLLoader.load(getClass().getClassLoader().getResource("tableVariation/tableVariationView.fxml"));
			Scene scene = new Scene(root);//created local Scene object
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 	

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
    void dogenerateBill(MouseEvent event) {
    	playClick();
    	Parent root;
		try
		{
			root = FXMLLoader.load(getClass().getClassLoader().getResource("billing/billingView.fxml"));
			Scene scene = new Scene(root);//created local Scene object
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 	
    }

    @FXML
    void updateVariations(MouseEvent event) {
    	playClick();
    	
    	Parent root;
		try
		{
			root = FXMLLoader.load(getClass().getClassLoader().getResource("routineEntry/routineEntryView.fxml"));
			Scene scene = new Scene(root);//created local Scene object
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 	

    } 
    @FXML
    void doLogin(ActionEvent event) {    	
    	playClick();
    	Parent root;
		try
		{
			root = FXMLLoader.load(getClass().getClassLoader().getResource("login/loginView.fxml"));
			Scene scene = new Scene(root);//created local Scene object
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 	
    }

  


 
    @FXML
    void manageCustomers(MouseEvent event) {
    	playClick();
    	Parent root;
		try
		{
			root = FXMLLoader.load(getClass().getClassLoader().getResource("customerEntry/customerEntryView.fxml"));
			Scene scene = new Scene(root);//created local Scene object
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 	

    }

    @FXML
    void payCollect(MouseEvent event) {
    	playClick();
    	Parent root;
		try
		{
			root = FXMLLoader.load(getClass().getClassLoader().getResource("paymentCollection/paymentCollectionView.fxml"));
			Scene scene = new Scene(root);//created local Scene object
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 	

    }

    @FXML
    void initialize() {
    	
    	/*
    	  // Create a ScrollPane
        ScrollPane scrollPane = new ScrollPane();

 
        // Always show vertical scroll bar
        scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        
        // Horizontal scroll bar is only displayed when needed
        scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
 
        Stage primaryStage = null;
		primaryStage.setTitle("ScrollPane Demo 1 (o7planning.org)");
        Scene scene = new Scene(scrollPane, 550, 200);
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }
 
       

    }

