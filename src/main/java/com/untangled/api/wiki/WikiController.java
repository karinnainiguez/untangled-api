package com.untangled.api.wiki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WikiController {
	
	@Autowired
	private WikiService wikiService;
	
	@RequestMapping("/testing")
	public String testing() {
		return wikiService.practice();
	}

}
