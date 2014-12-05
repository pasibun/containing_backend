/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nhl.containing_backend;

import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.model.problem.SearchProblem;
import es.usc.citius.hipster.util.graph.GraphBuilder;
import es.usc.citius.hipster.util.graph.GraphSearchProblem;
import es.usc.citius.hipster.util.graph.HipsterDirectedGraph;
import java.util.List;

/**
 *
 * @author Thom
 */
public class Dijkstra {

    /**
     * This method uses Dijkstra to find the shortest path between 2 waypoints
     * @param beginPoint the waypoint the AGV currently is
     * @param endPoint the destination of the waypoint
     * @return list with the shortest path
     */
    public List shortestPath(char beginPoint, char endPoint) {
    //Create a simple weighted directed graph with Hipster where
    // vertices are Strings and edge values are just doubles
        
        HipsterDirectedGraph<String, Double> graph = GraphBuilder.create()
                .connect("A").to("C").withEdge(250d)
                .connect("A").to("B").withEdge(275d)
                .connect("B").to("D").withEdge(250d)
                .connect("C").to("D").withEdge(275d)
                .connect("C").to("E").withEdge(260d)
                .connect("D").to("F").withEdge(260d)
                .connect("E").to("F").withEdge(275d)
                .connect("E").to("G").withEdge(280d)
                .connect("F").to("H").withEdge(280d)
                .connect("C").to("D").withEdge(275d)
                .buildDirectedGraph();
        
        // Create the search problem. For graph problems, just use
        // the GraphSearchProblem util class to generate the problem with ease.
        SearchProblem p = GraphSearchProblem
                .startingFrom(Character.toString(beginPoint))
                .in(graph)
                .takeCostsFromEdges()
                .build();

        // Search the shortest path from "A" to "F"
        //System.out.println(Hipster.createDijkstra(p).search("E").getOptimalPaths());
        return Hipster.createDijkstra(p).search(endPoint).getOptimalPaths();
    }
}
