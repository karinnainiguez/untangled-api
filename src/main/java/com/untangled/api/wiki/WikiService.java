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
		
//		repo.deleteAll();
		connect("adding first", "second");
		
		
		
		System.out.println(repo.findAll());
		return "SOMETHING IS HAPPENING :) ";
	}
	
	// HELPER CONNECT PARENT AND CHILD (creates wiki if not existant)
	private void connect(String parent, String child) {
		repo.addConnection(parent, child);
	}

}
