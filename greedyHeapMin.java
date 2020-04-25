import java.lang.*;
import java.util.*;

/*Estrategia greedy para colocar os guardas nos vertices dos retangulos */

public class greedyHeapMin {
	public static void createHeapMin(HashMap<Integer,ArrayList<Pair>> map,
			int n_possibleRectangles, ArrayList<Integer> possibleRectangles,
			int totalRecs) {

		int currentRec;
		ArrayList<Pair> currentList;
		int[] dist = new int[totalRecs+1];
		for(int i=1; i<=totalRecs;i++) {
			dist[i] = 100000;
		}
		for(int i=1; i<=totalRecs; i++) {
			if(!possibleRectangles.contains(i)) {
				map.remove(i);
			} else {
				currentList = map.get(i);
				dist[i] = currentList.size();
			}
		}

		Heapmin h = new Heapmin(dist,totalRecs);
		ArrayList<Integer> haveGuards = new ArrayList<Integer>();
		Pair pair;
		Pair pairMax = null;
		int val = 0;
		int max = 0;
		ArrayList<Integer> guardedRecs = new ArrayList<Integer>();
		ArrayList<Integer> maxGuardedRecs = new ArrayList<Integer>();
		ArrayList<Pair> guardedPairs = new ArrayList<>();

		while(!h.isEmpty()) {

			currentRec = h.extractMin();
			if(dist[currentRec] == 100000) break;
			currentList = map.get(currentRec);
			if(currentList == null) continue;

			for(int i=0;i<currentList.size();i++) {
				pair = currentList.get(i);
				val = 0;

				for(int j=1; j<=totalRecs;j++) {
					if(map.get(j) == null) {
						continue;
					} else {
						if(map.get(j).contains(pair)) {
							val++;
							guardedRecs.add(j);
						}
					}
				}

				if(val > max) {
					max = val;
					maxGuardedRecs = new ArrayList<>(guardedRecs);
					guardedRecs.clear();
					pairMax = pair;
				}

				if(max == 3) {
					break;
				} else {
					val = 0;
					guardedRecs.clear();
				}

			}
			max = 0;
			if(pairMax != null) guardedPairs.add(pairMax);

			for(int j=0;j<maxGuardedRecs.size();j++) {
				haveGuards.add(maxGuardedRecs.get(j));
			    map.remove(maxGuardedRecs.get(j));
			}
			System.out.println("All rects guarded: "+haveGuards);
			System.out.println(guardedPairs);
		}

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

		createHeapMin(map,n_possibleRectangles,possibleRectangles,n_rectangles);
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