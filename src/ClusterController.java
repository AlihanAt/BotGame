import lenz.htw.coshnost.world.GraphNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ClusterController {

    private final float clusterRadius = 0.30f;
    public static float clusterDistance = 0.50f;
    private final float clusterCount = 40;
    private final Cluster[] clusters = new Cluster[(int) clusterCount];

    public void startClustering(){
        GraphNode[] graph = Mapper.graph;
        pickRandomNodesAsCluster(graph);
        fillClusterWithNodes(graph);
    }

    private void pickRandomNodesAsCluster(GraphNode[] graph) {
        Random r = new Random();

        for(int i = 0; i< clusterCount; i++) {
            int randomNumber = r.nextInt(graph.length);
            if(graph[randomNumber].isBlocked())
                if(randomNumber == clusterCount)
                    randomNumber -= 1;
                else
                    randomNumber += 1;

            clusters[i] = new Cluster(graph[randomNumber]);
        }
    }

    private void fillClusterWithNodes(GraphNode[] graph) {
        float dist;
        List<GraphNode> tempNodeList;
        List<Integer> tempIndexList;
        for (int j = 0; j< clusterCount; j++) {
            tempNodeList = new ArrayList<>();
            tempIndexList = new ArrayList<>();

            for(int i = 0; i< graph.length; i++){
                dist = Mapper.calcDistance(clusters[j].getCenter().getPosition(), graph[i].getPosition());
                if(dist < clusterRadius){
                    tempNodeList.add(graph[i]);
                    tempIndexList.add(i);
                }
            }
            GraphNode[] nodes = tempNodeList.toArray(new GraphNode[0]);
            int[] indexes = tempIndexList.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();
            clusters[j].setNodesMap(indexes, nodes);
        }
    }

    public void updateClusters(int myNumber){
        if(clusters[0] == null)
            return;

        updateNodeColors();

        int myNodes = 0;
        int enemyNodes = 0;
        int neutralNodes = 0;
        int tempOwner;

        for(int i = 0; i< clusterCount; i++){

            HashMap<Integer, GraphNode> map = clusters[i].getNodesMap();
            for (HashMap.Entry<Integer, GraphNode> entry : map.entrySet()) {
                //gucken, wem die node gehört
                tempOwner = entry.getValue().getOwner();
                if(tempOwner == myNumber){
                    myNodes += 1;
                }
                else if(tempOwner == -1){
                    neutralNodes += 1;
                }
                else
                    enemyNodes += 1;
            }
//          der Spieler mit den meisten nodes im cluster bekommt die cluster zugeschrieben
            neutralNodes /=4;
            setClusterOwnership(myNodes, enemyNodes, neutralNodes, i);
        }

    }

    private void updateNodeColors() {

        for(int i=0; i<clusterCount; i++){
            HashMap<Integer, GraphNode> map = clusters[i].getNodesMap();

            for (HashMap.Entry<Integer, GraphNode> entry : map.entrySet()) {
                GraphNode tempNode = Mapper.graph[entry.getKey()];
                entry.getValue().ownership = tempNode.ownership;
            }
        }
    }

    private void setClusterOwnership(int myNodes, int enemyNodes, int neutralNodes, int i) {
        if(myNodes > enemyNodes && myNodes > neutralNodes)
            clusters[i].setOwnership(0);
        else if(enemyNodes > myNodes && enemyNodes > neutralNodes)
            clusters[i].setOwnership(1);
        else
            clusters[i].setOwnership(-1);
    }

    public Cluster[] getClusters(){
        return clusters;
    }

}
