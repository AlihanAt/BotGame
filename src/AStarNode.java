import lenz.htw.coshnost.world.GraphNode;

import java.util.ArrayList;
import java.util.List;

public class AStarNode implements Comparable<AStarNode> {

    private static int idCounter = 0;
    public int id;

    public AStarNode parent = null;

    GraphNode node;
    float f = Float.MAX_VALUE;
    float g = Float.MAX_VALUE;
    float h;
    List<Edge> neighbors;

    AStarNode(float h){
        this.h = h;
        this.id = idCounter++;
        this.neighbors = new ArrayList<>();
    }

    @Override
    public int compareTo(AStarNode n) {
        return Float.compare(this.f, n.f);
    }

    public void addBranch(int weight, AStarNode node){
        Edge newEdge = new Edge(weight, node);
        neighbors.add(newEdge);
    }

    public float calculateHeuristic(AStarNode target){
        return this.h;
    }

    public static class Edge {
        Edge(int weight, AStarNode node){
            this.weight = weight;
            this.node = node;
        }

        public int weight;
        public AStarNode node;
    }

}
