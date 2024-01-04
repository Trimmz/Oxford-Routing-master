/*
 * Graph class for representing a graph with nodes and weighted edges
 */

package Graph;

import HashMap.HashMap;
import LinkedList.LinkedList;

public class Graph<N, W>{
    private boolean isDirected;
    private HashMap<N, HashMap<N, W>> adjList;
    private LinkedList<N> nodes = new LinkedList<N>();

    private int edgeNumber = 0;
    private int maxSize;

    // Constructor to initialize the graph with a specified size and directed/undirected status
    public Graph(int size, boolean isDirected)
    {
        this.isDirected = isDirected;
        this.maxSize = size;
        adjList = new HashMap<N, HashMap<N, W>>(maxSize, true);
    }

    // Method to get neighbours of a given node
    public LinkedList<N> getNeighbours(N node)
    {
        LinkedList<N> neighbours = new LinkedList<N>();
        if (adjList.contains(node)) {
            HashMap<N, W> neighborsMap = adjList.getValue(node);
            for (N neighborNode : neighborsMap.getKeys()) {
                neighbours.append(neighborNode);
            }
        }

        return neighbours;
    }

    // Method to get the weight of the edge between two nodes
    public W getWeight(N node1, N node2) {
        if (adjList.contains(node1) && adjList.getValue(node1).contains(node2)) {
            return adjList.getValue(node1).getValue(node2);
        }

        return null;
    }

    //Private method to add an edge between two nodes on an undirected graph
    private void addEdge(N node1, N node2, W weight)
    {
        if(!nodes.contains((node1)))
        {
            nodes.append(node1);
        }
        if(!nodes.contains((node2)))
        {
            nodes.append(node2);
        }

        if(adjList.contains(node1))
        {
            adjList.getValue(node1).add(node2, weight);
        }
        else{
            adjList.add(node1, new HashMap<N, W>(maxSize-1));
            adjList.getValue((node1)).add(node2, weight);
        }
    }

    // Public method to add an edge between two nodes on a directed graph and update edge count
    public void add(N node1, N node2, W weight)
    {
        addEdge(node1, node2, weight);
        edgeNumber++;
        if(!isDirected)
        {
            addEdge(node2, node1, weight);
        }
    }

    // Method to represent the graph as a string
    public String toString()
    {
        StringBuilder out = new StringBuilder("{");
        out.append("\n\t");
        out.append(adjList.toString());
        out.append("\n}");
        return out.toString();
    }

    public int getNumberOfEdges()
    {
        return edgeNumber;
    }

    public int getNumberOfNodes()
    {
        return nodes.length();
    }

    public int getMaxSize()
    {
        return maxSize;
    }

    public HashMap<N, W> getValueAtNode(N node)
    {
        return adjList.getValue(node);
    }

    public LinkedList<N> getNodeArray()
    {
        return nodes;
    }
}
