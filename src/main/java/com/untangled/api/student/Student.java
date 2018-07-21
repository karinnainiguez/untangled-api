package com.untangled.api.student;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Student {
	
	@SuppressWarnings("deprecation")
	@GraphId private Long id;

}
