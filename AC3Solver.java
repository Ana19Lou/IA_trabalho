import java.util.*;

public class AC3Solver {
    Integer numberOfRectanglesToGuard;
    HashMap<Integer, ArrayList<Integer>> verticeRectangle;
    HashMap<Integer, ArrayList<Integer>> rectangleVertice;
    ArrayList<Integer> rectanglesToGuard;
    ArrayList<Integer> path;
    HashMap<Integer, Integer> domain;
    ArrayList<ArrayList<Integer>> allPaths;

    AC3Solver(int nRec,  ArrayList<Integer> rectanglesToGuard, HashMap<Integer, ArrayList<Integer>> verticeRectangle,
     HashMap<Integer, ArrayList<Integer>> rectangleVertice, HashMap<Integer, Integer> domain) {
        this.verticeRectangle = verticeRectangle;
        this.rectangleVertice = rectangleVertice;
        this.rectanglesToGuard = rectanglesToGuard;
        this.numberOfRectanglesToGuard = nRec;
        this.allPaths = new ArrayList<>();
        this.domain = domain;
        this.publicSolve(rectanglesToGuard, domain);
    }

    public void publicSolve(ArrayList<Integer> rectanglesToGuard, HashMap<Integer, Integer> domain) {
        this.solve(new ArrayList<Integer>(),new ArrayList<Integer>(),rectanglesToGuard, domain);
    }

    private void solve(ArrayList<Integer> path,ArrayList<Integer> guardedRectangles, ArrayList<Integer> rectanglesToGuard, HashMap<Integer, Integer> currentDomain) {
        if (guardedRectangles.size() == this.numberOfRectanglesToGuard) {
            allPaths.add(path);
        } else {
            for (Integer rectangle : lowerVertices(rectanglesToGuard)) {
                int vertice = getVertice(domain,rectangle,rectanglesToGuard);
                if (!path.contains(vertice)) {
                    ArrayList<Integer> relativeBestPath = new ArrayList<>(path);
                    ArrayList<Integer> relativeGuardedRectangles = new ArrayList<>(guardedRectangles);
                    ArrayList<Integer> relativeRectanglesToGuard = new ArrayList<>(rectanglesToGuard);
                    HashMap<Integer, Integer> relativeDomain = new HashMap<>();

                    for(Integer key : currentDomain.keySet()) {
                        relativeDomain.put(key, currentDomain.get(key));
                    }
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
                    if(revise(relativeDomain, vertice)) {
                        this.solve(relativeBestPath, relativeGuardedRectangles, relativeRectanglesToGuard, relativeDomain);
                    }
                }
            }
        }
    }

    private boolean revise(HashMap<Integer, Integer> relativeDomain, int vertice) {
        boolean change = false;
        for(Map.Entry<Integer, Integer> entry : relativeDomain.entrySet()) {
            if(entry.getValue() == null) {
                ArrayList<Integer> recsOfVert = new ArrayList<>(verticeRectangle.get(entry.getKey()));
                recsOfVert.retainAll(verticeRectangle.get(vertice));
                if(recsOfVert.size() == verticeRectangle.get(entry.getKey()).size()) {
                    entry.setValue(0);
                    change = true;
                }
            }
        }
        return change;    
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

    private int getVertice(HashMap<Integer, Integer> domain, Integer rectangle, ArrayList<Integer> rectanglesToGuard) {
        int max = 0;
        int v = 0;
        int val = 0;
        for (Integer vertice : this.rectangleVertice.get(rectangle)) {
			if(domain.get(vertice) != null) continue;
            for(Integer rec : verticeRectangle.get(vertice)) {
                if(rectanglesToGuard.contains(rec)) {
                    val++;
                }
            }
            if(val > max) {
                max = val;
                v = vertice;
            }
            val=0;
        }
        return v;
    }
}

