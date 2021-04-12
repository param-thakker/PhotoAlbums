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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.Album;
import model.Photo;

public class UserController {
	@FXML
	Button logout;
	@FXML
	Button newAlbum;
	@FXML
	Button renameAlbum;
	@FXML
	Button delAlbum;
	@FXML
	Button openAlbum;
	@FXML
	Button search;
	@FXML
	Button mcConfirm;
	@FXML
	Button mcCancel;
	@FXML
	Button backToAlbum;
	@FXML
	Button addPhoto;
	@FXML
	Button delPhoto;
	@FXML
	Button movePhoto;
	@FXML
	Button copyPhoto;
	@FXML
	Button addTag;
	@FXML
	Button delTag;
	@FXML
	Button prevPhoto;
	@FXML
	Button nextPhoto;
	@FXML
	Button sLogout;
	@FXML
	Button sCreateAlbum;
	@FXML
	Button sBacktoAlbums;
	@FXML
	TextField caption;
	@FXML
	TextField dateCaptured;
	@FXML
	TextField Tags;
	@FXML
	ListView albumListView;
	@FXML
	ListView mcAlbumList;
	@FXML
	ListView photoList;
	@FXML
	ListView searchResults;
	@FXML
	TextField albumField;
	@FXML
	Button confirmAdd;
	
	
	private ObservableList<String> obsList; 
	List<Album> AlbumList = new ArrayList<Album>();
	List<String> albumStringList = new ArrayList<String>();
	List<Photo> photoLister = new ArrayList<Photo>();
	
	public void start(Stage mainStage) throws IOException{
		logout.setOnAction(e-> {
			try {
				  logout();
				  mainStage.hide();
			}catch (IOException e1) {
			}
		});
		albumField.setDisable(true);
		confirmAdd.setDisable(true);

	}
	
	public void search(ActionEvent e) throws IOException{
		System.out.println("search pushed!");
	}
    public void logout() throws IOException{
    	System.out.println("logout pushed!");
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
    public void newAlbum(ActionEvent e) throws IOException{
    	albumField.setDisable(false);
		confirmAdd.setDisable(false);
		confirmAdd.setVisible(true);
		albumField.setVisible(true);
	}
    public void renameAlbum(ActionEvent e) throws IOException{
    	System.out.println("Rename Album pushed!");
	}
    public void delAlbum(ActionEvent e) throws IOException{
    	System.out.println("Delete Album pushed!");
    	if (albumListView.getSelectionModel().getSelectedItem() != null) {
    		//delete album
    		String albumToBeRemoved = (String) albumListView.getSelectionModel().getSelectedItem();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + albumListView.getSelectionModel().getSelectedItem() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();

			int currIndex = albumListView.getSelectionModel().getSelectedIndex();
			if (alert.getResult() == ButtonType.YES) {
				albumListView.getItems().remove(currIndex);
				AlbumList.remove(currIndex);
				albumStringList.remove(currIndex);
			}
    	}else {
    		Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setHeaderText("No selected album to delete!");
			alert2.showAndWait();
		}
    }
    public void openAlbum(ActionEvent e) throws IOException{
    	System.out.println("Open Album pushed!");
    	
    	if (albumListView.getSelectionModel().getSelectedItem() != null) {
    		//open album **CURRENTLY GIVES NULLPOINTER idk how to fix this yet**
    		/*Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/PhotoDisplay.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			UserController userController = loader.getController();
			userController.start(stage);
			Scene scene = new Scene(root,923,671);
			stage.setScene(scene);
			root.getScene().getWindow().hide(); //currently doesnt work properly
			stage.show();	*/ 
			
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No selected album to open!");
			alert.showAndWait();
    	}
    }
    public void mcConfirm(ActionEvent e) throws IOException{
    	System.out.println("Move/Copy Confirm pushed!");
    }
    public void mcCancel(ActionEvent e) throws IOException{
    	System.out.println("Move/Copy Cancel pushed!");
    }
    public void backToAlbum(ActionEvent e) throws IOException{
    	System.out.println("Back to Album pushed!");
    }
    public void addPhoto(ActionEvent e) throws IOException{
    	System.out.println("Add Photo pushed!");
    	
    
    	
    }
    public void delPhoto(ActionEvent e) throws IOException{
    	System.out.println("Delete Photo pushed!");
    }
    public void movePhoto(ActionEvent e) throws IOException{
    	System.out.println("Move Photo pushed!");
    }
    public void copyPhoto(ActionEvent e) throws IOException{
    	System.out.println("Copy Photo pushed!");
    }
    public void addTag(ActionEvent e) throws IOException{
    	System.out.println("Add Tag pushed!");
    }
    public void delTag(ActionEvent e) throws IOException{
    	System.out.println("Delete Tag pushed!");
    }
    public void nextPhoto(ActionEvent e) throws IOException{
    	System.out.println("Next Photo pushed!");
    }
    public void prevPhoto(ActionEvent e) throws IOException{
    	System.out.println("Previous Photo pushed!");
    }
    public void sCreateAlbum(ActionEvent e) throws IOException{
    	System.out.println("Create Album by Search Results pushed!");
    }
    public void confirm(ActionEvent e) {
    	AlbumList.add(new Album(albumField.getText()));
    	albumStringList.add(albumField.getText());
		obsList = FXCollections.observableArrayList(albumStringList);
		albumListView.setItems(obsList);
		
		albumField.setDisable(true);
		confirmAdd.setDisable(true);
		confirmAdd.setVisible(false);
		albumField.setVisible(false);
    }
}
