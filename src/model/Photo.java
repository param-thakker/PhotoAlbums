package model;
import java.io.*;
import java.util.*;

public class Photo implements Serializable {

	private static final long serialVersionUID = 6955723612371190680L;
	
	public String photoCaption;
	public String photoName;
	public Calendar photoDate;
	public List<Tag> photoTags;
  //	private File pics;

	
	public Photo(String name,String caption, Calendar date) {
		this.photoName=name;
		this.photoCaption=caption;
		this.photoDate=date;
		this.photoTags=new ArrayList<>();
		this.photoDate.set(Calendar.MILLISECOND, 0);
		
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

	public String getPhotoName(){
		return this.photoName;
	}
	public void setPhotoCaption(String caption) {
		this.photoCaption=caption;
	}
}
