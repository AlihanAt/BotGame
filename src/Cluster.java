import lenz.htw.coshnost.world.GraphNode;

import java.util.HashMap;


public class Cluster {

    private GraphNode center;
    private HashMap<Integer, GraphNode> nodesMap = new HashMap<>();

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

    public int getOwnership() {
        return ownership;
    }

    public void setOwnership(int ownership) {
        this.ownership = ownership;
    }

    public void setNodesMap(int[] indexes, GraphNode[] nodes){
        for(int i=0; i<nodes.length;i++){
            nodesMap.put(indexes[i], nodes[i]);
        }
    }

    public HashMap<Integer, GraphNode> getNodesMap(){
        return nodesMap;
    }

//    public boolean isEmpty(){
//        if(center == null)
//            return true;
//
//        if(nodes == null)
//            return true;
//
//        return false;
//    }
}
