import lenz.htw.coshnost.net.NetworkClient;
import lenz.htw.coshnost.world.GraphNode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Client implements Runnable {

    private NetworkClient client;

    private BotController einfarbig;
    private BotController gepunktet;
    private BotController gestreift;
    private ClusterController clusterController = new ClusterController();

    public int myNumber;
    private int updateId;

    float xMin = 0, xMax = 0;
    float yMin = 0, yMax = 0;
    float zMin = 0, zMax = 0;

    @Override
    public void run() {
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() throws IOException {


        client = new NetworkClient("localhost", "Teamname", "Fuer Fortnite");

        Mapper.graph = client.getGraph();
        Mapper.fillNodesMap();
        clusterController.startClustering();
        myNumber = client.getMyPlayerNumber();

        einfarbig = new BotController(0);
        gepunktet = new BotController(1);
        gestreift = new BotController(2);

        int counter = 0;
        if(myNumber==0) {
            gestreift.setPosition(client.getBotPosition(myNumber, 2));
            Mapper.calcStartNode(gestreift);

            while (true) {
                float[] dir0 = client.getBotDirection(0);
                float[] dir1 = client.getBotDirection(1);
                client.changeMoveDirection(0, -dir0[0], -dir0[1], -dir0[2]);
                client.changeMoveDirection(1, -dir1[0], -dir1[1], -dir1[2]);

                Mapper.setNewTargetNode(gestreift, myNumber);
                gestreift.setNewRouteTarget();
                changeMoveDirection(gestreift);
                gestreift.setPosition(client.getBotPosition(myNumber, 2));
                Mapper.checkIfArrivedAtRouteTarget(gestreift);

                if(counter >= 10000000) {
                    Mapper.graph = client.getGraph();
                    clusterController.updateClusters(myNumber);
                    Mapper.checkIfArrivedAtTarget(gestreift);
                    counter = 0;
                }
                counter += 1;
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