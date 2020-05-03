import java.util.*;

/*Estrategia greedy para colocar os guardas nos vertices dos retangulos */

public class greedyVertices {

	private static void removeRectangles(int n_rectangles, ArrayList<Integer> possibleRectangles, HashMap<Integer, ArrayList<Integer>> map) {
		for (int i = 1; i <= n_rectangles; i++) {
			if (!possibleRectangles.contains(i)) {
				for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
					if (entry.getValue().contains(i)) {
						entry.getValue().remove(entry.getValue().indexOf(i));
					}
				}

			}
		}
	}

	private static void createMapVertice(ArrayList<Integer> possibleRectangles, HashMap<Integer, ArrayList<Integer>> map, int n_rectangles) {
		removeRectangles(n_rectangles, possibleRectangles, map);
		int max = 0;
		int maxVertice = 0;
		ArrayList<Integer> guardedVertices = new ArrayList<>();
		ArrayList<Integer> rectanglesMax = new ArrayList<>();
		while(possibleRectangles.size() != 0) {
			for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
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
				removeRectangles(n_rectangles, possibleRectangles, map);
			}
		}
		System.out.println("Guards are in vertices: "+guardedVertices);
	}

	public static void createInstance(int n_rectangles, Scanner in) {
		int rectangle, x, y, vertices, vert;
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();

		for (int i = 0; i < n_rectangles; i++) {
			rectangle = in.nextInt();
			vertices = in.nextInt();

			for (int j = 0; j < vertices; j++) {
				x = in.nextInt();
				y = in.nextInt();
				vert = x * n_rectangles + y;
				if (map.containsKey(vert)) {
					map.get(vert).add(rectangle);
				} else {
					ArrayList<Integer> newRec = new ArrayList<>();
					newRec.add(rectangle);
					map.put(vert, newRec);
				}
			}
		}
		int n_possibleRectangles = in.nextInt();
		ArrayList<Integer> possibleRectangles = new ArrayList<>();
		for (int i = 0; i < n_possibleRectangles; i++) {
			possibleRectangles.add(in.nextInt());
		}
		createMapVertice(possibleRectangles, map, n_rectangles);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n_instances = in.nextInt();

		for (int i = 0; i < n_instances; i++) {
			int n_rectangles = in.nextInt();
			createInstance(n_rectangles, in);
		}

	}
}