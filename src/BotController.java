import lenz.htw.coshnost.world.GraphNode;

public class BotController {

    private int botNumber;
    private float[] position;
    private GraphNode currentNode;
    private GraphNode targetNode;

    int x=0,y=0,z=0;

    public BotController(int botNumber){
        this.botNumber = botNumber;
    }

    public int[] updateMoveDirection(){

        if(position == null || targetNode == null)
            return new int[]{0,0,0};

        if (position[0] < targetNode.x) {
            x=1;
        } else if (position[0] > targetNode.x) {
            x = -1;
        }

        if(position[1] < targetNode.y){
            y=1;
        } else if (position[1] > targetNode.y){
            y=-1;
        }

        if(position[2] < targetNode.z){
            z=1;
        } else if (position[2] > targetNode.z){
            z=-1;
        }
//        System.out.println("Direction Set");
        return new int[]{x,y,z};
    }

    public void calcStartNode(GraphNode[] graph){
        int index = 0;
        float distance = 1;
        float tempDistance = 1;

        for(int i = 0; i<graph.length; i++){
            tempDistance = calcDistance(position, graph[i].getPosition());
            if(tempDistance < distance) {
                distance = tempDistance;
                index = i;
            }
        }

        currentNode = graph[index];
        System.out.println("Start-Node Set");
//        GraphNode[] nachbarn = graph[index].getNeighbors();
//        for ( GraphNode node : nachbarn){
//            tempDistance = calcDistance(position, node.getPosition());
//            System.out.println("Nachbar Distance: " + tempDistance);
//        }
    }

    private float calcDistance(float[] pos1, float[] pos2){
        return (float) Math.sqrt(Math.pow(pos1[0]-pos2[0], 2) + Math.pow(pos1[1] - pos2[1], 2) + Math.pow(pos1[2] - pos2[2],2));
    }

    public void getNewTargetNode(GraphNode[] graph, int myNumber) {
        GraphNode target;

        for (int i=0; i<graph.length; i++) {
            if(graph[i].getOwner() != myNumber && graph[i].getOwner() != -1){

                target = graph[i];
                if(target.equals(targetNode)) {
                    System.out.println("Same Target Node");
                    return;
                }
                setTargetNode(target);
                System.out.println("Target-Node Set");
                return;
            }
        }
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        System.out.println("Position Set");
        this.position = position;
    }

    public GraphNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(GraphNode node){
        this.currentNode = node;
    }

    public GraphNode getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(GraphNode targetNode) {
        this.targetNode = targetNode;
    }

    public int getBotNumber() {
        return botNumber;
    }
}
