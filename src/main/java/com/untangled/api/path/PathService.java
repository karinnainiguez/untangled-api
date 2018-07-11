package com.untangled.api.path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.untangled.api.page.Page;

@Service
public class PathService {
	
	RestTemplate restTemplate = new RestTemplate();
	
	public List<Path> getAllPaths(){
		// create list of topics to return 
		return Arrays.asList(
				new Path(1, new ArrayList<String>(Arrays.asList("thisis my first url string", "this is my second url string", "put in from controller"))), 
				new Path(4, new ArrayList<String>(Arrays.asList("second path url1", "secondpath url2"))), 
				new Path(5, new ArrayList<String>(Arrays.asList("third path only has one")))
				);
	}

	public Page generatePage(String pageName) {
		// format page name withoug whitespace
		String formatName = pageName.replaceAll(" ", "_");
		
		// get response from external API as string
		String stringResponse = restTemplate.getForObject(
				"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&pllimit=max&plnamespace=0&titles=" + formatName, 
				String.class
				);
		
		// truncate string to only the page info.
		String subString = stringResponse.substring(stringResponse.indexOf("pageid") - 2, stringResponse.lastIndexOf("]") + 2);
		
		// Using ObjectMapper, make a Page instance from string
		ObjectMapper objectMapper = new ObjectMapper();
		
		Page page = null;
		try {
			page = objectMapper.readValue(subString, Page.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// return page instance.
		return page;
	}
	
	public List<Path> generatePaths(String start, String end){
		
		
		
		
		
		
		
		
		
		
		
		
		
		// create list of topics to return 
		return Arrays.asList(
				new Path(1, new ArrayList<String>(Arrays.asList("thisis my first url string", "this is my second url string", "put in from controller"))), 
				new Path(4, new ArrayList<String>(Arrays.asList("second path url1", "secondpath url2"))), 
				new Path(5, new ArrayList<String>(Arrays.asList("third path only has one")))
				);
	}

}
