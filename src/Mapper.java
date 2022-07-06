import lenz.htw.coshnost.world.GraphNode;

public class Mapper {

    public static GraphNode[] graph;
    private static final float vertexRange = 0.035f;

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
//        System.out.println("Distance: " + dist);
    }

}
