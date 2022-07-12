package Game;

import Cluster.Cluster;
import Cluster.ClusterController;
import lenz.htw.coshnost.world.GraphNode;

import java.util.Hashtable;

public class Mapper {

    public static int myNumber;
    public static GraphNode[] graph;
    public static Hashtable<GraphNode, Integer> nodesMap = new Hashtable<>();;

    private static final float vertexRange = 0.055f;

    public static void fillNodesMap(){
        if(graph==null)
            return;

        for (int i=0;i<graph.length;i++){
            nodesMap.put(graph[i], i);
        }
    }

    public static int getNodeIndex(GraphNode node){
        return nodesMap.get(node);
    }

    public static float calcDistance(float[] pos1, float[] pos2){
        return (float) Math.sqrt(Math.pow(pos1[0]-pos2[0], 2) + Math.pow(pos1[1] - pos2[1], 2) + Math.pow(pos1[2] - pos2[2],2));
    }

    public static GraphNode calcClosestNode(float[] pos){
        int index = 0;
        float distance = 1;
        float tempDistance = 1;

        for(int i = 0; i<graph.length; i++){
            tempDistance = calcDistance(pos, graph[i].getPosition());
            if(tempDistance < distance) {
                distance = tempDistance;
                index = i;
            }
        }

        return graph[index];
    }

    public static GraphNode calcNewTargetNode(BotController bot, ClusterController clusterController) {

        Cluster[] clusters = clusterController.getClusters();
        Cluster targetCluster = clusters[0];
        float dist = calcDistance(bot.getPosition(), targetCluster.getCenter().getPosition());

        for(int i=1; i<clusters.length; i++){
            float tempDist = calcDistance(bot.getPosition(), clusters[i].getCenter().getPosition());

            if(clusters[i].getCenter().equals(bot.getTargetNode()))
                continue;

            if(tempDist < dist && (clusters[i].getOwnership() == 1 && bot.getBotNumber() == 2)
                    || (clusters[i].getOwnership() != 0 && bot.getBotNumber() < 2)){

                dist = tempDist;
                targetCluster = clusters[i];
            }
        }

        return targetCluster.getCenter();
    }

    public static boolean calcIfArrivedAtTarget(float[] pos, float[] targetPos){
        float dist = calcDistance(pos, targetPos);
        return dist <= vertexRange;
    }

}
