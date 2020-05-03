import java.lang.*;
import java.util.*;

/*Estrategia A* para colocar os guardas nos vertices dos retangulos */

public class AStarImplementation {

	public static void AStar(HashMap<Integer, ArrayList<Pair>> map,int n_possibleRectangles
		ArrayList<Integer> possibleRectangles,int n_rectangles) {

	}

	public static void createInstance(int n_rectangles, Scanner in) {
		int rectangle, x, y,vertices;
		HashMap<Integer, ArrayList<Pair>> map = new HashMap<>(); 

		for(int i=0; i<n_rectangles;i++) {
			ArrayList<Pair> pairs = new ArrayList<>();
			rectangle = in.nextInt();
			vertices = in.nextInt();

			for(int j=0; j<vertices;j++) {
				x = in.nextInt();
				y = in.nextInt();
				Pair p = new Pair(x,y);
				pairs.add(p);
			}

			map.put(rectangle,pairs);
		}

		//Ate aqui é igual ao input da ficha 1, mas agora no final acrescenta-se
		//uma linha que diz quantos e quais retangulos a analisar
		int n_possibleRectangles = in.nextInt();
		ArrayList<Integer> possibleRectangles = new ArrayList<>();

		for(int i=0; i<n_possibleRectangles;i++) {
			possibleRectangles.add(in.nextInt());
		}

		createAStar(map,n_possibleRectangles,possibleRectangles,n_rectangles);
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