import lenz.htw.coshnost.net.NetworkClient;
import lenz.htw.coshnost.world.GraphNode;

public class Client implements Runnable {

    private int playerNo;
    private GraphNode[] graph;

    @Override
    public void run() {
        start();
    }

    private void start() {
        NetworkClient client = new NetworkClient("localhost", "Teamname", "Für Fortnite");

        graph =  client.getGraph();
        playerNo = client.getMyPlayerNumber();

        client.changeMoveDirection(0, 1,0,0);
        client.changeMoveDirection(1, 0,1,0);
        client.changeMoveDirection(2, 0,0,1);

        float[] pos = graph[0].getPosition();
        System.out.println(playerNo + ": " + pos[0] + ", " + pos[1] + ", " + pos[2]);
        GraphNode[] neighbors = graph[0].getNeighbors();    //immer genau 5 oder 6 Nachbarn

        System.out.println(playerNo + ": " + neighbors[0].x + ", " + neighbors[0].y + ", " + neighbors[0].z);

        float[] pos2 = client.getBotPosition(playerNo, 0);
        System.out.println(playerNo + ": " + pos2[0] + ", " + pos2[1] + ", " + pos2[2] + "\n");
    }
}