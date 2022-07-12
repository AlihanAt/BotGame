package Network;

import Cluster.ClusterController;
import Game.BotController;
import Game.Mapper;
import lenz.htw.coshnost.net.NetworkClient;

import java.io.IOException;

public class Client implements Runnable {

    private NetworkClient client;

    private BotController einfarbig;
    private BotController gepunktet;
    private BotController gestreift;
    private final ClusterController clusterController = new ClusterController();

    public int myNumber;
    private int counter = 0;

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

        setup();

        //später nur doOwnTurn
        if(myNumber==0) {
            doOwnTurn();
        }
        else {
            doEnemyTurn();
        }

    }

    private void doEnemyTurn() {
        while(true) {
            float[] dir0 = client.getBotDirection(0);
            float[] dir1 = client.getBotDirection(1);
            float[] dir2 = client.getBotDirection(2);
            client.changeMoveDirection(0, -dir0[0], -dir0[1], -dir0[2]);
            client.changeMoveDirection(1, -dir1[0], -dir1[1], -dir1[2]);
            client.changeMoveDirection(2, -dir2[0], -dir2[1], -dir2[2]);
        }
    }

    private void doOwnTurn() {
        einfarbig.initializeBot(client.getBotPosition(myNumber, 0));
        gepunktet.initializeBot(client.getBotPosition(myNumber, 1));
        gestreift.initializeBot(client.getBotPosition(myNumber, 2));

        while (true) {
            MoveBot(einfarbig, 0);
            MoveBot(gepunktet, 1);
            MoveBot(gestreift, 2);
        }
    }

    private void MoveBot(BotController botToMove, int botNr) {
        botToMove.setNewTarget(clusterController);
        botToMove.setPosition(client.getBotPosition(myNumber, botNr));
        botToMove.checkIfArrivedAtRouteTarget();
        changeMoveDirection(botToMove);

        if(counter >= 10000000) {
            Mapper.graph = client.getGraph();
            clusterController.updateClusters(myNumber);
            botToMove.checkIfArrivedAtTarget();
            counter = 0;
        }
        counter += 1;
    }

    private void setup() {
        Mapper.graph = client.getGraph();
        Mapper.fillNodesMap();
        clusterController.startClustering();
        myNumber = client.getMyPlayerNumber();
        Mapper.myNumber = myNumber;

        einfarbig = new BotController(0);
        gepunktet = new BotController(1);
        gestreift = new BotController(2);
    }

    private void changeMoveDirection(BotController bot) {
        float[] dir = bot.updateMoveDirection();
        client.changeMoveDirection(bot.getBotNumber(), dir[0], dir[1], dir[2]);
    }

}