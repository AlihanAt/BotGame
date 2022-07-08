import lenz.htw.coshnost.world.GraphNode;

import java.util.List;

public class BotController {

    private int botNumber;
    private float[] position;
    private GraphNode currentNode;
    private GraphNode targetNode;
    private GraphNode nextRouteNode;
    private List<AStarNode> route;
    private boolean arrivedAtTarget = true;
    private boolean arrivedAtRouteTarget = true;

    private int x=0,y=0,z=0;

    public BotController(int botNumber){
        this.botNumber = botNumber;
    }

    public void setNewRouteTarget() {
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

    public int[] updateMoveDirection(){

        if(route == null)
            return new int[]{0,0,0};

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

        return new int[]{x,y,z};
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
//        System.out.println("Position Set");
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

    public boolean isArrivedAtTarget() {
        return arrivedAtTarget;
    }

    public void setArrivedAtTarget(boolean arrivedAtTarget) {
        this.arrivedAtTarget = arrivedAtTarget;
    }

    public boolean isArrivedAtRouteTarget() {
        return arrivedAtRouteTarget;
    }

    public void setArrivedAtRouteTarget(boolean arrived) {
        this.arrivedAtRouteTarget = arrived;
    }

    public GraphNode getNextRouteNode() {
        return nextRouteNode;
    }

    public void setNextRouteNode(GraphNode nextRouteNode) {
        this.nextRouteNode = nextRouteNode;
    }

    public List<AStarNode> getRoute() {
        return route;
    }

    public void setRoute(List<AStarNode> route) {
        this.route = route;
    }
}
