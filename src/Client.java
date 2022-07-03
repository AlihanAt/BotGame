import lenz.htw.coshnost.net.NetworkClient;
import lenz.htw.coshnost.world.GraphNode;

import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Client implements Runnable {

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

        NetworkClient client = new NetworkClient("localhost", "Teamname", "Für Fortnite");
        graph =  client.getGraph();
        myNumber = client.getMyPlayerNumber();

        if(myNumber == 3){
            writer.write(graph[10].toString() + "\n");
            System.out.println(graph[10].toString() + "\n");
            GraphNode[] nachbarn = graph[10].getNeighbors();

            for (GraphNode node : nachbarn) {
                writer.write(node.toString() + "\n");
                System.out.println(node.toString() + "\n");
            }
        }


        BotController einfarbig = new BotController(0);
        BotController gepunktet = new BotController(1);
        BotController gestreift = new BotController(2);

        int tempId;
        GraphNode tempNode;
        if(myNumber == 1) {
            System.out.println("hello, " + myNumber + ", " + graph[10].toString());
            calcRange(graph[10]);
            while (true) {
                tempId = client.getMostRecentUpdateId();
                if (updateId != tempId) {

                    int x=0,y=0,z = 0;

                    gestreift.setPosition(client.getBotPosition(myNumber, 2));
                    tempNode = graph[10];
                    if (gestreift.getPosition()[0] < tempNode.x) {
                        x=1;
                    } else if (gestreift.getPosition()[0] > tempNode.x) {
                        x = -1;
                    }

                    if(gestreift.getPosition()[1] < tempNode.y){
                        y=1;
                    } else if (gestreift.getPosition()[1] > tempNode.y){
                        y=-1;
                    }

                    if(gestreift.getPosition()[2] < tempNode.z){
                        z=1;
                    } else if (gestreift.getPosition()[2] > tempNode.z){
                        z=-1;
                    }
                    client.changeMoveDirection(2,x,y,z);

                    arrivedAtTarget(client.getBotPosition(myNumber,2), graph[10]);

//                    if(client.getBotPosition(myNumber,2) == graph[10].getPosition()){
//                        System.out.println("Ziel erreicth!");
//                    }

//                boolean x = true;
//                if (x){
//                    gestreift.setPosition(client.getBotPosition(myNumber, 2));
//                    tempNode = getNodeByPosition(gestreift.getPosition());
//                    x= false;
//                }

//                gestreift.setPosition(client.getBotPosition(myNumber, 2));
//                tempNode = getNodeByPosition(gestreift.getPosition());
//                if(tempNode.isBlocked()){
//                    //änder den weg?
//                    System.out.println("is blocked");
//                }

                }
            }
        }

//        client.changeMoveDirection(0, 1,0,0);
//        client.changeMoveDirection(1, 0,1,0);
//        client.changeMoveDirection(2, 0,0,1);
//
//        float[] pos = graph[0].getPosition();
//        System.out.println(myNumber + ": " + pos[0] + ", " + pos[1] + ", " + pos[2]);
//        GraphNode[] neighbors = graph[0].getNeighbors();    //immer genau 5 oder 6 Nachbarn
//
//        System.out.println(myNumber + ": " + neighbors[0].x + ", " + neighbors[0].y + ", " + neighbors[0].z);
//
//        float[] pos2 = client.getBotPosition(myNumber, 0);
//        System.out.println(myNumber + ": " + pos2[0] + ", " + pos2[1] + ", " + pos2[2] + "\n");
    }

    private void arrivedAtTarget(float[] pos, GraphNode node){
        System.out.println(pos[0] + ", " + pos[1] + ", " + pos[2]);
        if((pos[0]>xMin && pos[0]<xMax) && (pos[1]>yMin && pos[1]<yMax) && (pos[2]>zMin && pos[2]<zMax)){
            System.out.println("Ziel erreicht");
        }

    }

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
        System.out.println("X: " + xMin + " - " + xMax +
                "\nY: " + yMin + " - " + yMax +
                "\nZ: " + zMin + " - " + zMax);
    }

    private GraphNode getNodeByPosition(float[] pos) throws IOException {

        for (GraphNode node : graph) {
//            writer.write(node.toString() + "\n");
            if(node.x == pos[0] && node.y == pos[1] && node.y == pos[2] ){
                return node;
            }
        }

        return null;
    }
}