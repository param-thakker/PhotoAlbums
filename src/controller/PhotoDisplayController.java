package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Album;
import model.Photo;
import model.User;

public class PhotoDisplayController {
	
	private Album currentAlbum;
	private User currentUser;
	
	@FXML
	Button backToAlbum;
	@FXML
	ListView<Photo> photoList;
	

	List<Photo> list=new ArrayList<>();
	
	private ObservableList<Photo> obsList;  
	

	public void start(Stage mainStage,Album album, User user) throws IOException{
		displayList();
		this.currentAlbum=album;
		this.currentUser=user;
		backToAlbum.setOnAction(e -> {
			  try {
				  backToAlbum();
				  mainStage.hide();
			  }catch (IOException e1) {
			  }
		  });
		
	}
	public void backToAlbum() throws IOException {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AlbumDisplay.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		UserController userController = loader.getController();
		userController.start(stage,currentUser);
		Scene scene = new Scene(root,923,671);
		stage.setScene(scene);
		root.getScene().getWindow().hide();
		stage.show();
		
	}
	public void addPhoto(ActionEvent e) {
		FileChooser photoPicker = new FileChooser();
		photoPicker.setTitle("Please select an image to import");
		photoPicker.getExtensionFilters().addAll(
				new ExtensionFilter("Image Files", "*.bmp", "*.BMP", "*.gif", "*.GIF", "*.jpg", "*.JPG", "*.png",
						"*.PNG"));
				
		File chosenPicture = photoPicker.showOpenDialog(null);
		
		if (chosenPicture != null) {
			Image image = new Image(chosenPicture.toURI().toString());
			String name = chosenPicture.getName();
			Calendar photoDate = Calendar.getInstance();
			photoDate.set(Calendar.MILLISECOND,0);
			Photo photoToBeAdded = new Photo(name,"", photoDate);
			if (currentAlbum.getPhotos()==null || currentAlbum.getPhotos().size()==0) {
				list.add(photoToBeAdded);
				currentAlbum.getPhotos().add(photoToBeAdded);
			}
			else {
			for (Photo currentPhoto : currentAlbum.getPhotos()) {
				if (currentPhoto.equals(photoToBeAdded)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error in Adding New Photo");
					//alert.setHeaderText("Photo Add Error.");
					alert.setContentText("This photo already exists in the album");

					alert.showAndWait();
					//return;
				}
			}
			list.add(photoToBeAdded);
			currentAlbum.getPhotos().add(photoToBeAdded);
			}
			
			displayList();			
			//photos.getItems().add(photoToBeAdded);
			//selectedAlbum.getPhotos().add(photoToBeAdded);
			//CommonFunctions.saveData(users);
		}
		
	}
	public void delPhoto(ActionEvent e) {
	if (photoList.getSelectionModel().getSelectedItem() != null) {
    		
    		String photoToBeRemoved = photoList.getSelectionModel().getSelectedItem().getPhotoName();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + photoToBeRemoved + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();

			int currIndex = photoList.getSelectionModel().getSelectedIndex();
			if (alert.getResult() == ButtonType.YES) {
				//List<Album> currentUserAlbumlist= currentUser.getAlbums();
				//for (Album alb:currentUserAlbumList)
				currentAlbum.getPhotos().remove(photoList.getSelectionModel().getSelectedItem());
				//currentUser.getAlbums().getPhotos().remove(photoList.getSelectionModel().getSelectedItem());
				//photoList.getItems().remove(currIndex);
				list.remove(photoList.getSelectionModel().getSelectedItem());
				displayList();
				//AlbumList.remove(currIndex);
				//albumStringList.remove(currIndex);
			}
    	}else {
    		Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setHeaderText("No selected album to delete!");
			alert2.showAndWait();
		}
	}
	public void movePhoto(ActionEvent e) {
	
	}
	public void copyPhoto(ActionEvent e) {
		
	}
	public void addTag(ActionEvent e) {
		
	}
	public void delTag(ActionEvent e) {
		
	}
	public void prevPhoto(ActionEvent e) {
		
	}
	public void nextPhoto(ActionEvent e) {
		
	}
	public void displayList() {
		/*for (Photo photo:list) {
			if (!list.contains(photo)) {
			list.add(photo);
			}
		}*/
		obsList = FXCollections.observableArrayList(list); 

		photoList.setItems(obsList); 
	}
	
	
	
	
}
