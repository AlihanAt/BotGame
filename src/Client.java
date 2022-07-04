import lenz.htw.coshnost.net.NetworkClient;
import lenz.htw.coshnost.world.GraphNode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Client implements Runnable {

    private NetworkClient client;

    private BotController einfarbig;
    private BotController gepunktet;
    private BotController gestreift;

    private int myNumber;
    private GraphNode[] graph;



    private int updateId;

    float xMin = 0, xMax = 0;
    float yMin = 0, yMax = 0;
    float zMin = 0, zMax = 0;

    BufferedWriter writer;

    @Override
    public void run() {
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() throws IOException {
        writer =  new BufferedWriter(new FileWriter("test.txt"));

        client = new NetworkClient("localhost", "Teamname", "Für Fortnite");
        graph = client.getGraph();
        myNumber = client.getMyPlayerNumber();

        einfarbig = new BotController(0);
        gepunktet = new BotController(1);
        gestreift = new BotController(2);


        if(myNumber==0) {
            gestreift.setPosition(client.getBotPosition(myNumber, 2));
            gestreift.calcStartNode(graph);

            while (true) {
                graph = client.getGraph();
                gestreift.getNewTargetNode(graph, myNumber);
                changeMoveDirection(gestreift);
                arrivedAtTarget(gestreift.getPosition(), gestreift.getTargetNode());

            }
        }
        else {
            while(true) {
                float[] dir0 = client.getBotDirection(0);
                float[] dir1 = client.getBotDirection(1);
                float[] dir2 = client.getBotDirection(2);
                client.changeMoveDirection(0, -dir0[0], -dir0[1], -dir0[2]);
                client.changeMoveDirection(1, -dir1[0], -dir1[1], -dir1[2]);
                client.changeMoveDirection(2, -dir2[0], -dir2[1], -dir2[2]);
            }
        }

    }

    private void changeMoveDirection(BotController bot) {
        int[] dir = bot.updateMoveDirection();
        client.changeMoveDirection(bot.getBotNumber(), (float)dir[0], (float) dir[1], (float) dir[2]);
    }

    private void arrivedAtTarget(float[] pos, GraphNode node){
        if(node == null)
            return;

        calcRange(node);
//        System.out.println(pos[0] + ", " + pos[1] + ", " + pos[2]);
        if((pos[0]>xMin && pos[0]<xMax) && (pos[1]>yMin && pos[1]<yMax) && (pos[2]>zMin && pos[2]<zMax)){
            System.out.println("Ziel erreicht");
            gestreift.getNewTargetNode(graph, myNumber);
        }

    }

    //berechnung der fläche eines Knotens inkl. Nachbarn
    private void calcRange(GraphNode node) {
        GraphNode[] nachbarn = node.getNeighbors();

        xMin = nachbarn[0].getX();
        xMax = nachbarn[0].getX();
        yMin = nachbarn[0].getY();
        yMax = nachbarn[0].getY();
        zMin = nachbarn[0].getZ();
        zMax = nachbarn[0].getZ();

        for (GraphNode n : nachbarn) {
            if(n.getX() < xMin)
                xMin = n.getX();
            else if(n.getX() > xMax)
                xMax = n.getX();
            if(n.getY() < yMin)
                yMin = n.getY();
            else if(n.getY() > yMax)
                yMax = n.getY();
            if(n.getZ() < zMin)
                zMin = n.getZ();
            else if(n.getZ() > zMax)
                zMax = n.getZ();
        }
//        System.out.println("X: " + xMin + " - " + xMax +
//                "\nY: " + yMin + " - " + yMax +
//                "\nZ: " + zMin + " - " + zMax);
    }

}