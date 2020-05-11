import java.util.*;

public class BranchAndBoundSolver {
    ArrayList<Integer> bestPath;
    Integer bound;
    Integer allRecs;

    Integer numberOfRectanglesToGuard;

    HashMap<Integer, ArrayList<Integer>> rectangleVertice;
    HashMap<Integer, ArrayList<Integer>> verticeRectangle;

    BranchAndBoundSolver(int nRec,int allRecs, ArrayList<Integer> rectanglesToGuard, HashMap<Integer, ArrayList<Integer>> rectangleVertice, HashMap<Integer, ArrayList<Integer>> verticeRectangle) {
        this.bestPath = new ArrayList<>();
        this.bound = Integer.MAX_VALUE;

        this.allRecs = allRecs;

        this.rectangleVertice = rectangleVertice;
        this.verticeRectangle = verticeRectangle;

        this.numberOfRectanglesToGuard = nRec;

        this.publicSolve(nRec, rectanglesToGuard);
    }

    public void publicSolve(int nRec, ArrayList<Integer> rectanglesToGuard) {
        this.solve(new ArrayList<Integer>(), 1, new ArrayList<Integer>(), rectanglesToGuard);
    }
    
    private ArrayList<Integer> solve(ArrayList<Integer> bestPath, int relativeBound, ArrayList<Integer> guardedRectangles, ArrayList<Integer> rectanglesToGuard) {
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
        return bestPath;
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

        ArrayList<Integer> finalVertices = new ArrayList<>(vertices);
        for(Integer vertice1: vertices) {
            if(finalVertices.size() == 1) break;
            for(Integer vertice2: vertices) {
                ArrayList<Integer> tempvertices1 = new ArrayList<>(solution.get(vertice1));
                ArrayList<Integer> tempvertices2 = new ArrayList<>(solution.get(vertice2));
                if(vertice1 == vertice2) break;
                if(finalVertices.contains(vertice1) && finalVertices.contains(vertice2)) {
                    if(tempvertices1.size() <= tempvertices2.size()) {
                        tempvertices1.retainAll(tempvertices2);
                        if(tempvertices1.size() == solution.get(vertice1).size()){
                            if(finalVertices.contains(vertice1)) {
                                finalVertices.remove(vertice1);
                            }
                        }
                    }
                }
            }
        }
		return finalVertices;
	}
}