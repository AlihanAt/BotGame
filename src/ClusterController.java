import lenz.htw.coshnost.world.GraphNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClusterController {

    private final float clusterRadius = 0.30f;
    public static float clusterDistance = 0.50f;
    private final float clusterCount = 30;
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
            clusters[i] = new Cluster(graph[randomNumber]);
        }
    }

    private void fillClusterWithNodes(GraphNode[] graph) {
        float dist;
        List<GraphNode> tempList;
        for (int j = 0; j< clusterCount; j++) {
            tempList = new ArrayList<>();

            for(int i = 0; i< graph.length; i++){
                dist = Mapper.calcDistance(clusters[j].getCenter().getPosition(), graph[i].getPosition());
                if(dist < clusterRadius){
                    tempList.add(graph[i]);
                }
            }
            GraphNode[] array = tempList.toArray(new GraphNode[0]);
            clusters[j].setNodes(array);
        }
    }

    //geht besser...
    public void updateClusters(int myNumber){
        if(clusters[0] == null)
            return;

        int myNodes = 0;
        int enemyNodes = 0;
        int neutralNodes = 0;
        int tempOwner;

        for(int i = 0; i< clusterCount; i++){
            for(int j=0; j<clusters[i].getNodes().length; j++){

                //gucken, wem die node gehört
                tempOwner = clusters[i].getNodes()[j].getOwner();
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
            setClusterOwnership(myNodes, enemyNodes, neutralNodes, i);
        }

//        for (Cluster c :
//                clusters) {
//            System.out.println(c.getOwnership());
//        }
//        System.out.println("--------------");
    }

    private void setClusterOwnership(int myNodes, int enemyNodes, int neutralNodes, int i) {
        if(myNodes > enemyNodes && myNodes > neutralNodes)
            clusters[i].setOwnership(0);
        else if(enemyNodes > myNodes && enemyNodes > neutralNodes)
            clusters[i].setOwnership(1);
        else
            clusters[i].setOwnership(-1);
    }

}
