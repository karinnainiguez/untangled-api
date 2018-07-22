package com.untangled.api.wiki;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface WikiRepository extends Neo4jRepository<Wiki, Long> {
	
	Wiki findByTitle(String title);
}
