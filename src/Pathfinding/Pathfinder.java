package Pathfinding;

import Game.Mapper;
import lenz.htw.coshnost.world.GraphNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

//https://stackabuse.com/graphs-in-java-a-star-algorithm/
public class Pathfinder {

    public static List<GraphNode> aStar(GraphNode startNode, GraphNode targetNode, int botNr){

        AStarNode start = new AStarNode(startNode, Mapper.getNodeIndex(startNode));
        AStarNode target = new AStarNode(targetNode, Mapper.getNodeIndex(targetNode));
        start.setHeuristic(start.calculateHeuristic(target) + getAdditionalHeuristics(start, botNr));
        target.setHeuristic(0);

        PriorityQueue<AStarNode> closedList = new PriorityQueue<>();
        PriorityQueue<AStarNode> openList = new PriorityQueue<>();

        start.f = start.g + start.calculateHeuristic(target) + getAdditionalHeuristics(start, botNr);
        openList.add(start);

        while (!openList.isEmpty()){
            AStarNode currentNode = openList.peek();
            if(currentNode.equals(target)){
                return createRouteList(currentNode);
            }

            int indexInGraph = Mapper.getNodeIndex(currentNode.getNode());
            currentNode.setNeighbors(Mapper.graph[indexInGraph].getNeighbors(), target);

            for(AStarNode.Edge edge : currentNode.neighbors){
                AStarNode nextNode = edge.node;
                float totalWeight = currentNode.g + edge.weight;

                if(!openList.contains(nextNode) && !closedList.contains(nextNode)) {
                    nextNode.parent = currentNode;
                    nextNode.g = totalWeight;
                    nextNode.f = nextNode.g + nextNode.calculateHeuristic(target) + getAdditionalHeuristics(nextNode, botNr);
                    openList.add(nextNode);
                }else{
                    if(totalWeight < nextNode.g){
                        nextNode.parent = currentNode;
                        nextNode.g = totalWeight;
                        nextNode.f = nextNode.g + nextNode.calculateHeuristic(target) + getAdditionalHeuristics(nextNode, botNr);

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

    private static List<GraphNode> createRouteList(AStarNode node){
        AStarNode n = node;
        List<GraphNode> route = new ArrayList<>();

        while(n.parent != null){
            route.add(n.getNode());
            n = n.parent;
        }
        route.add(n.getNode());
        Collections.reverse(route);
        return route;
    }

    private static float getAdditionalHeuristics(AStarNode node, int botNr){
        if(botNr == 2)
            return Heuristic.getOwnColorHeuristicValueFrom(node);
        else
            return Heuristic.getTrenchHeuristicValueFrom(node);
    }

}
