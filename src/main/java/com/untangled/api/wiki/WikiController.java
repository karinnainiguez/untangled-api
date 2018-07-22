package com.untangled.api.wiki;

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

}
