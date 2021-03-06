package com.untangled.api.path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
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

		// HELPER METHOD- generate page from string with API call
	public Page generatePage(String pageName) {

		// format page name without whitespace
		String formatName = pageName.replaceAll(" ", "_");
		formatName = pageName.replaceAll("%20", "_");

		// get response from external API as string
		String stringResponse = restTemplate.getForObject(
				"https://en.wikipedia.org/w/api.php?action=query&format=json&prop=links&pllimit=max&plnamespace=0&titles="
						+ formatName,
				String.class);

		// return null if string not length of success
		if (stringResponse.length() < 300) { return null; }

		// PARSE - truncate string to only the page info.
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

	// HELPER METHOD - create and return an instance of PathCollection
	private PathCollection generateCollection(String from, String to, List<GraphPath<String, DefaultEdge>> list) {
		ArrayList<ArrayList<String>> pathList = new ArrayList<ArrayList<String>>();

		for (GraphPath<String, DefaultEdge> graphPath : list) {
			graphPath.getVertexList();
			pathList.add((ArrayList<String>) graphPath.getVertexList());
		}

		Comparator<ArrayList> arrListLengthComparator = new Comparator<ArrayList>() {
			@Override
			public int compare(ArrayList o1, ArrayList o2) {
				return Integer.compare(o1.size(), o2.size());
			}
		};

		Collections.sort(pathList, arrListLengthComparator);

		PathCollection path = new PathCollection(from, to, pathList);

		return path;
	}

	public PathCollection generatePaths(String start, String end) {

		// create a graph from the start page

		Graph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		directedGraph.addVertex(start);
		ArrayList<String> nodeCollection = new ArrayList<String>();
		childNodes(directedGraph, start, end, nodeCollection, 0);

		// for all the possible paths

		AllDirectedPaths<String, DefaultEdge> dirPaths = new AllDirectedPaths<String, DefaultEdge>(directedGraph);

		List<GraphPath<String, DefaultEdge>> pathCollection = dirPaths.getAllPaths(start, end, true, 6);

		System.out.println(pathCollection);

		return generateCollection(start, end, pathCollection);
	}

		// HELPER METHOD - generate children from string parent ITERATIVE!
	private void childNodes(Graph<String, DefaultEdge> graph, String parent, String goal, ArrayList<String> collection,
			int countdown) {

		// PARENT

		Page parentPage = generatePage(parent);
		
		if (parentPage != null) {
			for (Link firstChild : parentPage.getLinks()) {
				String firstChildTitle = firstChild.getTitle();
				graph.addVertex(firstChildTitle);
				graph.addEdge(parent, firstChildTitle);
				
				if(firstChildTitle.equals(goal)) {
					countdown = countdown + 1;
					System.out.println("FOUND " + countdown);
				}
				
				
				// FIRST CHILD
					
				Page firstChildPage = generatePage(firstChildTitle);
				
				
				if (firstChildPage != null) {
					for (Link secondChild : firstChildPage.getLinks()) {
						String secondChildTitle = secondChild.getTitle();
						graph.addVertex(secondChildTitle);
						graph.addEdge(firstChildTitle, secondChildTitle);
						
						if(secondChildTitle.equals(goal)) {
							countdown = countdown + 1;
							System.out.println("FOUND " + countdown);
						}
						
						
						// SECOND CHILD
							
						Page secondChildPage = generatePage(secondChildTitle);
						
						if (secondChildPage != null) {
							for (Link thirdChild : secondChildPage.getLinks()) {
								String thirdChildTitle = thirdChild.getTitle();
								graph.addVertex(thirdChildTitle);
								graph.addEdge(secondChildTitle, thirdChildTitle);
								
								if(thirdChildTitle.equals(goal)) {
									countdown = countdown + 1;
									System.out.println("FOUND " + countdown);
								}
								
								
								// THIRD CHILD
									
								Page thirdChildPage = generatePage(thirdChildTitle);
								
								if (thirdChildPage != null) {
									for (Link fourthChild : thirdChildPage.getLinks()) {
										String fourthChildTitle = fourthChild.getTitle();
										graph.addVertex(fourthChildTitle);
										graph.addEdge(thirdChildTitle, fourthChildTitle);
										
										if(fourthChildTitle.equals(goal)) {
											countdown = countdown + 1;
											System.out.println("FOUND " + countdown);
										}
										
										
										// FOURTH CHILD
											
										Page fourthChildPage = generatePage(fourthChildTitle);
										
										if (fourthChildPage != null) {
											for (Link fifthChild : fourthChildPage.getLinks()) {
												String fifthChildTitle = fifthChild.getTitle();
												graph.addVertex(fifthChildTitle);
												graph.addEdge(fourthChildTitle, fifthChildTitle);
												
												if(fifthChildTitle.equals(goal)) {
													countdown = countdown + 1;
													System.out.println("FOUND " + countdown);
												}
											}
											
											if(countdown > 1) {
												return;
											}
										}     // END OF FOURTH CHILD
			
										
									}
								}  // END OF THIRD CHILD
								
								
							}
						} // END OF SECOND CHILD
													
						
					}
				}  // END OF FIRST CHILD
				
				
			}
		}  // END OF PARENT

	} // END OF METHOD

} //END OF CLASS
