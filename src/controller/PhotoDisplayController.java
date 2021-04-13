package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;
import java.text.*;

public class PhotoDisplayController {
	
	private Album currentAlbum;
	private User currentUser;
	
	
	@FXML
	Button addTag;
	@FXML
	Button delTag;
	@FXML
	Button backToAlbum;
	@FXML
	Button captionCancel;
	@FXML
	ListView<Photo> photoList;
	@FXML
	TextField dateCapturedField;
	@FXML
	TextField caption;
	@FXML
	TextField albumHeader;
	@FXML
	TextField Tags;
	@FXML
	TextField tagNameField;
	@FXML
	TextField tagValueField;
	@FXML
	ListView<Tag> tagListView;
	@FXML
	Button addCaptionButton;
	@FXML	
	Button confirmCaption;
	@FXML
	ImageView photoView;
	@FXML	
	Button confirmTag;
	

	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy '@' hh:mm a");

	List<Photo> list=new ArrayList<>();
	String lastCaption = "";
	
	
	private ObservableList<Photo> obsList;  

	

	public void start(Stage mainStage,Album album, User user) throws IOException{
		
		displayList();
		this.currentAlbum=album;
		this.currentUser=user;
		confirmCaption.setVisible(false);

		captionCancel.setVisible(false);
		albumHeader.setText(album.albumName);
		

		tagNameField.setVisible(false);
		tagValueField.setVisible(false);
		confirmTag.setVisible(false);

		
		backToAlbum.setOnAction(e -> {
			  try {
				  backToAlbum();
				  mainStage.hide();
			  }catch (IOException e1) {
			  }
		  });
		
		photoList
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				photoDetail(mainStage));
	
		
		
	}
	private void photoDetail(Stage mainStage) { 
		if (!photoList.getSelectionModel().isEmpty()) {
			addCaptionButton.setVisible(true);
			Photo photo=photoList.getSelectionModel().getSelectedItem();
			Image p = new Image(photo.source);
			photoView.setImage(p);
			String cap=photo.getPhotoCaption();
			Calendar date=photo.getPhotoDate();
			caption.setText(cap);
			dateCapturedField.setText(dateTimeformat.format(date.getTime()));
			
		}
	}
	private void photoDetailV2() {
		if (!photoList.getSelectionModel().isEmpty()) {
			addCaptionButton.setVisible(true);
			Photo photo=photoList.getSelectionModel().getSelectedItem();
			Image p = new Image(photo.source);
			photoView.setImage(p);
			String cap=photo.getPhotoCaption();
			Calendar date=photo.getPhotoDate();
			caption.setText(cap);

			List<Tag> tags=photo.getPhotoTags();
			caption.setText(cap);
			dateCapturedField.setText(dateTimeformat.format(date.getTime()));
			tagListView.setItems(FXCollections.observableArrayList(photoList.getSelectionModel().getSelectedItem().getPhotoTags()));
		}
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
			//Date photoDate = Calendar.getInstance();
			Calendar photoDate = Calendar.getInstance();
			photoDate.setTimeInMillis(chosenPicture.lastModified());
			
			//photoDate.set(Calendar.MILLISECOND,0);
			Photo photoToBeAdded = new Photo(name,"", photoDate, chosenPicture.toURI().toString());
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
				photoView.setImage(null);
				caption.clear();
				dateCapturedField.clear();
				//clear tag list view after deletion
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
		tagNameField.setVisible(true);
		tagValueField.setVisible(true);
		confirmTag.setVisible(true);
	}
	public void delTag(ActionEvent e) {
		if (tagListView.getSelectionModel().getSelectedItem() != null) {
			Photo photoToBeRemoved = photoList.getSelectionModel().getSelectedItem();
    		Tag tagToBeRemoved = tagListView.getSelectionModel().getSelectedItem();
			Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + tagToBeRemoved + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();

			int currIndex = tagListView.getSelectionModel().getSelectedIndex();
			if (alert.getResult() == ButtonType.YES) {
				photoToBeRemoved.getPhotoTags().remove(tagToBeRemoved);
				tagListView.setItems(FXCollections.observableArrayList(photoList.getSelectionModel().getSelectedItem().getPhotoTags()));
			}
    	} else {
    		Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setHeaderText("No selected tag name-value to delete!");
			alert2.showAndWait();
		}
	}
	public void prevPhoto(ActionEvent e) {
		System.out.println("yuh");
		if (!photoList.getSelectionModel().isEmpty() && photoList.getSelectionModel().getSelectedIndex() != 0) {
			photoList.getSelectionModel().select(photoList.getSelectionModel().getSelectedIndex()-1);
			photoDetailV2();
		}
	}
	public void nextPhoto(ActionEvent e) {
		System.out.println("thank u next");
		if (!photoList.getSelectionModel().isEmpty() /*&& not greater than size of available list*/ ) {
			photoList.getSelectionModel().select(photoList.getSelectionModel().getSelectedIndex()+1);
			photoDetailV2();
		}
	}
	public void confirmAddTag(ActionEvent e) {
		if ((tagNameField.getText().trim().length()==0 || tagNameField.getText()==null) ) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Tag name can't be empty! Please enter legitimate values");
			alert.showAndWait();
		}
		else {
	
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to add this tag name-value pair?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					Photo selectedPhoto=photoList.getSelectionModel().getSelectedItem();
					Tag tag=new Tag(tagNameField.getText(),tagValueField.getText());
					selectedPhoto.getPhotoTags().add(tag);
					//obsList = FXCollections.observableArrayList(list); 

					//photoList.setItems(obsList); 
					tagListView.setItems(FXCollections.observableArrayList(photoList.getSelectionModel().getSelectedItem().getPhotoTags()));
					tagNameField.clear();
					tagValueField.clear();
					
					tagNameField.setVisible(false);
					tagValueField.setVisible(false);
					confirmTag.setVisible(false);
				
				}
	
			
		}
	}
	public void addCaption(ActionEvent e) {
		if (photoList.getSelectionModel().getSelectedItem()==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No photo selected to add Caption");
			alert.showAndWait();
		}
		else {
			lastCaption = caption.getText();
			caption.setEditable(true);
			confirmCaption.setVisible(true);
			captionCancel.setVisible(true);
		}
	}
	public void confirm(ActionEvent e) {
		if (caption.getText().trim().length()==0 || caption.getText()==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No caption entered! Please enter a caption");
			alert.showAndWait();
		}
		else {

			//String newUser=userField.getText();
				
			
				Alert alert = new Alert(AlertType.CONFIRMATION, "Set " + caption.getText() + " as caption for this picture?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			//	alert.setTitle("Add new user");

	
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
		
					Photo selectedPhoto=photoList.getSelectionModel().getSelectedItem();
					selectedPhoto.setPhotoCaption(caption.getText());
					
					displayList();
					caption.setEditable(false);
					confirmCaption.setVisible(false);
					captionCancel.setVisible(false);
				
				}
	
			
		}
	}
	public void captionCancel(ActionEvent e) {
		caption.setEditable(false);
		confirmCaption.setVisible(false);
		captionCancel.setVisible(false);
		caption.setText(lastCaption);
	}
	public void displayList() {
		
		obsList = FXCollections.observableArrayList(list); 

		photoList.setItems(obsList); 
	}
	
	
	
	
}
