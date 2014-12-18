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
    public String shortestPath(String beginPoint, String endPoint) {
    //Create a simple weighted directed graph with Hipster where
    // vertices are Strings and edge values are just doubles
        
        HipsterDirectedGraph<String, Double> graph = GraphBuilder.create()
                .connect("A").to("L").withEdge(125d)
                .connect("L").to("C").withEdge(125d)
                .connect("A").to("B").withEdge(275d)
                .connect("B").to("M").withEdge(125d)
                .connect("M").to("D").withEdge(125d)
                .connect("D").to("M").withEdge(125d)
                .connect("C").to("D").withEdge(275d)
                .connect("C").to("O").withEdge(130d)
                .connect("O").to("E").withEdge(125d)
                .connect("D").to("N").withEdge(130d)
                .connect("N").to("D").withEdge(130d)
                .connect("N").to("F").withEdge(130d)
                .connect("F").to("N").withEdge(130d)
                .connect("E").to("F").withEdge(275d)
                .connect("E").to("P").withEdge(140d)
                .connect("P").to("G").withEdge(140d)
                .connect("F").to("Q").withEdge(140d)
                .connect("Q").to("H").withEdge(140d)
                .connect("Q").to("F").withEdge(140d)
                .connect("H").to("Q").withEdge(140d)
                .connect("H").to("G").withEdge(140d)
                .connect("G").to("H").withEdge(275d)
                .connect("G").to("J").withEdge(50d)
                .connect("H").to("K").withEdge(50d)
                .connect("E").to("I").withEdge(50d)
                .buildDirectedGraph();
        
        // Create the search problem. For graph problems, just use
        // the GraphSearchProblem util class to generate the problem with ease.
        SearchProblem p = GraphSearchProblem
                .startingFrom((beginPoint))
                .in(graph)
                .takeCostsFromEdges()
                .build();

        // Search the shortest path from "A" to "F"
        //System.out.println(Hipster.createDijkstra(p).search("E").getOptimalPaths());
        return Hipster.createDijkstra(p).search(endPoint).getOptimalPaths().toString();
    }
}
