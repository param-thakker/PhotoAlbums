package model;
import java.io.*;
import java.util.*;

public class Album implements Serializable {

	private static final long serialVersionUID = -4143935150417416554L;
	public String albumName;
	public List<Photo> photos;
	
	public Album(String name) {
		this.albumName=name;
		this.photos=new ArrayList<>();
	}
	public String getAlbumName() {
		return this.albumName;
	}
	public List<Photo> getPhotos(){
		return this.photos;
	}
	public int getAlbumSize() {
		return this.photos.size();
	}

	public void addPhoto(Photo photo) {
		photos.add(photo);
	}

	public String toString() {
		return this.albumName;
	}
	

	
	

}
