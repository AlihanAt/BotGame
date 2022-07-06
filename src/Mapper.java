import lenz.htw.coshnost.world.GraphNode;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static GraphNode[] graph;
    private static final float vertexRange = 0.035f;

    private static float calcDistance(float[] pos1, float[] pos2){
        return (float) Math.sqrt(Math.pow(pos1[0]-pos2[0], 2) + Math.pow(pos1[1] - pos2[1], 2) + Math.pow(pos1[2] - pos2[2],2));
    }

    public static void calcStartNode(BotController bot){
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
        System.out.println("Start-Node Set");
//        GraphNode[] nachbarn = graph[index].getNeighbors();
//        for ( GraphNode node : nachbarn){
//            tempDistance = calcDistance(position, node.getPosition());
//            System.out.println("Nachbar Distance: " + tempDistance);
//        }
    }

    public static void getNewTargetNode(BotController bot, int myNumber) {
        if(!bot.isArrived())
            return;

        GraphNode newTarget;

        for (int i=0; i<graph.length; i++) {
            if(graph[i].getOwner() != myNumber && graph[i].getOwner() != -1){

                newTarget = graph[i];
                if(newTarget.equals(bot.getTargetNode())) {
//                    System.out.println("Same Target Node");
                    continue;
                }
                bot.setTargetNode(newTarget);
                bot.setArrived(false);
                System.out.println("New Target-Node Set");
                return;
            }
        }
    }

    public static void checkIfArrivedAtTarget(BotController bot){
        if(bot.getTargetNode() == null || bot.getPosition() == null)
            return;

        float dist = calcDistance(bot.getPosition(), bot.getTargetNode().getPosition());
        if(dist < vertexRange){
            System.out.println("Ziel erreicht");
            bot.setArrived(true);
        }
        System.out.println("Distance: " + dist);
    }

    public static void createCluster(){
        GraphNode node = getNorthpole();
        float clusterDistance = Cluster.clusterDistance;
        List<GraphNode> resultNodes = new ArrayList<>();
        resultNodes.add(node);
        List<GraphNode> tempNodes = new ArrayList<>(resultNodes);
        float fullDistance = 0;

        while(fullDistance < 2){

            for (GraphNode n : resultNodes) {
                System.out.println("test: " + fullDistance);
                getPossibleClusterNodes(n, clusterDistance, tempNodes);
                tempNodes = filterClusterNodes(clusterDistance, tempNodes);
            }
            fullDistance += clusterDistance;
            resultNodes = new ArrayList<>(tempNodes);
        }

        System.out.println("result size: " + resultNodes.size());

    }

    private static List<GraphNode> filterClusterNodes(float clusterDistance, List<GraphNode> resultNodes) {
        List<GraphNode> tempList = new ArrayList<>(resultNodes);
        int index = 0;
        float tempDist;

        for (GraphNode n1 : resultNodes) {
            for (GraphNode n2 : resultNodes) {
                if(n1.equals(n2))
                    break;
                tempDist = calcDistance(n1.getPosition(), n2.getPosition());
                if(tempDist < clusterDistance){
                    System.out.println(resultNodes.size() + ", " + index);
                    tempList.remove(index);
                    index += 1;
                }
                else{
                    break;
                }
            }
            index = 0;
        }

        return tempList;
//        for (GraphNode n : tempList) {
//            System.out.println(calcDistance(node.getPosition(), n.getPosition()));
//        }
    }

    private static void getPossibleClusterNodes(GraphNode node, float clusterDistance, List<GraphNode> resultNodes) {
        float tempDist;
        for (GraphNode n : graph) {
            tempDist = calcDistance(node.getPosition(), n.getPosition());
            if (tempDist > clusterDistance && tempDist < clusterDistance + 0.05
                    && node.getY() > n.getY()) {
                resultNodes.add(n);
            }
        }

//        for (GraphNode n : resultNodes) {
//            System.out.println(calcDistance(node.getPosition(), n.getPosition()));
//        }
    }

    public static GraphNode getNorthpole(){
        float[] np = new float[]{0,1,0};
        GraphNode tempNode = null;
        int index = 0;
        float dist = 1;
        float tempDist = 1;

        for(int i = 0; i<graph.length; i++){
            tempDist = calcDistance(graph[i].getPosition(), np);
            if(dist > tempDist) {
                dist = tempDist;
                tempNode = graph[i];
                index = i;
            }
        }
        System.out.println(calcDistance(np, tempNode.getPosition()) + ", index: " + index);
        return tempNode;
    }

}
