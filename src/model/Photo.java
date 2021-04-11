package model;
import java.io.*;
import java.util.*;

public class Photo implements Serializable {

	private static final long serialVersionUID = 6955723612371190680L;
	
	public String photoCaption;
	public Calendar photoDate;
	public List<Tag> photoTags;
  //	private File pics;

	
	public Photo(String caption, Calendar date) {
		this.photoCaption=caption;
		this.photoDate=date;
		this.photoTags=new ArrayList<>();
		
	}
	public Calendar getPhotoDate() {
		return this.photoDate;
	}
	public List<Tag> getPhotoTags() {
		return this.photoTags;
	}
	public String getPhotoCaption() {
		return this.photoCaption;
	}


}
