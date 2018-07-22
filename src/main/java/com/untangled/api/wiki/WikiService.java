package com.untangled.api.wiki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WikiService {
	
	@Autowired 
	WikiRepository repo;
	
	RestTemplate restTemplate = new RestTemplate();
	
	public String practice() {
		System.out.println("INSIDE THE METHOD");
		System.out.println(repo.findAll());
		
		connect("adding first", "second");
		
		apiCall("Ada Developers Academy");
		
		
		
		System.out.println(repo.findAll());
		return "SOMETHING IS HAPPENING :) ";
	}
	
	// HELPER CONNECT PARENT AND CHILD (creates wiki if not existant)
	private void connect(String parent, String child) {
		repo.addConnection(parent, child);
	}
	
	// HELPER - call wikipedia api to get list of children
	private void apiCall(String title) {
		String stringResponse = restTemplate.getForObject(
				"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&pllimit=max&plnamespace=0&titles="
						+ formatTitle(title),
				String.class);
		System.out.println(stringResponse);
	}
	
	// HELPER - format titles
	private String formatTitle(String title) {
		String newTitle = title.replaceAll("%20", "_");
		newTitle = newTitle.replaceAll("%20", "_");
		return newTitle;
	}

}
