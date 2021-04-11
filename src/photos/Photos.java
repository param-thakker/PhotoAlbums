package photos;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Photos extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/SearchDisplay.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		//AdminController admin = loader.getController();
		//admin.start(primaryStage);
		//LoginController loginControl = loader.getController();
		//loginControl.start(primaryStage);
		UserController user = loader.getController();
		user.start(primaryStage);

		Scene scene = new Scene(root, 600, 400); //resize as needed
		primaryStage.setScene(scene);
		primaryStage.setTitle("User Login");
		primaryStage.show(); 
		
	}

}
