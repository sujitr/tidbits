package com.sujit.graph;

/**
 * Basic graph node data structure representing a Person.
 * This node could be used to construct a social network.
 *
 * For now, only name as label has been used. Other
 * attributes could be added as needed.
 */
public class GraphNode {

    private String label;       // name of the person entity

    public GraphNode(String name){
        this.label = name;
    }

    /*
    We would need to override hashCode and equals methods
    to enable comparison between the nodes while traversing
    the graph.
     */
    @Override
    public int hashCode(){
        return this.label.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(obj==null || !(obj instanceof GraphNode)) return false;
        if(obj==this) return true;
        GraphNode node = (GraphNode) obj;
        return this.label.equals(node.label);
    }

    @Override
    public String toString(){
        return this.label;
    }
}
