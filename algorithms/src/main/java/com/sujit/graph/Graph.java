package com.sujit.graph;

import java.util.*;

/**
 * Class to build the graph and perform
 * various operations on the graph to mutate and search.
 */
public class Graph {
    private Map<GraphNode, List<GraphNode>> nodeListMap;    // Adjacency List representation of the graph

    public Graph(){
        nodeListMap = new HashMap<>();
    }

    public Graph(Map<GraphNode, List<GraphNode>> nodeListMap){
        this.nodeListMap = nodeListMap;
    }

    public Map<GraphNode, List<GraphNode>> getNodeListMap() {
        return nodeListMap;
    }

    public void setNodeListMap(Map<GraphNode, List<GraphNode>> nodeListMap) {
        this.nodeListMap = nodeListMap;
    }

    /**
     * Add a new person in the graph
     * @param name
     */
    public void addNode(String name){
        nodeListMap.putIfAbsent(new GraphNode(name), new LinkedList<>());
    }

    /**
     * Remove a person from the graph
     * @param name
     */
    public void removeNode(String name){
        GraphNode targetNode = new GraphNode(name);
        /* remove the node from all instances of linked lists
        where it had been marked as adjacent for other nodes
         */
        nodeListMap.values().stream().forEach(l -> l.remove(targetNode));
        /*
        remove the node entry itself from the map
         */
        nodeListMap.remove(targetNode);
    }

    /**
     * Mark two person as direct friend with each other
     * @param name1
     * @param name2
     */
    public void addEdge(String name1, String name2){
        GraphNode node1 = new GraphNode(name1);
        GraphNode node2 = new GraphNode(name2);
        nodeListMap.get(node1).add(node2);
        nodeListMap.get(node2).add(node1);
    }

    /**
     * Unmarks two person as direct friend with each other
     * @param name1
     * @param name2
     */
    public void removeEdge(String name1, String name2){
        GraphNode node1 = new GraphNode(name1);
        GraphNode node2 = new GraphNode(name2);
        nodeListMap.get(node1).remove(node2);
        nodeListMap.get(node2).remove(node1);
    }

    /**
     * Fetch the direct friends of a given person
     * @param name
     * @return
     */
    public List<GraphNode> getNeighbors(String name){
        GraphNode node = new GraphNode(name);
        return nodeListMap.get(node);
    }

    /**
     * DFT traversal on the graph.
     * @param name
     * @return set containing all nodes in the graph
     */
    public Set<GraphNode> traverseDFT(String name){
        /* taking this person as an arbitrary root node */
        GraphNode root = new GraphNode(name);
        /* to keep track of all visited nodes */
        Set<GraphNode> visitedNodes = new HashSet<>();
        /* to keep track of nodes which is yet to be visited */
        Stack<GraphNode> nodesToVisit = new Stack<>();
        nodesToVisit.push(root);
        while(!nodesToVisit.isEmpty()){
            GraphNode node = nodesToVisit.pop();
            if(!visitedNodes.contains(node)){
                visitedNodes.add(node);
                // get all its neighbors and add them in stack for traversing
                nodeListMap.get(node).forEach(nodesToVisit::push);
            }
        }
        return visitedNodes;
    }
}
