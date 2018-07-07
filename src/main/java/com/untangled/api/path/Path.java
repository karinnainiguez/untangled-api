package com.untangled.api.path;

import java.util.ArrayList;

public class Path {

	
	private int length;
	private ArrayList<String> URLCollection;
	
	public Path() {
		
	}
	
	public Path(int length) {
		super();
		this.length = length;
	}
	
	public Path(int length, ArrayList<String> arrayList) {
		super();
		this.length = length;
		URLCollection = arrayList;
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public ArrayList<String> getURLCollection() {
		return URLCollection;
	}
	public void setURLCollection(ArrayList<String> uRLCollection) {
		URLCollection = uRLCollection;
	}
	
}
