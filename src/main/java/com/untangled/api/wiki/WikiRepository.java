package com.untangled.api.wiki;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface WikiRepository extends Neo4jRepository<Wiki, Long> {
	
	Wiki findByTitle(String title);
	
	@Query("MERGE (w:Wiki{title: {title}}) RETURN w")
	Wiki addWiki(@Param("title") String title);
	
	@Query("MERGE (p:Wiki{title: {parent}}) MERGE (c:Wiki{title: {child}}) MERGE (p)-[:LINKS_TO]->(c)")
	void addConnection(@Param("parent") String parent, @Param("child") String child);
}
