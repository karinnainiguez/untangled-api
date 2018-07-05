package com.untangled.api.path;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathController {
	
	@RequestMapping("/paths")
	public List<Path> getPaths() {
		// create list of topics to return 
		return Arrays.asList(
				new Path(1), 
				new Path(4), 
				new Path(5)
				);
	}

}
