package com.sujit.graph;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GraphTest {

    private static Graph graph = new Graph();

    @BeforeAll
    public static void setGraph(){
        graph = new Graph();

        graph.addNode("Bob");
        graph.addNode("Alice");
        graph.addNode("Mark");
        graph.addNode("Rob");
        graph.addNode("Maria");

        graph.addEdge("Bob","Alice");
        graph.addEdge("Bob","Rob");
        graph.addEdge("Alice","Mark");
        graph.addEdge("Rob","Mark");
        graph.addEdge("Alice","Maria");
        graph.addEdge("Rob","Maria");
    }

    @Test
    public void testBFT(){
        Set<GraphNode> expectedGuys = new HashSet<>();
        expectedGuys.add(new GraphNode("Bob"));
        expectedGuys.add(new GraphNode("Rob"));
        expectedGuys.add(new GraphNode("Alice"));
        expectedGuys.add(new GraphNode("Mark"));
        expectedGuys.add(new GraphNode("Maria"));

        Set<GraphNode> actualGuys = graph.traverseBFT("Bob");
        assertEquals(expectedGuys, actualGuys);
    }

    @Test
    public void testDFT(){
        Set<GraphNode> expectedGuys = new HashSet<>();
        expectedGuys.add(new GraphNode("Bob"));
        expectedGuys.add(new GraphNode("Rob"));
        expectedGuys.add(new GraphNode("Alice"));
        expectedGuys.add(new GraphNode("Mark"));
        expectedGuys.add(new GraphNode("Maria"));

        Set<GraphNode> actualGuys = graph.traverseDFT("Bob");
        assertEquals(expectedGuys, actualGuys);
    }
}
