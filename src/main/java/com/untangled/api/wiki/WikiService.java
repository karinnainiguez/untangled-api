package com.untangled.api.wiki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WikiService {
	
	@Autowired 
	WikiRepository repo;
	
	public String practice() {
		System.out.println("INSIDE THE METHOD");
		System.out.println(repo.findAll());
		
		Wiki firstWiki = new Wiki("title numero 1");
		Wiki secondWiki = new Wiki("title numero 2");
		Wiki thirdWiki = new Wiki("title third one");
		
		repo.save(firstWiki);
		repo.save(secondWiki);
		repo.save(thirdWiki);
		
		
		
		System.out.println(repo.findAll());
		return "SOMETHING IS HAPPENING :) ";
	}

}
