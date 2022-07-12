package Pathfinding;

import Game.Mapper;

public class Heuristic {

    public static float getOwnColorHeuristicValueFrom(AStarNode a) {

        if(a.getNode().getOwner() == Mapper.myNumber || a.getNode().getOwner() == -1){
            return 10000;
        }
        return 0;
    }

    public static float getTrenchHeuristicValueFrom(AStarNode a) {

        if(a.getNode().isBlocked()){
            return 10000000;
        }
        return 0;
    }
}
