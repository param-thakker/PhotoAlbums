package controller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import model.User;

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
	Button sLogout;
	@FXML
	Button sCreateAlbum;
	@FXML
	Button sBacktoAlbums;
	@FXML
	Button renameConfirm;
	@FXML
	Button renameCancel;
	@FXML
	ListView<Album> albumListView;
	@FXML
	ListView<Photo> searchResults;
	@FXML
	TextField albumField;
	@FXML
	TextField welcome;
	@FXML 
	TextField albumNameDetail;
	@FXML
	TextField albumNumPhotoDetail;
	@FXML
	TextField albumDate1Detail;
	@FXML
	TextField albumDate2Detail;
	@FXML
	Button confirmAdd;
	@FXML
	Button newAlbumCancel;
	User currentUser;
	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy");
	
	private ObservableList<Album> obsList; 
	List<Album> AlbumList = new ArrayList<Album>();
	List<String> albumStringList = new ArrayList<String>();
	List<Photo> photoLister = new ArrayList<Photo>();
	
	public void start(Stage mainStage, User user) throws IOException{
		welcome.setText("Welcome " + user.getUsername() + ", Please select an Album: ");
		albumField.setDisable(true);
		confirmAdd.setDisable(true);
		this.currentUser=user;
		displayList();
		
		logout.setOnAction(e-> {
			try {
				  logout();
				  mainStage.hide();
			}catch (IOException e1) {
			}
		});
		openAlbum.setOnAction(e-> {
			try {
				  if (!openAlbum().equals("error")){
				  mainStage.hide();
				  }
			}catch (IOException e1) {
			}
		});
		
		albumListView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				albumDetail(mainStage));
		
		}
	
	public void search(ActionEvent e) throws IOException{
		System.out.println("search pushed!");
	}
	private void albumDetail(Stage mainStage) {
		if (!albumListView.getSelectionModel().isEmpty()) {
			Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
			
			albumNameDetail.setText(selectedAlbum.albumName);
			albumNumPhotoDetail.setText(Integer.toString(selectedAlbum.photos.size()));
			
			
			String earliestDate="";
			String latestDate="";
			if (selectedAlbum.getPhotos().size()!=0) {
				Calendar date=selectedAlbum.getPhotos().get(0).getPhotoDate();
				 earliestDate=dateTimeformat.format(date.getTime());
				 latestDate=dateTimeformat.format(date.getTime());
			}
			for (Photo photo:selectedAlbum.getPhotos()) {
				Calendar date=photo.getPhotoDate();
		
				if (dateTimeformat.format(date.getTime()).compareTo(earliestDate)<0) {
					earliestDate=dateTimeformat.format(date.getTime());
				}
				if (dateTimeformat.format(date.getTime()).compareTo(latestDate)>0) {
					 latestDate=dateTimeformat.format(date.getTime());
				}
				
			}
			albumDate1Detail.setText(earliestDate);
			albumDate2Detail.setText(latestDate);
		}
	}
	private void albumDetailV2() {
		if (!albumListView.getSelectionModel().isEmpty()) {
			Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
			
			albumNameDetail.setText(selectedAlbum.albumName);
			albumNumPhotoDetail.setText(Integer.toString(selectedAlbum.photos.size()));
			String earliestDate="";
			String latestDate="";
			if (selectedAlbum.getPhotos().size()!=0) {
				Calendar date=selectedAlbum.getPhotos().get(0).getPhotoDate();
				 earliestDate=dateTimeformat.format(date.getTime());
				 latestDate=dateTimeformat.format(date.getTime());
			}
			for (Photo photo:selectedAlbum.getPhotos()) {
				Calendar date=photo.getPhotoDate();
		
				if (dateTimeformat.format(date.getTime()).compareTo(earliestDate)<0) {
					earliestDate=dateTimeformat.format(date.getTime());
				}
				if (dateTimeformat.format(date.getTime()).compareTo(latestDate)>0) {
					 latestDate=dateTimeformat.format(date.getTime());
				}
				
			}
			albumDate1Detail.setText(earliestDate);
			albumDate2Detail.setText(latestDate);
		}
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
		newAlbumCancel.setDisable(false);
		newAlbumCancel.setVisible(true);
		albumField.setVisible(true);
		renameAlbum.setDisable(true);
		delAlbum.setDisable(true);
		openAlbum.setDisable(true);
		search.setDisable(true);
	}
    public void renameAlbum(ActionEvent e) throws IOException{
    	System.out.println("Rename Album pushed!");
    	if (albumListView.getSelectionModel().getSelectedItem() != null) {
    		albumNameDetail.setOpacity(1);
    		albumNameDetail.setPromptText("Enter new Album name here");
    		albumNameDetail.setEditable(true);
    		renameConfirm.setVisible(true);
    		renameConfirm.setDisable(false);
    		renameCancel.setVisible(true);
    		renameCancel.setDisable(false);
    		albumNumPhotoDetail.setDisable(true);
    		albumDate1Detail.setDisable(true);
    		albumDate2Detail.setDisable(true);
    		
    		delAlbum.setDisable(true);
    		openAlbum.setDisable(true);
    		search.setDisable(true);
    		newAlbum.setDisable(true);
    		
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("No selected album to edit!");
    		alert.showAndWait();
    	}
	}
    public void renameConfirm(ActionEvent e) throws IOException{
    	Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
    	selectedAlbum.albumName = albumNameDetail.getText();
    	displayList();
    	albumDetailV2();
    	
		albumNameDetail.setEditable(false);
		renameConfirm.setVisible(false);
		renameConfirm.setDisable(true);
		renameCancel.setVisible(false);
		renameCancel.setDisable(true);
		albumNumPhotoDetail.setDisable(false);
		albumDate1Detail.setDisable(false);
		albumDate2Detail.setDisable(false);
		
		delAlbum.setDisable(false);
		openAlbum.setDisable(false);
		search.setDisable(false);
		newAlbum.setDisable(false);
    }
    public void renameCancel(ActionEvent e) {
    	albumDetailV2();
    	albumNameDetail.setEditable(false);
		renameConfirm.setVisible(false);
		renameConfirm.setDisable(true);
		renameCancel.setVisible(false);
		renameCancel.setDisable(true);
		albumNumPhotoDetail.setDisable(false);
		albumDate1Detail.setDisable(false);
		albumDate2Detail.setDisable(false);
		
		delAlbum.setDisable(false);
		openAlbum.setDisable(false);
		search.setDisable(false);
		newAlbum.setDisable(false);
    }
    public void delAlbum(ActionEvent e) throws IOException {
    	System.out.println("Delete Album pushed!");
    	if (albumListView.getSelectionModel().getSelectedItem() != null) {
    		
    		String albumToBeRemoved = albumListView.getSelectionModel().getSelectedItem().getAlbumName();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + albumToBeRemoved + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();

			int currIndex = albumListView.getSelectionModel().getSelectedIndex();
			if (alert.getResult() == ButtonType.YES) {
				currentUser.getAlbums().remove(albumListView.getSelectionModel().getSelectedItem());
				albumListView.getItems().remove(currIndex);
				//AlbumList.remove(currIndex);
				//albumStringList.remove(currIndex);
			}
    	}else {
    		Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setHeaderText("No selected album to delete!");
			alert2.showAndWait();
		}
    }
    public String openAlbum() throws IOException{
    	//System.out.println("Open Album pushed!");
    	
    	if (albumListView.getSelectionModel().getSelectedItem() != null) {
    		//open album **CURRENTLY GIVES NULLPOINTER idk how to fix this yet**
    		Album selectedAlbum = albumListView.getSelectionModel().getSelectedItem();
    		Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/PhotoDisplay.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			PhotoDisplayController photoController = loader.getController();
			photoController.start(stage, selectedAlbum,currentUser);
			Scene scene = new Scene(root,923,671);
			stage.setScene(scene);
			root.getScene().getWindow().hide(); //currently doesnt work properly
			stage.show();
			return "";//*/ 
			
			
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No selected album to open");
			alert.showAndWait();
			return "error";
    	}
    }
    
    public void sCreateAlbum(ActionEvent e) throws IOException{
    	System.out.println("Create Album by Search Results pushed!");
    }
    public void confirm(ActionEvent e) {
    	//AlbumList.add(new Album(albumField.getText()));
    	//albumStringList.add(albumField.getText());
    	currentUser.getAlbums().add(new Album(albumField.getText()));
		displayList();
		
		albumField.clear();
		albumField.setDisable(true);
		confirmAdd.setDisable(true);
		confirmAdd.setVisible(false);
		newAlbumCancel.setDisable(true);
		newAlbumCancel.setVisible(false);
		albumField.setVisible(false);
		renameAlbum.setDisable(false);
		delAlbum.setDisable(false);
		openAlbum.setDisable(false);
		search.setDisable(false);
    }
    public void albumCancel(ActionEvent e) {
    	albumField.clear();
    	albumField.setDisable(true);
		confirmAdd.setDisable(true);
		confirmAdd.setVisible(false);
		newAlbumCancel.setDisable(true);
		newAlbumCancel.setVisible(false);
		albumField.setVisible(false);
		renameAlbum.setDisable(false);
		delAlbum.setDisable(false);
		openAlbum.setDisable(false);
		search.setDisable(false);
    }
    public void displayList() {
    	obsList = FXCollections.observableArrayList(currentUser.getAlbums());
		albumListView.setItems(obsList);
	}
}
