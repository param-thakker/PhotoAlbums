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
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
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
/**
 * The PhotoDisplayController class handles the user control and logic in the PhotoDisplay screen
 * @author Jonathan Lu
 * @author Param Thakker
 *
 */
public class PhotoDisplayController {
	/**
	 * The current Album accessed
	 */
	private Album currentAlbum;
	/**
	 * The User that is currently logged in
	 */
	private User currentUser;
	
	/**
	 * The FXML Button to enable the addition of a new Tag
	 */
	@FXML
	Button addTag;
	/**
	 * The FXML Button to delete an existing Tag
	 */
	@FXML
	Button delTag;
	/**
	 * The FXML Button to start moving a photo from one Album to another
	 */
	@FXML
	Button movePhoto;
	/**
	 * The FXML Button to start copying a photo from one Album to another
	 */
	@FXML
	Button copyPhoto;
	/**
	 * The FXML Button to go back to the AlbumDisplay screen
	 */
	@FXML
	Button backToAlbum;
	/**
	 * The FXML Button to cancel (re)captioning a Photo
	 */
	@FXML
	Button captionCancel;
	/**
	 * The FXML ListView displaying the current Photos in the Album
	 */
	@FXML
	ListView<Photo> photoList;
	/**
	 * The FXML TextField displaying the date that the selected Photo was captured on
	 */
	@FXML
	TextField dateCapturedField;
	/**
	 * The FXML TextField displaying the caption of the selected Photo
	 */
	@FXML
	TextField caption;
	/**
	 * The FXML TextField displaying the name of the accessed Album
	 */
	@FXML
	TextField albumHeader;
	/**
	 * The FXML TextField allowing the User to insert a NAME parameter for a Tag
	 */
	@FXML
	TextField tagNameField;
	/**
	 * The FXML TextField allowing the User to insert a VALUE parameter for a Tag
	 */
	@FXML
	TextField tagValueField;
	/**
	 * The FXML ListView displaying the Tags of the selected Photo
	 */
	@FXML
	ListView<Tag> tagListView;
	/**
	 * The FXML Button to enable the (re)captioning of a Photo
	 */
	@FXML
	Button addCaptionButton;
	/**
	 * The FXML Button to confirm the (re)captioning of a Photo
	 */
	@FXML	
	Button confirmCaption;
	/**
	 * The FXML ImageView to display the currently selected Photo
	 */
	@FXML
	ImageView photoView;
	/**
	 * The FXML Button to confirm the addition of new Tags to a Photo
	 */
	@FXML	
	Button confirmTag;
	

	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy '@' hh:mm a");

	List<Photo> list=new ArrayList<>();
	/**
	 * The most recently used caption 
	 */
	String lastCaption = "";
	
