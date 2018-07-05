package com.untangled.api.path;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathController {
	
	@RequestMapping("/paths")
	public String getPaths() {
		return "All possible paths";
	}

}
