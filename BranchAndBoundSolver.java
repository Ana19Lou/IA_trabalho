import java.util.ArrayList;
import java.util.HashMap;

public class BranchAndBoundSolver {
    ArrayList<Integer> bestPath;
    Integer bound;

    Integer numberOfRectanglesToGuard;

    HashMap<Integer, ArrayList<Integer>> rectangleVertice;
    HashMap<Integer, ArrayList<Integer>> verticeRectangle;

    BranchAndBoundSolver(int nRec, ArrayList<Integer> rectanglesToGuard, HashMap<Integer, ArrayList<Integer>> rectangleVertice, HashMap<Integer, ArrayList<Integer>> verticeRectangle) {
        this.bestPath = new ArrayList<>();
        this.bound = Integer.MAX_VALUE;

        this.rectangleVertice = rectangleVertice;
        this.verticeRectangle = verticeRectangle;

        this.numberOfRectanglesToGuard = nRec;

        this.publicSolve(nRec, rectanglesToGuard);
    }

    public void publicSolve(int nRec, ArrayList<Integer> rectanglesToGuard) {
        this.solve(new ArrayList<Integer>(), 1, new ArrayList<Integer>(), rectanglesToGuard);
    }
    
    private void solve(ArrayList<Integer> bestPath, int relativeBound, ArrayList<Integer> guardedRectangles, ArrayList<Integer> rectanglesToGuard) {
        if (bestPath.size() < this.bound) {
            if (guardedRectangles.size() == this.numberOfRectanglesToGuard) {
                this.bestPath = new ArrayList<>(bestPath);
                this.bound = relativeBound;
            } else {
                for (Integer rectangle : lowerVertices(rectanglesToGuard)) {
                    for (Integer vertice : candidateVertices(rectangle)) {
                        if (!bestPath.contains(vertice)) {
                            ArrayList<Integer> relativeBestPath = new ArrayList<>(bestPath);
                            ArrayList<Integer> relativeGuardedRectangles = new ArrayList<>(guardedRectangles);
                            ArrayList<Integer> relativeRectanglesToGuard = new ArrayList<>(rectanglesToGuard);
    
                            relativeBestPath.add(vertice);
    
                            // Rectangles
                            for (Integer r : this.verticeRectangle.get(vertice)) {
                                int index = relativeRectanglesToGuard.indexOf(r);
                                if (index >= 0) {
                                    relativeRectanglesToGuard.remove(index);
                                }
                                if (!relativeGuardedRectangles.contains(r)) {
                                    relativeGuardedRectangles.add(r);
                                }
                            }
    
                            this.solve(relativeBestPath, relativeBound + 1, relativeGuardedRectangles, relativeRectanglesToGuard);
                        }
                    }
                }
            }
        }
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
    
    private ArrayList<Integer> candidateVertices(Integer rectangle) {
		ArrayList<Integer> vertices = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> solution = new HashMap<>();
        
		for (Integer vertice : this.rectangleVertice.get(rectangle)) {
			solution.put(vertice, this.verticeRectangle.get(vertice));
			vertices.add(vertice);
        }

		return vertices;
	}
}