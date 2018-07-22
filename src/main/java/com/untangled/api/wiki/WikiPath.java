package com.untangled.api.wiki;

import java.util.ArrayList;

public class WikiPath {
	
	private ArrayList<Wiki> path;
	
	public WikiPath() {}

	public WikiPath(ArrayList<Wiki> path) {
		super();
		this.path = path;
	}

	public ArrayList<Wiki> getPath() {
		return path;
	}

	public void setPath(ArrayList<Wiki> path) {
		this.path = path;
	}
	
}