	/**
	 * The ObservableList displaying the List of Photos
	 */
	private ObservableList<Photo> obsList;  

	
	/**
	 * The main start method of PhotoDisplayController
	 * @param mainStage the Stage to execute on
	 * @param album the currently accessed Album
	 * @param user the User that's currently logged in
	 * @throws IOException
	 */
	public void start(Stage mainStage,Album album, User user) throws IOException{
		
		this.currentAlbum=album;
		this.currentUser=user;
		displayList();
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
		
		movePhoto.setOnAction(e -> {
			try {
				movePhoto();
			}catch (IOException e1) {
				System.out.println("bruh exception");
			}
		});
		
		copyPhoto.setOnAction(e -> {
			try {
				copyPhoto();
			}catch (IOException e1) {
				System.out.println("bruh exception");
			}
		});
		
		photoList
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				photoDetail(mainStage));
	
		
		
	}
	/**
	 * Displays the image, caption, date taken, and Tags of the current Photo on the bottom display area upon selection of a ListView item
	 * @param mainStage the stage to execute on
	 */
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
			List<Tag> tags=photo.getPhotoTags();
			tagListView.setItems(FXCollections.observableArrayList(photoList.getSelectionModel().getSelectedItem().getPhotoTags()));
		}
	}
	/**
	 * Displays the image, caption, date taken, and Tags of the current Photo on the bottom display area without selection of a ListView item
	 * @param mainStage the stage to execute on
	 */
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
	/**
	 * Redirects back to the AlbumDisplay screen
	 * @throws IOException
	 */
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
	/**
	 * Opens up a FileChooser to allow the User to add a new Photo, then add it into currentAlbum if the conditions are satisfied
	 * @param e the ActionEvent to activate addPhoto()
	 */
	public void addPhoto(ActionEvent e) {
		FileChooser photoPicker = new FileChooser();
		photoPicker.setTitle("Please select an image to import");
		photoPicker.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.bmp", "*.BMP", "*.gif", "*.GIF", "*.jpg", "*.JPG", "*.png", "*.PNG"));
		//System.out.println("I am here");		
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
				currentAlbum.addPhoto(photoToBeAdded);
			}
			else {
				for (Photo currentPhoto : currentAlbum.getPhotos()) {
					if (currentPhoto.source.equals(photoToBeAdded.source)) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error in Adding New Photo");
						//alert.setHeaderText("Photo Add Error.");
						alert.setContentText("This photo already exists in the album");
	
						alert.showAndWait();
						return;
					}
				}
				list.add(photoToBeAdded);
				currentAlbum.addPhoto(photoToBeAdded);
			}
				
			displayList();			
			//photos.getItems().add(photoToBeAdded);
			//selectedAlbum.getPhotos().add(photoToBeAdded);
			//CommonFunctions.saveData(users);
		}
		
	}
	/**
	 * Deletes a Photo from the current Album 
	 * @param e the ActionEvent to activate delPhoto()
	 */
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
				tagListView.setItems(null);
			}
    	}else {
    		Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setHeaderText("No selected album to delete!");
			alert2.showAndWait();
		}
	}
	/**
	 * Enables the moving of a Photo from this Album to another by opening up the MoveCopyPhoto screen
	 * @throws IOException
	 */
	public void movePhoto() throws IOException {
		//show the move/copy screen on top of the current screen, without hiding the current album
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/MoveCopyPhoto.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.show();
		System.out.println("Move Photo");
	}
	/**
	 * Enables the copying of a Photo from this Album to another by opening up the MoveCopyPhoto screen
	 * @throws IOException
	 */
	public void copyPhoto() throws IOException{
		//show the move/copy screen on top of the current screen, without hiding the current album
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/MoveCopyPhoto.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Scene scene = new Scene(root,600,400);
		stage.setScene(scene);
		stage.show();
	}
	/** 
	 * Enables the addition of Tags to the selected Photo
	 * @param e the ActionEvent to activate addTag()
	 */
	public void addTag(ActionEvent e) {
		tagNameField.setVisible(true);
		tagValueField.setVisible(true);
		confirmTag.setVisible(true);
	}
	/**
	 * Deletes the currently selected Tag from the selected Photo.
	 * @param e the ActionEvent to activate delTag()
	 */
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
	/**
	 * Selects the preceding Photo in the Album
	 * @param e the ActionEvent to activate prevPhoto
	 */
	public void prevPhoto(ActionEvent e) {
		//System.out.println("yuh");
		if (!photoList.getSelectionModel().isEmpty() && photoList.getSelectionModel().getSelectedIndex() != 0) {
			photoList.getSelectionModel().select(photoList.getSelectionModel().getSelectedIndex()-1);
			photoDetailV2();
		}
	}
	/**
	 * Selects the next Photo in the Album
	 * @param e the ActionEvent to activate nextPhoto
	 */
	public void nextPhoto(ActionEvent e) {
		//System.out.println("thank u next");
		if (!photoList.getSelectionModel().isEmpty() /*&& not greater than size of available list*/ ) {
			photoList.getSelectionModel().select(photoList.getSelectionModel().getSelectedIndex()+1);
			photoDetailV2();
		}
	}
	/**
	 * Confirms the addition of new Tags to the selected Photo should it meet the criteria
	 * @param e the ActionEvent to activate confirmAddTag()
	 */
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
	/**
	 * Enables the captioning (or recaptioning) the currently selected Photo.
	 * @param e the ActionEvent to activate addCaption()
	 */
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
	/**
	 * Confirms the captioning (or recaptioning) of the currently selected Photo
	 * @param e the ActionEvent to activate confirm()
	 */
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
	/**
	 * Cancels the current attempted caption/recaptioning of the currently selected Photo, and sets the caption to the last known caption
	 * @param e the ActionEvent to activate captionCancel()
	 */
	public void captionCancel(ActionEvent e) {
		caption.setEditable(false);
		confirmCaption.setVisible(false);
		captionCancel.setVisible(false);
		caption.setText(lastCaption);
	}
	/**
	 * Displays the thumbnails of all the images in the album in the top bar, as well as their respective captions
	 */
	public void displayList() {
		
		obsList = FXCollections.observableArrayList(currentAlbum.getPhotos()); 

		photoList.setItems(obsList); 
		photoList.setCellFactory(listView -> new ListCell<Photo>() {
		    private ImageView photoView = new ImageView();
		    @Override
		    public void updateItem(Photo photo, boolean empty) {
		        super.updateItem(photo, empty);
		        if (empty) {
		            setText(null);
		            setGraphic(null);
		        } else {
		            Image image = new Image(photo.source);
		            photoView.setFitHeight(50);
		            photoView.setFitWidth(70);
		            photoView.setImage(image);
		            setText(photo.photoCaption);
		            setGraphic(photoView);
		        }
		    }
		});
	}
	
	
	
	
}
