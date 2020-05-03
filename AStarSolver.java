import java.util.*;

public class AStarSolver {
    Integer numberOfRectanglesToGuard;

    HashMap<Integer, ArrayList<Integer>> rectangleVertice;
    HashMap<Integer, ArrayList<Integer>> verticeRectangle;

    AStarSolver(int nRec, HashMap<Integer, ArrayList<Integer>> rectangleVertice, HashMap<Integer, ArrayList<Integer>> verticeRectangle) {
        this.rectangleVertice = rectangleVertice;
        this.verticeRectangle = verticeRectangle;

        this.numberOfRectanglesToGuard = nRec;

        this.solve();
    }

    public void solve() {
        // Rectangulos não vigiados
        ArrayList<AStarNode> openList = new ArrayList<>();
        
        // Rectangulos vigiados
        ArrayList<AStarNode> closedList = new ArrayList<>();

        ArrayList<Integer> guardedRectangles = new ArrayList<>();

        while(guardedRectangles.size() != numberOfRectanglesToGuard) {
            AStarNode q = this.higherGuardedRectangles(openList);
            openList.remove(q);
            closedList.add(q);
            ArrayList<AStarNode> successors = this.getSuccessors(q);

            for (AStarNode successor : successors) {
                
            }
        }
    }

    public AStarNode leastH(ArrayList<AStarNode> openList) {
        int min = Integer.MAX_VALUE;
        AStarNode q;
        for (AStarNode node : openList) {
            if (node.h < min) {
                min = node.h;
                q = node;
            }
        }

        return q;
    }

    public ArrayList<AStarNode> getSuccessors(AStarNode q) {
        return new ArrayList<>();
    }
}