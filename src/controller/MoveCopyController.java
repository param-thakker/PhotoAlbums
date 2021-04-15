package controller;
import java.awt.Button;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class MoveCopyController {
	
	@FXML 
	Button mcConfirm;
	@FXML
	Button mcCancel;
	@FXML
	ListView mcAlbumList;
	
	List<Album> albums = new ArrayList<>();
	private ObservableList<Album> obsList;
	private User currUser;
	private Photo selectedPhoto;
	
	public void start(Stage mainStage, User currentUser, Photo photoToMove) {
		albums = currentUser.getAlbums();
		currUser = currentUser;
		selectedPhoto = photoToMove;
		displayList();
	}
	
	public void displayList() {
    	obsList = FXCollections.observableArrayList(albums);
		mcAlbumList.setItems(obsList);
	}
}
