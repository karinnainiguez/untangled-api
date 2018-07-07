package com.untangled.api.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathController {
	
	@RequestMapping(
			value="/paths/{start}/{end}", 
			method = RequestMethod.GET
			)
	public List<Path> getPaths(@PathVariable String start, @PathVariable String end) {
		// create list of topics to return 
		return Arrays.asList(
				new Path(1, new ArrayList<String>(Arrays.asList("thisis my first url string", "this is my second url string", "put in from controller"))), 
				new Path(4, new ArrayList<String>(Arrays.asList("second path url1", "secondpath url2"))), 
				new Path(5, new ArrayList<String>(Arrays.asList("third path only has one")))
				);
	}

}
