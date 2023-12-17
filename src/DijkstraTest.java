import Dijkstra.Dijkstra;
import Graph.Graph;

public class DijkstraTest{
    public static void main(String[] args) {
        Graph<Integer, Integer> g = new Graph<Integer, Integer>(19, false);
                //should maybe check if undirected should the size be 2x

        g.add(2,1,50);
        g.add(1,3,71);
        g.add(3,4,42);
        g.add(3,5,50);
        g.add(4,5,70);
        g.add(5,6,84);
        g.add(5,8,118);
        g.add(5,7,108);
        g.add(17, 6, 100);
        g.add(6,9,120);
        g.add(7,15,114);
        g.add(9, 10, 48);
        g.add(9,11,63);
        g.add(11,12,99);
        g.add(12,16,158);
        g.add(9,13,132);
        g.add(15,14,37);
        g.add(14,12,129);
        g.add(14,13,129);

        String path = Dijkstra.Dijkstra(g, 2,16);
        System.out.println(path);
    }
}