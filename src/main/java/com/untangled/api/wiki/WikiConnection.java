package com.untangled.api.wiki;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "LINKS_TO")
public class WikiConnection {
	
	@Id @GeneratedValue private Long id;
	
	@StartNode
	private Wiki startPage;
	
	@EndNode
	private Wiki endPage;
	
	public WikiConnection() {}
	
	public WikiConnection(Wiki startPage, Wiki endPage) {
		super();
		this.startPage = startPage;
		this.endPage = endPage;
	}
	
	public WikiConnection(Long id, Wiki startPage, Wiki endPage) {
		super();
		this.id = id;
		this.startPage = startPage;
		this.endPage = endPage;
	}
	
	

}
