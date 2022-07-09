import lenz.htw.coshnost.net.NetworkClient;

import java.io.IOException;

public class Client implements Runnable {

    private NetworkClient client;

    private BotController einfarbig;
    private BotController gepunktet;
    private BotController gestreift;
    private ClusterController clusterController = new ClusterController();

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
        einfarbig.setPosition(client.getBotPosition(myNumber, 0));
        gepunktet.setPosition(client.getBotPosition(myNumber, 1));
        gestreift.setPosition(client.getBotPosition(myNumber, 2));
        Mapper.setClosestNode(einfarbig);
        Mapper.setClosestNode(gepunktet);
        Mapper.setClosestNode(gestreift);

        while (true) {
            MoveBot(einfarbig, 0);
            MoveBot(gepunktet, 1);
            MoveBot(gestreift, 2);
        }
    }

    private void MoveBot(BotController botToMove, int botNr) {
        Mapper.setNewTargetNode(botToMove, myNumber, clusterController);
        botToMove.setNewRouteTarget();
        changeMoveDirection(botToMove);
        botToMove.setPosition(client.getBotPosition(myNumber, botNr));
        Mapper.checkIfArrivedAtRouteTarget(botToMove);

        if(counter >= 10000000) {
            Mapper.graph = client.getGraph();
            clusterController.updateClusters(myNumber);
            Mapper.checkIfArrivedAtTarget(botToMove);
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
        client.changeMoveDirection(bot.getBotNumber(), (float)dir[0], (float) dir[1], (float) dir[2]);
    }

}