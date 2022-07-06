import lenz.htw.coshnost.world.GraphNode;

public class Cluster {

    private GraphNode center;
    private GraphNode[] nodes;
    //-1: none, 0: mine, 1: enemies
    private int ownership = -1;

    public Cluster(GraphNode center){
        this.center = center;
    }

    public GraphNode getCenter() {
        return center;
    }

    public void setCenter(GraphNode center) {
        this.center = center;
    }

    public GraphNode[] getNodes() {
        return nodes;
    }

    public void setNodes(GraphNode[] nodes) {
        this.nodes = nodes;
    }

    public int getOwnership() {
        return ownership;
    }

    public void setOwnership(int ownership) {
        this.ownership = ownership;
    }

    public boolean isEmpty(){
        if(center == null)
            return true;

        if(nodes == null)
            return true;

        return false;
    }
}
