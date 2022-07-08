import lenz.htw.coshnost.world.GraphNode;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Pathfinder {
    /*
    g: der wert, den man für den weg aktuellen weg berechnet hat von start bis n
    h: heuristik, der weg von einem knoten n zum ziel, ist geschätzt
    f: beides zusammen addiert
     */

    /*
    Notiz: ich muss immer nachbarn hinzufügen
        ich muss heuritik methiode ausfüllen, am besten mit nur distanz zum ziel
     */
    private static AStarNode aStar(AStarNode start, AStarNode target){

        PriorityQueue<AStarNode> closedList = new PriorityQueue<>();
        PriorityQueue<AStarNode> openList = new PriorityQueue<>();

        start.f = start.g + start.calculateHeuristic(target);
        openList.add(start);

        while (!openList.isEmpty()){
            AStarNode n = openList.peek();
            if(n == target){
                return n;
            }

            for(AStarNode.Edge edge : n.neighbors){
                AStarNode m = edge.node;
                float totalWeight = n.g + edge.weight;

                if(!openList.contains(m) && !closedList.contains(m)) {
                    m.parent = n;
                    m.g = totalWeight;
                    m.f = m.g + m.calculateHeuristic(target);
                    openList.add(m);
                }else{
                    if(totalWeight < m.g){
                        m.parent = n;
                        m.g = totalWeight;
                        m.f = m.calculateHeuristic(target);

                        if(closedList.contains(m)){
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }
            openList.remove(n);
            closedList.add(n);
        }
        return null;
    }

}
