package com.untangled.api.path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class PathController {
	RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping("/paths/{start}/{end}")
	public List<Path> getPaths(@PathVariable String start, @PathVariable String end) {
		// create list of topics to return 
		return Arrays.asList(
				new Path(1, new ArrayList<String>(Arrays.asList("thisis my first url string", "this is my second url string", "put in from controller"))), 
				new Path(4, new ArrayList<String>(Arrays.asList("second path url1", "secondpath url2"))), 
				new Path(5, new ArrayList<String>(Arrays.asList("third path only has one")))
				);
	}
	
	@RequestMapping("/testapi")
	public String getResponse() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange(
				"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&pllimit=max&plnamespace=0&titles=Software_development", 
				HttpMethod.GET, 
				entity, 
				String.class
				).getBody();
	}
	
	@RequestMapping("/newTest")
	public void newResponse() {
		String stringResponse = restTemplate.getForObject(
				"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&pllimit=max&plnamespace=0&titles=Software_development", 
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
		
		
		
		
		
//		System.out.println("\n\nMe trying to make it into an array");
//		String[] stringArr = subString.split(",");
//		System.out.println(Arrays.toString(stringArr));
		
//		Hashtable<String, String> hashResponse = restTemplate.getForObject(
//				"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&pllimit=max&plnamespace=0&titles=Software_development", 
//				Hashtable.class
//				);
//		return hashResponse;
//		
		
		
//		
//		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		HttpEntity<Hashtable> entity = new HttpEntity<Hashtable>(headers);
//		Hashtable hashResponse = restTemplate.exchange(
//				"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&pllimit=max&plnamespace=0&titles=Software_development", 
//				HttpMethod.GET, 
//				entity, 
//				Hashtable.class
//				).getBody();
////		System.out.println(hashResponse.get("query").get("pages"));
//		Object queryResponse = hashResponse.get("query");
//		return queryResponse;
	}

}
