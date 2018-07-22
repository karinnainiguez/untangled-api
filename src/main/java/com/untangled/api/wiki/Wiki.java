package com.untangled.api.wiki;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Wiki {
	@Id @GeneratedValue private Long id;
	
	private String title;
	
	@Relationship(type = "LINKS_TO")
	private List<Wiki> links = new ArrayList<>();
	
	public Wiki() {}
	
	public Wiki(String title) {
		super();
		this.title = title;
	}

	public Wiki(String title, List<Wiki> links) {
		super();
		this.title = title;
		this.links = links;
	}

	public Wiki(Long id, String title, List<Wiki> links) {
		super();
		this.id = id;
		this.title = title;
		this.links = links;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Wiki> getLinks() {
		return links;
	}

	public void setLinks(List<Wiki> links) {
		this.links = links;
	}

}
