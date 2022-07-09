import lenz.htw.coshnost.world.GraphNode;

import java.util.Hashtable;
import java.util.List;

public class Mapper {

    public static int myNumber;
    public static GraphNode[] graph;
    public static Hashtable<GraphNode, Integer> nodesMap = new Hashtable<>();;

    private static final float vertexRange = 0.05f;

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

    public static void setClosestNode(BotController bot){
        int index = 0;
        float distance = 1;
        float tempDistance = 1;

        for(int i = 0; i<graph.length; i++){
            tempDistance = calcDistance(bot.getPosition(), graph[i].getPosition());
            if(tempDistance < distance) {
                distance = tempDistance;
                index = i;
            }
        }

        bot.setCurrentNode(graph[index]);
    }

    public static void setNewTargetNode(BotController bot, int myNumber, ClusterController clusterController) {
        if(!bot.isArrivedAtTarget())
            return;

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

        bot.setTargetNode(targetCluster.getCenter());
        bot.setArrivedAtTarget(false);

        List<AStarNode> route = Pathfinder.aStar(bot.getCurrentNode(), bot.getTargetNode(), bot.getBotNumber());
        bot.setRoute(route);
    }

    public static void checkIfArrivedAtTarget(BotController bot){
        if(bot.getTargetNode() == null || bot.getPosition() == null)
            return;

        float dist = calcDistance(bot.getPosition(), bot.getTargetNode().getPosition());
        if(dist <= vertexRange){
            bot.setArrivedAtTarget(true);
            setClosestNode(bot);
        }
    }


    public static void checkIfArrivedAtRouteTarget(BotController bot) {
        if(bot.getRoute() == null || bot.getPosition() == null)
            return;

        float dist = calcDistance(bot.getPosition(), bot.getNextRouteNode().getPosition());
        if(dist <= vertexRange){
            bot.setArrivedAtRouteTarget(true);
            bot.setPosition(bot.getNextRouteNode().getPosition());
            setClosestNode(bot);
        }
    }

    public static float getOwnColorHeuristicValueFrom(AStarNode a) {

        if(a.node.getOwner() == myNumber || a.node.getOwner() == -1){
            return 10000;
        }
        return 0;
    }

    public static float getTrenchHeuristicValueFrom(AStarNode a) {

        if(a.node.isBlocked()){
            return 10000000;
        }
        return 0;
    }

}
