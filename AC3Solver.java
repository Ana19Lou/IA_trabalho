import java.util.*;

public class AC3Solver {
    Integer numberOfRectanglesToGuard;
    HashMap<Integer, ArrayList<Integer>> verticeRectangle;
    HashMap<Integer, ArrayList<Integer>> rectangleVertice;
    ArrayList<Integer> rectanglesToGuard;
    ArrayList<Integer> path;
    HashMap<Integer, Integer> domain;

    AC3Solver(int nRec,  ArrayList<Integer> rectanglesToGuard, HashMap<Integer, ArrayList<Integer>> verticeRectangle,
     HashMap<Integer, ArrayList<Integer>> rectangleVertice, HashMap<Integer, Integer> domain) {
        this.verticeRectangle = verticeRectangle;
        this.rectangleVertice = rectangleVertice;
        this.rectanglesToGuard = rectanglesToGuard;
        this.domain = domain;
        this.numberOfRectanglesToGuard = nRec;

        this.publicSolve(rectanglesToGuard,domain);
    }

    public void publicSolve(ArrayList<Integer> rectanglesToGuard, HashMap<Integer, Integer> domain) {   
        //System.out.println(verticeRectangle);
        while(rectanglesToGuard.size() != 0) {
            int vertice = getVertice(domain);
            domain.replace(vertice, 1);
            for(Integer rec : this.verticeRectangle.get(vertice)){
                if(rectanglesToGuard.contains(rec)) {
                    rectanglesToGuard.remove(rec);
                }
            }
            // System.out.println("Escolhido o vertice: "+vertice);
            // System.out.println("Falta guardar: "+rectanglesToGuard);
            if(revise(domain, vertice)) {
                System.out.println("It's going");
            }
            removeRectangles(rectanglesToGuard);
        }
        ArrayList<Integer> guards = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : domain.entrySet()) {
            if(entry.getValue() == 1) {
                guards.add(entry.getKey());
            }
        }
        System.out.println(guards);
    }

    private boolean revise(HashMap<Integer, Integer> domain, int vertice) {
        boolean change = false;
        for(Map.Entry<Integer, Integer> entry : domain.entrySet()) {
            if(entry.getValue() == null) {
                ArrayList<Integer> recsOfVert = new ArrayList<>(verticeRectangle.get(entry.getKey()));
                recsOfVert.retainAll(verticeRectangle.get(vertice));
                System.out.println("verticeRectangle.get(entry.getKey())" + verticeRectangle.get(entry.getKey()));
                if(recsOfVert.size() == verticeRectangle.get(entry.getKey()).size() || verticeRectangle.get(entry.getKey()).size() == 1) {
                    entry.setValue(0);
                    change = true;
                    System.out.println("Valor de entry.getKey(): "+entry.getKey()+" passou a ser 0");
                }
                System.out.println("----------------------------");
            }
        }
        return change;    
    }

    private int getVertice(HashMap<Integer, Integer> domain) {
        int max = 0;
        int vertice = 0;
        for(Map.Entry<Integer, ArrayList<Integer>> entry : verticeRectangle.entrySet()) {
            if(domain.get(entry.getKey()) != null) continue;
            if(entry.getValue().size() == 3) {
                return entry.getKey();
            }
            if(entry.getValue().size() > max) {
                vertice = entry.getKey();
                max = entry.getValue().size();
            }
        }
        return vertice;
    }

    private void removeRectangles(ArrayList<Integer> rectanglesToGuard) {
		for (int i = 1; i <= numberOfRectanglesToGuard; i++) {
			if (!rectanglesToGuard.contains(i)) {
				for (Map.Entry<Integer, ArrayList<Integer>> entry : verticeRectangle.entrySet()) {
					if (entry.getValue().contains(i)) {
						entry.getValue().remove(entry.getValue().indexOf(i));
					}
                }
            }
		}
	}
}

