package com.untangled.api.path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
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
		// format page name withoug whitespace
		String formatName = pageName.replaceAll(" ", "_");
		
		// get response from external API as string
		String stringResponse = restTemplate.getForObject(
				"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&pllimit=max&plnamespace=0&titles=" + formatName, 
				String.class
				);
		
		// truncate string to only the page info.
		String subString = stringResponse.substring(stringResponse.indexOf("pageid") - 2, stringResponse.lastIndexOf("]") + 2);
		
		// Using ObjectMapper, make a Page instance from string
		ObjectMapper objectMapper = new ObjectMapper();
		
		Page page = null;
		try {
			page = objectMapper.readValue(subString, Page.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// return page instance.
		return page;
	}
	
	public void generatePaths(String start, String end){
		
		// create pages from start and end. 
		Page startPage = generatePage(start);
		
		Page endPage = generatePage(end);
		
		
		// create a graph from the start page 
		
		Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		
		directedGraph.addVertex("a");
        directedGraph.addVertex("b");
        directedGraph.addVertex("c");
        directedGraph.addVertex("d");
        directedGraph.addVertex("e");
        directedGraph.addVertex("f");
        directedGraph.addVertex("g");
        directedGraph.addVertex("h");
        directedGraph.addVertex("i");
        directedGraph.addEdge("a", "b");
        directedGraph.addEdge("b", "d");
        directedGraph.addEdge("d", "c");
        directedGraph.addEdge("c", "a");
        directedGraph.addEdge("e", "d");
        directedGraph.addEdge("e", "f");
        directedGraph.addEdge("f", "g");
        directedGraph.addEdge("g", "e");
        directedGraph.addEdge("h", "e");
        directedGraph.addEdge("i", "h");
        directedGraph.addEdge("i", "e");
		
		// get paths from start (i)
        
        DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(directedGraph);
		
        SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths("i");
		
		// return best path only to end
        System.out.println(iPaths.getPath("c") + "\n");
        
        
        // for all the possible paths
        
        AllDirectedPaths<String, DefaultEdge> dirPaths = new AllDirectedPaths<String, DefaultEdge>(directedGraph);
		List<GraphPath<String, DefaultEdge>> pathCollection = dirPaths.getAllPaths("i", "c", true, 9);
        System.out.println(pathCollection);
        System.out.println(pathCollection.getClass());
        
        for ( GraphPath<String, DefaultEdge> path : pathCollection) {
        	System.out.println(path.getVertexList());
        }
        
		
		
		// create list of topics to return 
//		return pathCollection;
	}

}
