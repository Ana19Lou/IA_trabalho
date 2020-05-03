import java.util.*;

public class AStar {
    public static void createInstance(int n_rectangles, Scanner in) {
        int rectangle, x, y, vertices, vert;

		HashMap<Integer, ArrayList<Integer>> verticeRectangles = new HashMap<>(); 
		HashMap<Integer, ArrayList<Integer>> rectangleVertices = new HashMap<>(); 

		for(int i=0; i<n_rectangles;i++) {
			rectangle = in.nextInt();
            vertices = in.nextInt();

			ArrayList<Integer> recVertices = new ArrayList<>();
			for(int j=0; j<vertices;j++) {
				x = in.nextInt();
				y = in.nextInt();
				vert = x*n_rectangles + y;
				if(verticeRectangles.containsKey(vert)) {
                    verticeRectangles.get(vert).add(rectangle);
                } else {
                    ArrayList<Integer> newRec = new ArrayList<>();
                    newRec.add(rectangle);
					verticeRectangles.put(vert, newRec);
				}
				recVertices.add(vert);
			}
			rectangleVertices.put(rectangle, recVertices);
        }

        int n_possibleRectangles = in.nextInt();
        ArrayList<Integer> possibleRectangles = new ArrayList<>();
		for(int i=0; i<n_possibleRectangles;i++) {
            possibleRectangles.add(in.nextInt());
        }
    }

    public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n_instances = in.nextInt();

		for(int i=0; i<n_instances;i++) {
			int n_rectangles = in.nextInt();
			createInstance(n_rectangles, in);
		}

	}
}