import lenz.htw.coshnost.world.GraphNode;

public class BotController {

    private int botNumber;
    private float[] position;
    private GraphNode currentNode;
    private GraphNode targetNode;
    private boolean arrived = true;

    private int x=0,y=0,z=0;

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

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }
}
