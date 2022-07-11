public class Heuristic {

    public static float getOwnColorHeuristicValueFrom(AStarNode a) {

        if(a.node.getOwner() == Mapper.myNumber || a.node.getOwner() == -1){
            return 10000;
        }
        return 0;
    }

    public static float getTrenchHeuristicValueFrom(AStarNode a) {

        if(a.node.isBlocked()){
            return 10000000;
        }
        return 0;
    }
}
