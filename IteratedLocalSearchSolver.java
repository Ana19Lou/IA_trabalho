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
        bestH = h;
        for(Map.Entry<Integer, Integer> entry : guards.entrySet()) {
            bestPath.put(entry.getKey(), entry.getValue());
        }

        for(Integer vertice : guards.keySet()) {
            if(guards.get(vertice) == 1) {
                guards.replace(vertice, 0);
            } else {
                guards.replace(vertice, 1);
            }
            h = getHeuristic(guards);
            if(h < bestH) {
                bestH = h;
                for(Map.Entry<Integer, Integer> entry : guards.entrySet()) {
                    bestPath.put(entry.getKey(), entry.getValue());
                }
            }
            for(Integer secondVertice : guards.keySet()) {
                if(guards.get(secondVertice) == 1) {
                    guards.replace(secondVertice, 0);
                } else {
                    guards.replace(secondVertice, 1);
                }
                h = getHeuristic(guards);
                if(h < bestH) {
                    bestH = h;
                    for(Map.Entry<Integer, Integer> entry : guards.entrySet()) {
                        bestPath.put(entry.getKey(), entry.getValue());
                    }
                }
                // for(Integer thridVertice : guards.keySet()) {
                //     if(guards.get(thridVertice) == 1) {
                //         guards.replace(thridVertice, 0);
                //     } else {
                //         guards.replace(thridVertice, 1);
                //     }
                //     h = getHeuristic(guards);
                //     if(h < bestH) {
                //         bestH = h;
                //         for(Map.Entry<Integer, Integer> entry : guards.entrySet()) {
                //             bestPath.put(entry.getKey(), entry.getValue());
                //         }
                //     }
                //     if(guards.get(thridVertice) == 1) {
                //         guards.replace(thridVertice, 0);
                //     } else {
                //         guards.replace(thridVertice, 1);
                //     }
                // }
                if(guards.get(secondVertice) == 1) {
                    guards.replace(secondVertice, 0);
                } else {
                    guards.replace(secondVertice, 1);
                }
            }
            if(guards.get(vertice) == 1) {
                guards.replace(vertice, 0);
            } else {
                guards.replace(vertice, 1);
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
        HashMap<Integer, ArrayList<Integer>> copy = new HashMap<>();
        for(Map.Entry<Integer, ArrayList<Integer>> entry : verticeRectangle.entrySet()) {
            ArrayList<Integer> values = new ArrayList<>(entry.getValue());
            copy.put(entry.getKey(), values);
        }
		int max = 0;
		int maxVertice = 0;
		ArrayList<Integer> guardedVertices = new ArrayList<>();
        ArrayList<Integer> rectanglesMax = new ArrayList<>();
        ArrayList<Integer> possibleRectangles = new ArrayList<>(rectanglesToGuard);
		while(possibleRectangles.size() != 0) {
			for (Map.Entry<Integer, ArrayList<Integer>> entry : copy.entrySet()) {
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
				removeRectangles(possibleRectangles, copy);
			}
        }
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
        for(Integer vertice: guards.keySet()) {
            if(guards.get(vertice) == 1) {
                g++;
                for(Integer rectangle: verticeRectangle.get(vertice)) {
                    if(rectanglesNotGuarded.contains(rectangle)) {
                        rectanglesNotGuarded.remove(rectangle);
                    }
                }
            }
        }
        f = rectanglesNotGuarded.size();
        return (g + f * (2 * allRecs + 3));
    }

}