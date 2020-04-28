import java.util.*;

/*Estrategia greedy para colocar os guardas nos vertices dos retangulos */

public class greedyVertices {

	private static void removeRectangles(int n_rectangles, ArrayList<Integer> possibleRectangles, HashMap<Integer, ArrayList<Integer>> map) {
		for (int i = 1; i <= n_rectangles; i++) {
			if (!possibleRectangles.contains(i)) {
				for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
					System.out.println(entry.getKey() + " = " + entry.getValue());
					if (entry.getValue().contains(i)) {
						entry.getValue().remove(entry.getValue().indexOf(i));
						System.out.println("After removing: " + entry.getKey() + " = " + entry.getValue());
					}
				}

			}
		}
	}

	public static HashMap<Integer, ArrayList<Integer>> sortByValue(HashMap<Integer, ArrayList<Integer>> map) {
		// Create a list from elements of HashMap 
        List<Map.Entry<Integer, ArrayList<Integer>>> list = new LinkedList<Map.Entry<Integer, ArrayList<Integer>> >(map.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<Integer, ArrayList<Integer>> >() { 
            public int compare(Map.Entry<Integer, ArrayList<Integer>> o1, Map.Entry<Integer, ArrayList<Integer>> o2) { 
                return o2.getValue().size() - o1.getValue().size(); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<Integer, ArrayList<Integer>> temp = new LinkedHashMap<Integer, ArrayList<Integer>>(); 
        for (Map.Entry<Integer, ArrayList<Integer>> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
	}

	private static void createMapVertice(ArrayList<Integer> possibleRectangles, HashMap<Integer, ArrayList<Integer>> map, int n_rectangles) {
		removeRectangles(n_rectangles, possibleRectangles, map);
		HashMap<Integer, ArrayList<Integer>> sortedMap = sortByValue(map);
		System.out.println(sortedMap.entrySet().iterator().next().getValue());
		// while(possibleRectangles.size() != 0) {

		// }
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
		System.out.println(map);
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