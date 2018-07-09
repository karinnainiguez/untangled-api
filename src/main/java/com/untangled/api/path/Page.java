package com.untangled.api.path;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Page {
	private String title;
	private Link[] links;
	
	public Page() {}
	
	public Page(String title, Link[] links) {
		super();
		this.title = title;
		this.links = links;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Link[] getLinks() {
		return links;
	}

	public void setLinks(Link[] links) {
		this.links = links;
	}
	

}
