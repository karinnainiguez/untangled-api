package com.untangled.api.wiki;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WikiService {
	
	@Autowired 
	WikiRepository repo;
	
	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper objectMapper = new ObjectMapper();
	
	public Iterable<Map<String, Wiki>> allPaths(String start, String end) {
		Iterable<Map<String, Wiki>> possiblePaths = repo.findPaths(start, end);
		int count = 0;
		for( Map<String, Wiki> row : possiblePaths ) { count = count + 1; }
		
		System.out.println(count);
		if( count < 1 ) {
			recursiveChildren(start, 2);
			possiblePaths = repo.findPaths(start, end);
		}
		
		return possiblePaths;

	}
	
	public Iterable<Map<String, Wiki>> findPaths(String start, String end) {
		Iterable<Map<String, Wiki>> response = repo.findPaths(start, end);
		System.out.println(response);
		return response;
	}
	
	public String mapGraph(String title) {
		recursiveChildren(title, 2);
		return "SOMETHING IS HAPPENING :) ";
	}
	
	public void clearDB() {
		repo.clearDB();
	}
	
	
	// HELPER - RECURSIVEEEEE build child nodes from parent string
	private void recursiveChildren(String parent, int countdown) {
		if ( countdown < 1 ) {
			return;
		}
		ArrayList<String> children = apiCall(parent);
		for( String c : children) {
			repo.addConnection(parent, c);
			recursiveChildren(c, countdown - 1);
		}
		
	}
	
	// HELPER - turn string into arraylist
	@SuppressWarnings("unchecked")
	private ArrayList<String> stringToArray(String arrString) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<String> response = objectMapper.readValue(arrString, ArrayList.class);
		return response;
	}
	
	// HELPER - parse string to arrayList of strings format
	private String parser(String original) {
		if (original.length() < 300) {
			return "[]";
		}
		String subString = original.substring(original.indexOf("links") + 7,
				original.lastIndexOf("]") + 1);
		String arrString = subString.replaceAll("\\{\"ns\":0,\"title\":", "").replaceAll("\\}", "");
		return arrString;
	}
	
	// HELPER - call wikipedia api to get list of children
	private ArrayList<String> apiCall(String title) {
		String stringResponse = restTemplate.getForObject(
				"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&pllimit=max&plnamespace=0&titles="
						+ formatTitle(title),
				String.class);
		
		String newString = parser(stringResponse);
		ArrayList<String> response = new ArrayList<>();
		try {
			response = stringToArray(newString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	// HELPER - format titles
	private String formatTitle(String title) {
		String newTitle = title.replaceAll("%20", "_").replaceAll(" ", "_");
		return newTitle;
	}

	

}
