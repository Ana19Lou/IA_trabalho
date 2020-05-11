import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BFSSolver {
    ArrayList<Integer> bestSolution;

    Integer numberOfRectanglesToGuard;

    HashMap<Integer, ArrayList<Integer>> rectangleVertice;
    HashMap<Integer, ArrayList<Integer>> verticeRectangle;

    BFSSolver(int nRec, ArrayList<Integer> rectanglesToGuard, HashMap<Integer, ArrayList<Integer>> rectangleVertice, HashMap<Integer, ArrayList<Integer>> verticeRectangle) {
        this.rectangleVertice = rectangleVertice;
        this.verticeRectangle = verticeRectangle;

        this.numberOfRectanglesToGuard = nRec;

        this.bestSolution = new ArrayList<>();
        for (Integer rectangle : lowerVertices(rectanglesToGuard)) {
            for (Integer vertice : this.rectangleVertice.get(rectangle)) {
                this.findPaths(vertice, rectanglesToGuard);
            }
        }
    }

    private ArrayList<Integer> findPaths(Integer currentVertice, ArrayList<Integer> localRectanglesToGuard) {
        Queue<BFSState> queue = new LinkedList<>();

        BFSState state = new BFSState(currentVertice, localRectanglesToGuard);
        state.guardRectangles(this.verticeRectangle.get(currentVertice));

        queue.add(state);
        while (!queue.isEmpty()) {
            state = queue.poll();
            
            currentVertice = state.getCurrentVertice();
    
            if (state.allRectanglesGuarded()) {
                if (this.bestSolution.size() == 0 || state.getPath().size() < this.bestSolution.size()) {
                    this.bestSolution = state.getPath();
                }
                break;
            } else {
                for (Integer rectangle : lowerVertices(state.getUnguardedRectangles())) {
                    if (!state.hasRectangleBeenGuarded(rectangle)) {
                        for (Integer vertice : this.rectangleVertice.get(rectangle)) {
                            if (!state.hasVerticeBeenVisited(vertice)) {
                                BFSState newState = new BFSState(state);
            
                                newState.addVerticeToPath(vertice);
                                newState.guardRectangles(this.verticeRectangle.get(vertice));
                                newState.visitVertice(vertice);
            
                                queue.add(newState);
                            }
                        }
                    }
                }
            }
        }

        return bestSolution;
    }

    private ArrayList<Integer> lowerVertices(ArrayList<Integer> rectangles) {
		int min = Integer.MAX_VALUE;
		ArrayList<Integer> result = new ArrayList<>();
		for (Integer rec : rectangles) {
			if (this.rectangleVertice.get(rec).size() <= min) {
				if(this.rectangleVertice.get(rec).size() < min) {
					min = this.rectangleVertice.get(rec).size();
					result.clear();
				}
				result.add(rec);
			} 
        }
		return result;
    }
}