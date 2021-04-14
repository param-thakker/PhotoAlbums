package controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.User;
import controller.LoginController;

import javafx.event.ActionEvent;

/**
 * The AdminController class handles the user control and logic in the Admin screen
 * @author Param Thakker
 * @author Jonathan Lu
 *
 */
public class AdminController {
	/**
	 * The FXML Button to add a new User
	 */
	@FXML
	Button addUser;
	/**
	 * The FXML Button to delete an existing User
	 */
	@FXML
	Button delUser;
	/**
	 * The FXML ListView to display the list of Users
	 */
	@FXML
	ListView<User> userList;

	/**
	 * The FXML Button to logout of the application
	 */

	@FXML
	Button aLogout;
	/**
	 * The FXML TextField to add a new User
	 */
	@FXML
	TextField userField;
	/** 
	 * The FXML Button to confirm the addition of a new User
	 */
	@FXML
	Button confirmAdd;
	/**
	 * the list of available Users to login
	 */
	List<String> list=new ArrayList<>();
//<<<<<<< Updated upstream
	/**
	 * The ObservableList to display the available Users to login
	 */
	//private ObservableList<String> obsList;  
	/**
	 * The main start method for AdminController
	 * @param mainStage the Stage to execute on
	 * @throws IOException
	 */
	//public void start(Stage mainStage) throws IOException {
//=======
	List<User> usersList=new ArrayList<>();
	
	//private ObservableList<String> obsList;  
	
	public void start(Stage mainStage, List<User> users) throws IOException {
		this.usersList=users;
		userList.setItems(FXCollections.observableArrayList(users));
//>>>>>>> Stashed changes
		userField.setDisable(true);
		confirmAdd.setDisable(true);
		
		//displayList();		
		aLogout.setOnAction(e -> {
			  try {
				  logout();
				  mainStage.hide();
			  }catch (IOException e1) {
			  }
		  });
	}
	/**
	 * Enables a new User to be added to the list, popping up a textField to enter information
	 * @param e the ActionEvent to activate addUser()
	 * @throws IOException
	 */
	public void addUser(ActionEvent e) throws IOException {
		userField.setDisable(false);
		confirmAdd.setDisable(false);
	}
	/**
	 * Deletes a current User from the list of available Users
	 * @param e the ActionEvent to activate delUser()
	 * @throws IOException
	 */
	public void delUser(ActionEvent e) throws IOException {
	
		if (userList.getSelectionModel().getSelectedItem() != null) {
	        User selectedUser=userList.getSelectionModel().getSelectedItem();
	        Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + selectedUser.getUsername() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.setTitle("Delete user");
			alert.showAndWait();
			int currIndex = userList.getSelectionModel().getSelectedIndex();
			if (alert.getResult() == ButtonType.YES) {
				userList.getItems().remove(selectedUser);
				autoSave();
				
		
			}
	    }
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No selected user to delete!");
			alert.showAndWait();

		}
	}
	/**
	 * Confirms the addition of a new User, checking for duplicates and bad entries
	 * @param e the ActionEvent to activate confirm()
	 */
	public void confirm(ActionEvent e) {
		if (userField.getText().trim().length()==0 || userField.getText()==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No username entered! Please enter a username");
			alert.showAndWait();
		}
		else {
			String newUser=userField.getText();
			//if (LoginController.users.containsKey(newUser)) {
			for (User user:this.usersList) {
				if (user.getUsername().equals(newUser)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("This username already exists");
					alert.showAndWait();
					return;
				}
			}
				
			//}
		//	else {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Add " + newUser + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				alert.setTitle("Add new user");
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					
					userList.getItems().add(new User(newUser));
					autoSave();
					userField.clear();
					userField.setDisable(true);
					confirmAdd.setDisable(true);
				
				}
	
			//}
		}
	}
	/**
	 * logs out of the application, allowing a new User to log in
	 * @throws IOException
	 */
	public void logout() throws IOException{
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		LoginController loginControl = loader.getController();
		loginControl.start(stage);
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.setTitle("User Login");
		stage.show(); 
	}

	private void autoSave() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("data/data.dat");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(new ArrayList<>(Arrays.asList(userList.getItems().toArray())));
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	/*public void displayList() {
>>>>>>> Stashed changes
		for (String user:LoginController.users.keySet()) {
			if (!user.equals("admin") && !list.contains(user)) {
			list.add(user);
			}
		}
		obsList = FXCollections.observableArrayList(list); 

		userList.setItems(obsList); 
	}*/
	
}
