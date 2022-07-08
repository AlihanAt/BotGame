import lenz.htw.coshnost.world.GraphNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

//https://stackabuse.com/graphs-in-java-a-star-algorithm/
public class Pathfinder {
    /*
    g: der wert, den man für den weg aktuellen weg berechnet hat von start bis n
    h: heuristik, der weg von einem knoten n zum ziel, ist geschätzt
    f: beides zusammen addiert
     */

    public static List<AStarNode> aStar(GraphNode startNode, GraphNode targetNode){

        AStarNode start = new AStarNode(startNode, Mapper.getNodeIndex(startNode));
        AStarNode target = new AStarNode(targetNode, Mapper.getNodeIndex(targetNode));
        start.setHeuristic(start.calculateHeuristic(target));
        target.setHeuristic(0);

        PriorityQueue<AStarNode> closedList = new PriorityQueue<>();
        PriorityQueue<AStarNode> openList = new PriorityQueue<>();

        start.f = start.g + start.calculateHeuristic(target);
        openList.add(start);

        while (!openList.isEmpty()){
            AStarNode currentNode = openList.peek();
            if(currentNode.equals(target)){
                return createRouteList(currentNode);
            }

            int indexInGraph = Mapper.getNodeIndex(currentNode.node);
            currentNode.setNeighbors(Mapper.graph[indexInGraph].getNeighbors(), target);

            for(AStarNode.Edge edge : currentNode.neighbors){
                AStarNode nextNode = edge.node;
                float totalWeight = currentNode.g + edge.weight;

                if(!openList.contains(nextNode) && !closedList.contains(nextNode)) {
                    nextNode.parent = currentNode;
                    nextNode.g = totalWeight;
                    nextNode.f = nextNode.g + nextNode.calculateHeuristic(target);
                    openList.add(nextNode);
                }else{
                    if(totalWeight < nextNode.g){
                        nextNode.parent = currentNode;
                        nextNode.g = totalWeight;
                        nextNode.f = nextNode.calculateHeuristic(target);

                        if(closedList.contains(nextNode)){
                            closedList.remove(nextNode);
                            openList.add(nextNode);
                        }
                    }
                }
            }
            openList.remove(currentNode);
            closedList.add(currentNode);
        }
        return null;
    }

    private static List<AStarNode> createRouteList(AStarNode node){
        AStarNode n = node;
        List<AStarNode> route = new ArrayList<>();

        while(n.parent != null){
            route.add(n);
            n = n.parent;
        }
        route.add(n);
        Collections.reverse(route);
        return route;
    }

}
