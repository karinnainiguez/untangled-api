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
		String formatName = pageName.replaceAll(" ", "_");
		String stringResponse = restTemplate.getForObject(
				"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&pllimit=max&plnamespace=0&titles=" + formatName, 
				String.class
				);
		System.out.println("the ENTIRE response: ");
		System.out.println(stringResponse);
		System.out.println("\n\nMe trying to get into the pages:");
		String subString = stringResponse.substring(stringResponse.indexOf("pageid") - 2, stringResponse.lastIndexOf("]") + 2);
		System.out.println(subString);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		Page page = null;
		try {
			page = objectMapper.readValue(subString, Page.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("\n\nThis may have worked!");
		System.out.println(page.getTitle());
		System.out.println("\n\n and now for the real test");
		System.out.println(page.getLinks());
		return page;
	}
}
