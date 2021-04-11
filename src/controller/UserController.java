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
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

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
	ListView albumList;
	@FXML
	ListView mcAlbumList;
	@FXML
	ListView photoList;
	@FXML
	ListView searchResults;
	
	
	public void start(Stage mainStage) throws IOException{
		
	}
	
	public void search(ActionEvent e) throws IOException{
		
	}
    public void logout(ActionEvent e) throws IOException{
		
	}
    public void newAlbum(ActionEvent e) throws IOException{
		
	}
    public void renameAlbum(ActionEvent e) throws IOException{
		
	}
    public void delAlbum(ActionEvent e) throws IOException{
    	
    }
    public void openAlbum(ActionEvent e) throws IOException{
    	
    }
}
