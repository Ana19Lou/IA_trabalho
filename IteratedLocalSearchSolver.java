import java.util.*;

public class IteratedLocalSearchSolver {
    HashMap<Integer,Integer> bestPath;
    Integer bestH;
    ArrayList<HashMap<Integer, Integer>> allSolutions;

    Integer allRecs;
    Random rand;
    ArrayList<Integer> finalVerts;

    ArrayList<Integer> rectanglesToGuard;

    HashMap<Integer, ArrayList<Integer>> verticeRectangle;

    IteratedLocalSearchSolver(int nRec, ArrayList<Integer> rectanglesToGuard, HashMap<Integer, ArrayList<Integer>> verticeRectangle) {
        this.bestPath = new HashMap<>();
        this.rectanglesToGuard = rectanglesToGuard;

        this.verticeRectangle = verticeRectangle;

        this.allRecs = nRec;

        this.solve();
    }
    
    public void solve() {
        HashMap<Integer, Integer> guards = new HashMap<>();
        guards = creategreedySolution(guards);
        int h = getHeuristic(guards);
        bestPath = guards;
        bestH = h;
        HashMap<Integer, Integer> firstPath = guards;
        for(Integer vert : firstPath.keySet()) {
            guards = new HashMap<>(firstPath);
            if(guards.get(vert) == 0) {
                guards.replace(vert,1);
            } else {
                guards.replace(vert,0);
            }
            h = getHeuristic(guards);
            if(h < bestH) {
                bestH = h;
                bestPath = guards;
            }
            HashMap<Integer, Integer> secondPath;
            secondPath = new HashMap<>(guards);
            System.out.println("Second Path: "+secondPath);
            for(Integer secondVert : secondPath.keySet()) {
                if(secondVert == vert) continue;
                guards = new HashMap<>(secondVert);
                System.out.println("Fisrt Flip: "+firstPath);
                System.out.println("Second Flip: "+secondPath);
                if(guards.get(secondVert) == 0) {
                    guards.replace(secondVert,1);
                } else {
                    guards.replace(secondVert,0);
                }
                h = getHeuristic(guards);
                if(h < bestH) {
                    bestH = h;
                    bestPath = guards;
                }
            } 
        }
        finalVerts = new ArrayList<>();
        for(Integer vert : bestPath.keySet()) {
            if(bestPath.get(vert) == 1) {
                finalVerts.add(vert);
            }
        }
    }

    private HashMap<Integer,Integer> creategreedySolution(HashMap<Integer, Integer> guards) {
        HashMap<Integer, ArrayList<Integer>> shallowCopy = new HashMap<>();
        for(Map.Entry<Integer, ArrayList<Integer>> entry : verticeRectangle.entrySet()) {
            ArrayList<Integer> values = new ArrayList<>(entry.getValue());
            shallowCopy.put(entry.getKey(), values);
        }
		int max = 0;
		int maxVertice = 0;
		ArrayList<Integer> guardedVertices = new ArrayList<>();
        ArrayList<Integer> rectanglesMax = new ArrayList<>();
        ArrayList<Integer> possibleRectangles = new ArrayList<>(rectanglesToGuard);
		while(possibleRectangles.size() != 0) {
			for (Map.Entry<Integer, ArrayList<Integer>> entry : shallowCopy.entrySet()) {
				ArrayList<Integer> listRectangles = entry.getValue();
				if(listRectangles.size() == 3) {
					rectanglesMax = new ArrayList<>(listRectangles);
					maxVertice = entry.getKey();
					break;
				} else {
					if(listRectangles.size() > max) {
						rectanglesMax = new ArrayList<>(listRectangles);
						max = listRectangles.size();
						maxVertice = entry.getKey();
					}
				}
			}
			guardedVertices.add(maxVertice);
			max = 0;
			maxVertice = 0;
			if(rectanglesMax.size() != 0) {
				for(Integer rec : rectanglesMax) {
					if(possibleRectangles.contains(rec)) {
						possibleRectangles.remove(rec);
					}
				}
				removeRectangles(possibleRectangles, shallowCopy);
			}
        }
        System.out.println("verticeRectangle: "+verticeRectangle);
        for(Integer vertice: verticeRectangle.keySet()) {
            if(guardedVertices.contains(vertice)) {
                guards.put(vertice, 1);
            } else {
                guards.put(vertice, 0);
            }
        }
        return guards;
    }

    private void removeRectangles(ArrayList<Integer> possibleRectangles, HashMap<Integer, ArrayList<Integer>> map) {
		for (int i = 1; i <= allRecs; i++) {
			if (!possibleRectangles.contains(i)) {
				for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
					if (entry.getValue().contains(i)) {
						entry.getValue().remove(entry.getValue().indexOf(i));
					}
				}

			}
		}
	}

    private int getHeuristic(HashMap<Integer, Integer> guards) {
        int g,f;
        g = 0;
        f = 0;
        ArrayList<Integer> rectanglesNotGuarded = new ArrayList<>(rectanglesToGuard);
        //System.out.println("rectanglesToGuard"+rectanglesToGuard);
        System.out.println("rectanglesNotGuarded"+rectanglesNotGuarded);
        for(Integer vertice: guards.keySet()) {
            if(guards.get(vertice) == 1) {
                g++;
                //System.out.println("verticeRectangle.get(vertice):"+verticeRectangle.get(vertice));
                for(Integer rectangle: verticeRectangle.get(vertice)) {
                    //System.out.println("A ver o retangulo: "+rectangle);
                    if(rectanglesNotGuarded.contains(rectangle)) {
                        rectanglesNotGuarded.remove(rectangle);
                    }
                }
            }
        }
        System.out.println("rectanglesNotGuarded after"+rectanglesNotGuarded);
        f = rectanglesNotGuarded.size();
        System.out.println("G: "+g);
        System.out.println("F: "+f);
        System.out.println("R: "+allRecs);
        int val = g + f * (2 * allRecs + 3);
        System.out.println("G+F(2R+3): "+val);
        return (g + f * (2 * allRecs + 3));
    }

}