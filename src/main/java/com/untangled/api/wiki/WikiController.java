package com.untangled.api.wiki;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WikiController {
	
	@Autowired
	private WikiService wikiService;
	
	@RequestMapping("/graphOut/{start}")
	public String testing(@PathVariable String start) {
		return wikiService.mapGraph(start);
	}
	
	@RequestMapping("/allPaths/{start}/{end}")
	public Iterable<Map<String, Wiki>> allPaths(@PathVariable String start, @PathVariable String end) {
		return wikiService.allPaths(start, end);
	}
	
	@RequestMapping("/findPaths/{start}/{end}")
	public Iterable<Map<String, Wiki>> findPaths(@PathVariable String start, @PathVariable String end) {
		return wikiService.findPaths(start, end);
	}

}
