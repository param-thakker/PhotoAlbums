package model;
import java.util.*;
import java.io.*;

public class Tag implements Serializable {

	private static final long serialVersionUID = 3430529772963736249L;
	public String tagName;
	public String tagValue;
	
	public Tag(String name, String value) {
		this.tagName=name;
		this.tagValue=value;
	}
	public String getTagName() {
		return this.tagName;
	}
	public String getTagValue() {
		return this.tagValue;
	}
	public String toString() {
		return this.tagName + ":" + this.tagValue;
	}
}
