import java.util.*;

/*Estrategia BFS para colocar os guardas nos vertices dos retangulos */

public class BFS{

	public static void createBFS(ArrayList<Integer> possibleRectangles,HashMap<Integer, ArrayList<Integer>> map,int n_rectangles, HashMap<Integer, ArrayList<Integer>> mapRec) {
		removeRectangles(n_rectangles, possibleRectangles, map, mapRec);
        BFSSolver bfsSolver = new BFSSolver(possibleRectangles.size(), possibleRectangles, mapRec,map);
        ArrayList<Integer> bestPath = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for(ArrayList<Integer> path: bfsSolver.allPaths) {
            if(min > path.size()) {
                min = path.size();
                bestPath = path;
            }
        }
		// System.out.println(bestPath);
		System.out.println(bfsSolver.allPaths);
		
	}


	private static void removeRectangles(int n_rectangles, ArrayList<Integer> possibleRectangles, HashMap<Integer, ArrayList<Integer>> map, HashMap<Integer, ArrayList<Integer>> mapRec) {
		for (int i = 1; i <= n_rectangles; i++) {
			if (!possibleRectangles.contains(i)) {
				for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
					if (entry.getValue().contains(i)) {
						entry.getValue().remove(entry.getValue().indexOf(i));
					}
				}
				int toRemove = -1;
				for (Integer key : mapRec.keySet()) {
					if (key == i) {
						toRemove = key;
					}
				}
				if (toRemove > -1) mapRec.remove(toRemove);
			}
		}
	}

    public static void createInstance(int n_rectangles, Scanner in) {
		int rectangle, x, y,vertices, vert;
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>(); 
		HashMap<Integer, ArrayList<Integer>> mapRec = new HashMap<>(); 

		for(int i=0; i<n_rectangles;i++) {
			rectangle = in.nextInt();
            vertices = in.nextInt();


			ArrayList<Integer> recVertices = new ArrayList<>();
			for(int j=0; j<vertices;j++) {
				x = in.nextInt();
				y = in.nextInt();
				vert = x*n_rectangles + y;
				if(map.containsKey(vert)) {
                    map.get(vert).add(rectangle);
                } else {
                    ArrayList<Integer> newRec = new ArrayList<>();
                    newRec.add(rectangle);
					map.put(vert, newRec);
				}
				recVertices.add(vert);
			}
			mapRec.put(rectangle, recVertices);
        }
		int n_possibleRectangles = in.nextInt();
        ArrayList<Integer> possibleRectangles = new ArrayList<>();
		for(int i=0; i<n_possibleRectangles;i++) {
            possibleRectangles.add(in.nextInt());
        }
        createBFS(possibleRectangles,map,n_rectangles,mapRec);
	}

    public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n_instances = in.nextInt();

		for(int i=0; i<n_instances;i++) {
			int n_rectangles = in.nextInt();
			createInstance(n_rectangles,in);
		}

	}
}