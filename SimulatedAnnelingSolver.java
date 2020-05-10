import java.util.*;
import java.lang.Math;

public class SimulatedAnnelingSolver {
    HashMap<ArrayList<Integer>, Double> allSolutions = new HashMap<>();

    Integer allRecs;
    Random rand;
    ArrayList<Integer> finalVerts;

    ArrayList<Integer> bestPath;

    ArrayList<Integer> rectanglesToGuard;

    HashMap<Integer, ArrayList<Integer>> verticeRectangle;
    

    SimulatedAnnelingSolver(int nRec, ArrayList<Integer> rectanglesToGuard, HashMap<Integer, ArrayList<Integer>> verticeRectangle) {
        this.rectanglesToGuard = rectanglesToGuard;

        this.verticeRectangle = verticeRectangle;

        this.allRecs = nRec;

        this.solve();
    }
    
    public void solve() {
        double temperature = 10000;
        double coolingRate = 0.005;
        double maxProb = 1;
        int delta;
        HashMap<Integer, Integer> currentSolution = new HashMap<>();
        ArrayList<Integer> keys = new ArrayList<>(verticeRectangle.keySet());
        for(Map.Entry<Integer, ArrayList<Integer>> entry : verticeRectangle.entrySet()) {
            currentSolution.put(entry.getKey(), 0);
        }
        int currentH;
        int previousH = getHeuristic(currentSolution);
        while (temperature > 1) {
            currentSolution = new HashMap<>(generateNewSolution(currentSolution, keys));
            currentH = getHeuristic(currentSolution);
            ArrayList<Integer> verticesWithGuards = new ArrayList<>(checkGuards(currentSolution));
            delta = currentH - previousH;
            if(delta < 0) {
                allSolutions.put(verticesWithGuards, maxProb);
                previousH = currentH;
            } else {
                allSolutions.put(verticesWithGuards, probability(delta, temperature));
            }
            temperature *= 1-coolingRate;
        }
        double max = 0;
        ArrayList<Integer> bestSolutionSoFar = new ArrayList<>();
        for(Map.Entry<ArrayList<Integer>, Double> entry : allSolutions.entrySet()) {
            if(entry.getValue() >= max) {
                if(entry.getValue() == max) {
                    if(bestSolutionSoFar.size() > allRecs/3) {
                        if(entry.getKey().size() > allRecs/3) {
                            if(entry.getKey().size() < bestSolutionSoFar.size()){
                                bestSolutionSoFar = entry.getKey();
                            }
                        }
                    } else {
                        if(entry.getKey().size() > bestSolutionSoFar.size()) {
                            bestSolutionSoFar = entry.getKey();
                        }
                    }
                } else {
                    max = entry.getValue();
                    bestSolutionSoFar = entry.getKey();
                }
            }
        }
        bestPath = bestSolutionSoFar;
    }

    private HashMap<Integer, Integer> generateNewSolution(HashMap<Integer, Integer> currentSolution, ArrayList<Integer> keys ) {
        HashMap<Integer,Integer> newSolution = new HashMap<>();
        for(Map.Entry<Integer, Integer> entry : currentSolution.entrySet()) {
            newSolution.put(entry.getKey(), entry.getValue());
        }
        rand = new Random();
        int randomVertice = keys.get(rand.nextInt(keys.size()));
        if(newSolution.get(randomVertice) == 0) {
            newSolution.replace(randomVertice, 1);
        } else {
            newSolution.replace(randomVertice, 0);
        }
        return newSolution;
    }

    private ArrayList<Integer> checkGuards(HashMap<Integer, Integer> newSolution) {
        ArrayList<Integer> verticesWithGuards = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : newSolution.entrySet()) {
            if(entry.getValue() == 1) {
                verticesWithGuards.add(entry.getKey());
            }
        }
        return verticesWithGuards;
    }

    private double probability(int delta, double temperature) {
        double division = (double) -delta / temperature;
        double result = (double) Math.pow(Math.E, division);
        return result;
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