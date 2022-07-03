import lenz.htw.coshnost.net.NetworkClient;
import lenz.htw.coshnost.world.GraphNode;

public class Test {

    public static void main(String[] args) {

        NetworkClient client = new NetworkClient(null, "Teamname", "Juhu, ich habe gewonnen!");

        client.getMyPlayerNumber();
        client.getScore(0);
        client.getBotSpeed(0);    //Konstanten

        float[] botPosition = client.getBotPosition(0, 0);
        float[] botDirection = client.getBotDirection(0); // vermutlich nicht notwendig

        client.changeMoveDirection(0, 0); // vermutlich nicht notwendig

        client.changeMoveDirection(0, 1, 0, 0);
        client.getMostRecentUpdateId(); // wenn verändert, dann neue Daten vom Server da

        GraphNode[] graph = client.getGraph(); // immer Größe 10242
        graph[0].isBlocked(); // ist ein Graben oder nicht
        graph[0].getOwner(); // wem gehört das Feld? (0,1,2 = player number, -1 = leer)
        graph[0].getX(); // Ortsvektor
        float[] pos = graph[0].getPosition();
//        graph[0].ownership;
//        graph[0].isBlocked();

        GraphNode[] neighbors = graph[0].getNeighbors();    //immer genau 5 oder 6 Nachbarn
    }
}
