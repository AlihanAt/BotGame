import lenz.htw.coshnost.world.GraphNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStarNode implements Comparable<AStarNode> {

    GraphNode node;
    int graphIndex;
    List<Edge> neighbors;
    public AStarNode parent = null;

    private static int idCounter = 0;
    public int id;

    float f = Float.MAX_VALUE;
    float g = Float.MAX_VALUE;
    float h;

    AStarNode(GraphNode node, int graphIndex){
        this.id = idCounter++;
        this.neighbors = new ArrayList<>();

        this.node = node;
        this.graphIndex = graphIndex;
    }

    @Override
    public int compareTo(AStarNode n) {
        return Float.compare(this.f, n.f);
    }

    public boolean equals(Object obj){
        if (obj instanceof AStarNode) {
            AStarNode obj1 = (AStarNode)obj;
            return this.node.x == obj1.node.x && this.node.y == obj1.node.y && this.node.z == obj1.node.z;
        } else {
            return false;
        }
    }

    public float calculateHeuristic(AStarNode target){
        float h = Mapper.calcDistance(node.getPosition(), target.node.getPosition());
        return h;
    }

    public void setHeuristic(float h){
        this.h = h;
    }

    public void setNeighbors(GraphNode[] neighbors, AStarNode target){

        for (GraphNode n : neighbors) {
            if(n.isBlocked())
                continue;

            AStarNode starNode = new AStarNode(n, Mapper.getNodeIndex(n));
            starNode.setHeuristic(starNode.calculateHeuristic(target));

            this.addBranch(Mapper.calcDistance(node.getPosition(), starNode.node.getPosition()), starNode);

        }
    }

    private void addBranch(float weight, AStarNode node){
        Edge newEdge = new Edge(weight, node);
        neighbors.add(newEdge);
    }

    public static class Edge {
        Edge(float weight, AStarNode node){
            this.weight = weight;
            this.node = node;
        }

        public float weight;
        public AStarNode node;
    }

}
