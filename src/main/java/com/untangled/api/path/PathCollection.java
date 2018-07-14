package com.untangled.api.path;

import java.util.ArrayList;

public class PathCollection {
	private String from;
	private String to;
	private ArrayList<ArrayList<String>> paths;
	
	
	
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public ArrayList<ArrayList<String>> getPaths() {
		return paths;
	}
	public void setPaths(ArrayList<ArrayList<String>> paths) {
		this.paths = paths;
	}
	

}
