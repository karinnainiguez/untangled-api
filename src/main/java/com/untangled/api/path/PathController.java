package com.untangled.api.path;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PathController {
	
	@Autowired
	private PathService pathService;
	
	@RequestMapping("/paths/{start}/{end}")
	public PathCollection getPaths(@PathVariable String start, @PathVariable String end) {
		return pathService.generatePaths(start, end);
	}

}
