import lenz.htw.coshnost.world.GraphNode;

public class BotController {

    private int botNumber;
    private GraphNode currentNode;
    private float[] position;

    public BotController(int botNumber){
        this.botNumber = botNumber;
    }

    public void move(){

    }


    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public boolean isBlocked(){
        return true;
    }

}
