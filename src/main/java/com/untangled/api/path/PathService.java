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
import com.untangled.api.link.Link;
import com.untangled.api.page.Page;

@Service
public class PathService {

	RestTemplate restTemplate = new RestTemplate();

	public List<Path> getAllPaths() {
		// create list of topics to return
		return Arrays.asList(
				new Path(1,
						new ArrayList<String>(Arrays.asList("thisis my first url string",
								"this is my second url string", "put in from controller"))),
				new Path(4, new ArrayList<String>(Arrays.asList("second path url1", "secondpath url2"))),
				new Path(5, new ArrayList<String>(Arrays.asList("third path only has one"))));
	}

	public Page generatePage(String pageName) {
		// format page name without whitespace
		String formatName = pageName.replaceAll(" ", "_");
		formatName = pageName.replaceAll("%20", "_");

		// get response from external API as string
		String stringResponse = restTemplate.getForObject(
				"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&pllimit=max&plnamespace=0&titles="
						+ formatName,
				String.class);

		if (stringResponse.length() < 300) {
			return null;
		}
		// truncate string to only the page info.
		String subString = stringResponse.substring(stringResponse.indexOf("pageid") - 2,
				stringResponse.lastIndexOf("]") + 2);

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

	public void generatePaths(String start, String end) {

		// create pages from start and end.
		Page startPage = generatePage(start);

		Page endPage = generatePage(end);

		// create a graph from the start page

		Graph<Page, DefaultEdge> directedGraph = new DefaultDirectedGraph<Page, DefaultEdge>(DefaultEdge.class);

		directedGraph.addVertex(startPage);
		buildChildNodes(directedGraph, startPage, 2);

		// Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String,
		// DefaultEdge>(DefaultEdge.class);
		//
		// directedGraph.addVertex("a");
		// directedGraph.addVertex("b");
		// directedGraph.addVertex("c");
		// directedGraph.addVertex("d");
		// directedGraph.addVertex("e");
		// directedGraph.addVertex("f");
		// directedGraph.addVertex("g");
		// directedGraph.addVertex("h");
		// directedGraph.addVertex("i");
		// directedGraph.addEdge("a", "b");
		// directedGraph.addEdge("b", "d");
		// directedGraph.addEdge("d", "c");
		// directedGraph.addEdge("c", "a");
		// directedGraph.addEdge("e", "d");
		// directedGraph.addEdge("e", "f");
		// directedGraph.addEdge("f", "g");
		// directedGraph.addEdge("g", "e");
		// directedGraph.addEdge("h", "e");
		// directedGraph.addEdge("i", "h");
		// directedGraph.addEdge("i", "e");

		// // get paths from start (i)
		//
		// DijkstraShortestPath<Page, DefaultEdge> dijkstraAlg = new
		// DijkstraShortestPath<>(directedGraph);
		//
		// SingleSourcePaths<Page, DefaultEdge> iPaths =
		// dijkstraAlg.getPaths(startPage);
		//
		// // return best path only to end
		// System.out.println(iPaths.getPath(endPage) + "\n");

		// for all the possible paths

		// AllDirectedPaths<Page, DefaultEdge> dirPaths = new AllDirectedPaths<Page,
		// DefaultEdge>(directedGraph);
		// List<GraphPath<Page, DefaultEdge>> pathCollection =
		// dirPaths.getAllPaths(startPage, endPage, true, 9);
		// System.out.println(pathCollection);
		//
		// for ( GraphPath<Page, DefaultEdge> path : pathCollection) {
		// System.out.println(((Link) path.getVertexList()).getTitle());
		// }
		//

		// create list of topics to return
		// return pathCollection;
	}

	// helper method to build child notes

	private void buildChildNodes(Graph<Page, DefaultEdge> graph, Page parent, int countdown) {
		if (countdown < 1) {
			return;
		} else {
			for (Link link : parent.getLinks()) {
				
				Page childPage = generatePage(link.getTitle());
				
				if (childPage != null) {
					System.out.println("MADE CHILD PAGE");
					System.out.println(childPage.getTitle());
					graph.addVertex(childPage);
					graph.addEdge(parent, childPage);
					buildChildNodes(graph, childPage, (countdown - 1));
				}
			}
		}
	}

	public void newGeneratePaths(String start, String end) {

		// create a graph from the start page

		Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		directedGraph.addVertex(start);
		newBuildChildNodes(directedGraph, start, 2);
		
		// for all the possible paths

		 AllDirectedPaths<String, DefaultEdge> dirPaths = new AllDirectedPaths<String, DefaultEdge>(directedGraph);
		 
		 List<GraphPath<String, DefaultEdge>> pathCollection = dirPaths.getAllPaths(start, end, true, 3);
		 
		 System.out.println(pathCollection);
	}

	// NEW helper method to build child notes

	private void newBuildChildNodes(Graph<String, DefaultEdge> graph, String parent, int countdown) {
		if (countdown < 1) {
			return;
		} else {

			Page parentPage = generatePage(parent);
			if (parentPage != null) {
				for (Link link : parentPage.getLinks()) {
					System.out.println("MADe CHILD");
					System.out.println(link.getTitle());
					graph.addVertex(link.getTitle());
					graph.addEdge(parent, link.getTitle());
					newBuildChildNodes(graph, link.getTitle(), (countdown - 1));
				}
			}
		}
	}

}
