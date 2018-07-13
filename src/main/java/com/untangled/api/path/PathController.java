package com.untangled.api.path;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.untangled.api.page.Page;


@RestController
public class PathController {
	
	@Autowired
	private PathService pathService;
	
	@RequestMapping("/paths/{start}/{end}")
	public void getPaths(@PathVariable String start, @PathVariable String end) {
		pathService.generatePaths(start, end);
	}
	
//	@RequestMapping("/testapi")
//	public String getResponse() {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		HttpEntity<String> entity = new HttpEntity<String>(headers);
//		return restTemplate.exchange(
//				"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&pllimit=max&plnamespace=0&titles=Software_development", 
//				HttpMethod.GET, 
//				entity, 
//				String.class
//				).getBody();
//	}
	
	@RequestMapping("/newTest/{pageName}")
	public Page newResponse(@PathVariable String pageName) {
		return pathService.generatePage(pageName);
		
	}

}
