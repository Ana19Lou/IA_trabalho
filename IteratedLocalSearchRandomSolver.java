import java.util.*;

public class IteratedLocalSearchRandomSolver {
    HashMap<Integer,Integer> bestPath;
    Integer bestH;
    ArrayList<HashMap<Integer, Integer>> allSolutions;

    Integer allRecs;
    Random rand;

    HashMap<Integer, ArrayList<Integer>> verticeRectangle;

    IteratedLocalSearchRandomSolver(int nRec, ArrayList<Integer> rectanglesToGuard, HashMap<Integer, ArrayList<Integer>> verticeRectangle) {
        this.bestPath = new HashMap<>();

        this.verticeRectangle = verticeRectangle;

        this.allRecs = nRec;

        this.solve(rectanglesToGuard);
    }
    
    public void solve(ArrayList<Integer> rectanglesToGuard) {
        HashMap<Integer, Integer> guards = new HashMap<>();
        guards = createRandomSolution(guards);
        int h = getHeuristic(guards,rectanglesToGuard);
        bestPath = guards;
        bestH = h;
        HashMap<Integer, Integer> firstPath = new HashMap<>();
        for(Map.Entry<Integer, Integer> entry : guards.entrySet()) {
            firstPath.put(entry.getKey(), entry.getValue());
        }
        for(Integer vert : firstPath.keySet()) {
            guards = new HashMap<>(firstPath);
            if(guards.get(vert) == 0) {
                guards.replace(vert,1);
            } else {
                guards.replace(vert,0);
            }
            h = getHeuristic(guards, rectanglesToGuard);
            if(h < bestH) {
                bestH = h;
                bestPath = guards;
            }
        }
    }

    private HashMap<Integer,Integer> createRandomSolution(HashMap<Integer, Integer> guards) {
        rand = new Random();
        for(Integer vert : verticeRectangle.keySet()) {
            guards.put(vert,rand.nextInt(2));
        }
        return guards;
    }

    private int getHeuristic(HashMap<Integer, Integer> guards, ArrayList<Integer> rectanglesToGuard) {
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