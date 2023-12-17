package Dijkstra;

import Graph.Graph;
import HashMap.HashMap;
import LinkedList.LinkedList;
import PriorityQueue.PriorityQueue;

public class Dijkstra {
    public static String Dijkstra(Graph<Integer, Integer> g, int startNode, int endNode)
    {
        // Create a hashmap of each node and the current known distances to them from the start node
        HashMap<Integer, Integer> distances = new HashMap<Integer, Integer>(g.getNumberOfNodes());
        HashMap<Integer, String> paths = new HashMap<Integer, String>(g.getNumberOfNodes()); //TODO EXPLAIN
        LinkedList<Integer> nodes  = g.getNodeArray();
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
        LinkedList<Integer> vistedNodes = new LinkedList<Integer>();

        //Initialise every node to have a distance of a very large number (effectively infinity) from the start node
        for(int i = 0; i<g.getNumberOfNodes(); i++)
        {
            if(nodes.getValue(i) == startNode)
            {
                // The distance from the start node to the start node is known to be 0
                distances.add(startNode, 0);
                // Path to get from start node to start node requires going through the startnode //TODO EXPLAIN
                paths.add(startNode, startNode+""); //TODO EXPLAIN


                // Add the startnode to the front of the priority queue as its distance is 0
                queue.enQueue(nodes.get(i).getValue(), 0);
            }
            else{
                distances.add(nodes.getValue(i), Integer.MAX_VALUE);
                paths.add(nodes.getValue(i), "");
                //Add all nodes on the priority queue with the priority being their distances from A
                queue.enQueue(nodes.get(i).getValue(), Integer.MAX_VALUE);
            }

        }

        while(!queue.isEmpty())
        {
            // Removes the node at the front from the queue, so it is visited
            int currentNode = queue.deQueue().getValue();
            LinkedList<Integer> neighbours = g.getNeigbours(currentNode);
            for(int i = 0; i<neighbours.length(); i++)
            {
                if(!vistedNodes.contains(neighbours.get(i).getValue()))
                {
                    int newDistance = distances.getValue(currentNode) + g.getWeight(currentNode, neighbours.get(i).getValue());
                    String newPath = paths.getValue(currentNode)+","+neighbours.get(i);

                    if(newDistance<(Integer) distances.getItem(neighbours.get(i).getValue()).getValue())
                    {
//                      distances.getItem(neighbours.get(i).getValue()).setValue(distances.getValue(currentNode) + g.getWeight(currentNode, neighbours.get(i).getValue()));
                        distances.changeValue(neighbours.get(i).getValue(), newDistance);
                        paths.changeValue(neighbours.get(i).getValue(), newPath);
                        queue.setPriority(neighbours.get(i).getValue(), newDistance);
                    }
                }
            }
            //neighbours.get(i).getValue()


        }

        return paths.getValue(endNode);
    }
}