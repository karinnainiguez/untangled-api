package com.untangled.api.path;

public class Path {

	
	private int length;
	private String[] URLCollection;
	
	public Path() {
		
	}
	
	public Path(int length, String[] uRLCollection) {
		super();
		this.length = length;
		URLCollection = uRLCollection;
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String[] getURLCollection() {
		return URLCollection;
	}
	public void setURLCollection(String[] uRLCollection) {
		URLCollection = uRLCollection;
	}
	
}
