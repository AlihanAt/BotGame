import lenz.htw.coshnost.world.GraphNode;

import java.util.List;

public class BotController {

    private final int botNumber;
    private float[] position;
    private GraphNode currentNode;
    private GraphNode targetNode;
    private GraphNode nextRouteNode;
    private List<AStarNode> route;
    private boolean arrivedAtTarget = true;
    private boolean arrivedAtRouteTarget = true;

    private float x=0,y=0,z=0;

    public BotController(int botNumber){
        this.botNumber = botNumber;
    }

    public void initializeBot(float[] pos){
        position = pos;
        currentNode = Mapper.calcClosestNode(pos);
    }

    //not working
    public void checkIfBotIsStuck(float[] pos){

        if (posIsSame(pos)){
            System.out.println("Bot is stuck");
            currentNode = Mapper.calcClosestNode(position);
            arrivedAtRouteTarget = true;
            arrivedAtTarget = true;
        }
    }

    private boolean posIsSame(float[] pos) {
        if(position[0] == pos[0] && position[1] == pos[1] && position[2] == pos[2])
            return true;

        return false;
    }

    public void checkIfArrivedAtRouteTarget(){
        if(route == null || position == null)
            return;

        if(Mapper.calcIfArrivedAtTarget(position, nextRouteNode.getPosition())){
            arrivedAtRouteTarget = true;
            currentNode = Mapper.calcClosestNode(position);
        }
    }

    public void checkIfArrivedAtTarget(){
        if(targetNode == null || position == null)
            return;

        if(Mapper.calcIfArrivedAtTarget(position, targetNode.getPosition())){
            arrivedAtTarget = true;
            currentNode = Mapper.calcClosestNode(position);
        }
    }

    public void setNewTarget(ClusterController clusterController){
        setNewTargetNode(clusterController);
        setNewRouteTargetNode();
    }

    private void setNewTargetNode(ClusterController clusterController){
        if(!arrivedAtTarget)
            return;

        targetNode = Mapper.calcNewTargetNode(this, clusterController);
        arrivedAtTarget = false;
        route = Pathfinder.aStar(currentNode, targetNode, botNumber);
    }

    private void setNewRouteTargetNode() {
        if (route == null)
            return;

        if(!arrivedAtRouteTarget)
            return;

        if(route.size() >= 1) {
            nextRouteNode = route.get(0).node;
            arrivedAtRouteTarget = false;
            route.remove(0);
        }
    }

    public float[] updateMoveDirection(){

        if(route == null)
            return new float[]{0,0,0};

        //So ist es buggy?
//        x = nextRouteNode.x - position[0];
//        y = nextRouteNode.y - position[1];
//        z = nextRouteNode.z - position[2];

        if (position[0] <= nextRouteNode.x) {
            x=1;
        } else if (position[0] > nextRouteNode.x) {
            x = -1;
        }

        if(position[1] <= nextRouteNode.y){
            y=1;
        } else if (position[1] > nextRouteNode.y){
            y=-1;
        }

        if(position[2] <= nextRouteNode.z){
            z=1;
        } else if (position[2] > nextRouteNode.z){
            z=-1;
        }

        return new float[]{x,y,z};
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public GraphNode getCurrentNode() {
        return currentNode;
    }

    public GraphNode getTargetNode() {
        return targetNode;
    }

    public int getBotNumber() {
        return botNumber;
    }
}
