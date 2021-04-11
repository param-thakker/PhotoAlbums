package controller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.User;
import javafx.event.ActionEvent;


public class LoginController {
	
	@FXML
	TextField usernameTextField;
	
	@FXML
	Button login;
	
	
	HashMap<String,User> users = new HashMap<>();
	


	
	public void start(Stage mainStage) throws IOException{
	  users.put("admin", new User("admin"));
	  users.put("stock", new User("stock"));
	}
	
	public void login(ActionEvent e) throws IOException {
		String username=usernameTextField.getText();
		if(username.trim().length()==0 || username==null ){
			
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Error");
			errorAlert.setContentText("No username entered!! You must input a username");
			errorAlert.show();
			return;
		}
		else if(username.trim().equals("admin")){
			
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/Admin.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				AdminController adminController = loader.getController();
				adminController.start(stage);
				Scene scene = new Scene(root,350,400);
				stage.setScene(scene);
				root.getScene().getWindow().hide(); //currently doesnt work properly
				stage.show();	
		
		}
		else if(username.trim().equals("stock")) { //load stock photos but otherwise identical to reg login
			User user=users.get(username);
			
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AlbumDisplay.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			UserController userController = loader.getController();
			userController.start(stage);
			Scene scene = new Scene(root,923,671);
			stage.setScene(scene);
			root.getScene().getWindow().hide();//currently doesnt work properly
			stage.show();	
		}
		else if (users.containsKey(username)) {
			User user=users.get(username);
		
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/AlbumDisplay.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				UserController userController = loader.getController();
				userController.start(stage);
				Scene scene = new Scene(root,923,671);
				stage.setScene(scene);
				root.getScene().getWindow().hide(); //currently doesnt work properly
				stage.show();	 
				
		}
			
		}
		
		
		
		
		
	
	
}
