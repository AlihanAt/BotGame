import lenz.htw.coshnost.world.GraphNode;

import java.util.Hashtable;
import java.util.List;

public class Mapper {

    public static GraphNode[] graph;
    public static Hashtable<GraphNode, Integer> nodesMap = new Hashtable<>();;

    private static final float vertexRange = 0.035f;

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
    }

    public static void setNewTargetNode(BotController bot, int myNumber) {
        if(!bot.isArrivedAtTarget())
            return;

        GraphNode newTarget;

        for (int i=0; i<graph.length; i++) {
            if(graph[i].getOwner() != myNumber && graph[i].getOwner() != -1){

                newTarget = graph[i];
                if(newTarget.equals(bot.getTargetNode())) {
                    continue;
                }

//                float x = calcDistance(bot.getPosition(), graph[i].getPosition());
//                System.out.println("test " + x);
//                if(x>0.15f)
//                    continue;

                bot.setTargetNode(newTarget);
                bot.setArrivedAtTarget(false);

                List<AStarNode> route = Pathfinder.aStar(bot.getCurrentNode(), bot.getTargetNode());
                bot.setRoute(route);

                System.out.println("New Target-Node Set");
                return;
            }
        }
    }

    public static void checkIfArrivedAtTarget(BotController bot){
        if(bot.getTargetNode() == null || bot.getPosition() == null)
            return;

//        float[] pos = bot.getPosition();
//        System.out.println(pos[0] + ", " + pos[1] + ", " + pos[2]);

        float dist = calcDistance(bot.getPosition(), bot.getTargetNode().getPosition());
        if(dist <= vertexRange){
            System.out.println("Ziel erreicht");
            bot.setArrivedAtTarget(true);
        }
//        System.out.println("Distance: " + dist);
    }


    public static void checkIfArrivedAtRouteTarget(BotController bot) {
        if(bot.getRoute() == null || bot.getPosition() == null)
            return;

        float dist = calcDistance(bot.getPosition(), bot.getNextRouteNode().getPosition());
        if(dist <= vertexRange){
//            System.out.println("Routen Ziel erreicht");
            bot.setArrivedAtRouteTarget(true);
            bot.setPosition(bot.getNextRouteNode().getPosition());
        }
//        System.out.println("Distance: " + dist);
    }
}
