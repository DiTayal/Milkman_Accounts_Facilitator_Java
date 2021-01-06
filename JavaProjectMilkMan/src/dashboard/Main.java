package dashboard;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;







import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try 
		{	    	/*
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
		
			
			Parent root=FXMLLoader.load(getClass().getResource("dashboardView.fxml")); 
			Scene scene = new Scene(root,930,580);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
