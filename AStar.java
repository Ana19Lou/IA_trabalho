import java.util.*;

/*Estrategia A* para colocar os guardas nos vertices dos retangulos */

public class AStar{

	public static void createAStar(ArrayList<Integer> possibleRectangles,HashMap<Integer, ArrayList<Integer>> map,int n_rectangles, HashMap<Integer, ArrayList<Integer>> mapRec) {
		removeRectangles(n_rectangles, possibleRectangles, map, mapRec);
		// for(Integer rectangle: possibleRectangles) {
		// 	if(possibleRectangles.contains(rectangle)) {
		// 		removeNonCandidateVertices(rectangle, map, mapRec);
		// 	}
		// }
		// System.out.println("Final HashMap: "+ map);
		AStarSolver aStarSolver = new AStarSolver(possibleRectangles.size(), possibleRectangles, map, mapRec);
		System.out.println(aStarSolver.path);
	}

	// private static void removeNonCandidateVertices(int rectangle, HashMap<Integer, ArrayList<Integer>> verticeRectangle, HashMap<Integer, ArrayList<Integer>> rectangleVertice) {
	// 	ArrayList<Integer> vertices = new ArrayList<>();
	// 	HashMap<Integer, ArrayList<Integer>> solution = new HashMap<>();
	// 	for (Integer vertice : rectangleVertice.get(rectangle)) {
	// 		solution.put(vertice, verticeRectangle.get(vertice));
	// 		vertices.add(vertice);
	// 	}
	// 	ArrayList<Integer> tempVertices = new ArrayList<>(vertices);
	// 	ArrayList<Integer> removedVertices = new ArrayList<>();
	// 	for(Integer vertice1: vertices) {
	// 		if(tempVertices.size() == 1) break;
	// 		for(Integer vertice2: vertices) {
	// 			if(!verticeRectangle.containsKey(vertice1) || !verticeRectangle.containsKey(vertice2)) break;
	// 			ArrayList<Integer> tempvertices1 = new ArrayList<>(solution.get(vertice1));
    //             ArrayList<Integer> tempvertices2 = new ArrayList<>(solution.get(vertice2));
    //             if(vertice1 == vertice2) break;
    //             if(tempVertices.contains(vertice1) && tempVertices.contains(vertice2)) {
    //                 if(tempvertices1.size() <= tempvertices2.size()) {
    //                     tempvertices1.retainAll(tempvertices2);
    //                     if(tempvertices1.size() == solution.get(vertice1).size()){
    //                         if(tempVertices.contains(vertice1)) {
	// 							tempVertices.remove(vertice1);
	// 							removedVertices.add(vertice1);
    //                         }
    //                     }
    //                 }
    //             }
	// 		}
	// 	}

	// 	for(Integer vertice: removedVertices) {
	// 		verticeRectangle.remove(vertice);
	// 		System.out.println("Removeu o vertice: "+vertice);
	// 	}
	// }

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
        createAStar(possibleRectangles,map,n_rectangles,mapRec);
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