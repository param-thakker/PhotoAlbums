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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
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
	ListView albumList;
	@FXML
	ListView mcAlbumList;
	@FXML
	ListView photoList;
	@FXML
	ListView searchResults;
	
	private ObservableList<String> obsList; 
	List<Photo> photoLister = new ArrayList<Photo>();
	
	public void start(Stage mainStage) throws IOException{
		
	}
	
	public void search(ActionEvent e) throws IOException{
		System.out.println("search pushed!");
	}
    public void logout(ActionEvent e) throws IOException{
    	System.out.println("logout pushed!");
	}
    public void newAlbum(ActionEvent e) throws IOException{
    	System.out.println("New Album pushed!");
	}
    public void renameAlbum(ActionEvent e) throws IOException{
    	System.out.println("Rename Album pushed!");
	}
    public void delAlbum(ActionEvent e) throws IOException{
    	System.out.println("Delete Album pushed!");
    }
    public void openAlbum(ActionEvent e) throws IOException{
    	System.out.println("Open Album pushed!");
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
    public void addPhoto(ActionEvent e, Stage mainStage) throws IOException{
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
}
