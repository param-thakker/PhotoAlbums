package controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
import javafx.event.ActionEvent;

/**
 * The LoginController class handles the user control and logic with regards to the login screen
 * @author Param Thakker
 * @author Jonathan Lu
 *
 */
public class LoginController {
	/**
	 * The FXML TextField for the username
	 */
	@FXML
	TextField usernameTextField;
	/**
	 * The FXML Button to click to login
	 */
	@FXML
	Button login;
	



	
	/**
	 * The main start method for LoginController
	 * @param mainStage the Stage to execute on
	 * @throws IOException IOException
	 */
	public void start(Stage mainStage) throws IOException{

	  
	 login.setOnAction(e -> {
		  try {
			  login();
			  //hide mainStage only if successful login
			  mainStage.hide();
		  }catch (IOException e1) {
			
		  }
	  });
	}
	/**
	 * Handles an attempted login, checking the usernameTextField with the list of allowed users and redirects the screen accordingly
	 * @throws IOException IOException
	 */
	public void login() throws IOException {
		String username=usernameTextField.getText();
		File data = new File("data/data.dat");

		if (!data.exists()) {
			try {
				data.createNewFile();
				User stock = new User("stock");
				Album stockAlbum = new Album("stockAlbum");
				for (int i = 0; i <5; i++) {
					File photoFile = new File("data/photos/samplePic" + Integer.toString(i+1) + ".jpg");
					if (photoFile != null) {
						photoSaveDetails(photoFile,stockAlbum);
					}
					
				}

				stock.getAlbums().add(stockAlbum);
				List<User> users= new ArrayList<>();
				users.add(stock);
				
				autoSave(users);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		
		try {
			FileInputStream fileInputStream = new FileInputStream("data/data.dat");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			List<User> users = (List<User>) objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();

			User user = null;

			for (User currentUser : users) {
				if (currentUser.getUsername().equals(username)) {
					user = currentUser;
					break;
				}
			}

			if (username.equals("admin") ) {
					Stage stage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/Admin.fxml"));
					AnchorPane root = (AnchorPane)loader.load();
					AdminController adminController = loader.getController();
					adminController.start(stage,users);
					Scene scene = new Scene(root,350,400);
					stage.setScene(scene);
					stage.show();	
					//System.out.println("ADMIN");
			}
		
			else if (user!=null) {
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/AlbumDisplay.fxml"));
				AnchorPane root = (AnchorPane) loader.load();
				UserController userController = loader.getController();
				userController.start(stage,user,users);
				Scene scene = new Scene(root,923,671);
				stage.setScene(scene);
				//root.getScene().getWindow().hide();
				stage.show();	
			}
			else {

				Alert erroralert = new Alert(AlertType.ERROR);
				erroralert.setHeaderText("Error");
				erroralert.setContentText("Invalid username entered!! Please enter a valid username");
				erroralert.showAndWait();
				
				FXMLLoader loader = new FXMLLoader();   
				loader.setLocation(getClass().getResource("/view/Login.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				LoginController loginControl = loader.getController();
				Stage mainStage=new Stage();
				loginControl.start(mainStage);
				Scene scene = new Scene(root, 600, 400); //resize as needed
				mainStage.setScene(scene);
				mainStage.setTitle("User Login");
				mainStage.show(); 

			
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
			
		}
	/**
	 * Auto saves the current user data for future opening
	 * @param users the Users to save data to
	 */
	public static void autoSave(List<User> users) {
	try {
		FileOutputStream fileOutputStream = new FileOutputStream("data/data.dat");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(users);

		objectOutputStream.close();
		fileOutputStream.close();
	} catch (Exception exception) {
		exception.printStackTrace();
	}

	}
	/**
	 * Saves the current Photo details to the stock Album
	 * @param photoFile the File to save to stock Album
	 * @param stockAlbum the Album of stock photos
	 */
	public static void photoSaveDetails(File photoFile, Album stockAlbum) {
		String photoName = photoFile.getName();
		Calendar photoDate = Calendar.getInstance();
		photoDate.setTimeInMillis(photoFile.lastModified());
		Photo newStockPhoto = new Photo(photoName, "", photoDate, photoFile.toURI().toString());
		stockAlbum.getPhotos().add(newStockPhoto);
	}
		
		
		
		
		
	
	
}
