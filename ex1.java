import java.lang.*;
import java.util.*;

/* Classe par para cada vertice*/
class Pair {
	public final int x;   
	public final int y;  

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;

		Pair pair = (Pair) o;

		return this.x == pair .x && this.y == pair.y;
	}
}


/*Estrategia greedy para colocar os guardas nos vertices dos retangulos */

public class greedyHeapMax implements Heapmax {
	public static void createHeapMax(HashMap<Integer,ArrayList<Pair>> map,
			int n_possibleRectangles, ArrayList<Integer> possibleRectangles,
			int totalRecs) {

		int currentRec;
		ArrayList<Pair> currentList;
		int[] dist = new int[totalRecs+1];
		for(int i=1; i<=totalRecs;i++) {
			dist[i] = 0;
		}
		for(int i=1; i<=totalRecs; i++) {
			if(!possibleRectangles.contains(i)) {
				map.remove(i);
				//System.out.println("Nao e um rectangle na lista");
				//System.out.println("A remover :"+map.remove(i));
			} else {
				currentList = map.get(i);
				dist[i] = currentList.size();
				//System.out.println("O tamanho da lista de "+i+ " e: "+dist[i]);
			}
		}
		Heapmax h = new Heapmax(dist,totalRecs);
		ArrayList<Integer> haveGuards = new ArrayList<Integer>();
		Pair pair;
		int val = 0;
		int max = 0;
		ArrayList<Integer> guardedRecs = new ArrayList<Integer>();
		ArrayList<Integer> maxGuardedRecs = new ArrayList<Integer>();
		//A correr bem ate aqui
		while(!h.isEmpty()) {
			currentRec = h.extractMax();
			if(dist[currentRec] == 0) break;
			currentList = map.get(currentRec);
			if(currentList == null) break;
			//System.out.println(currentList);
			for(int i=0;i<currentList.size();i++) {
				pair = currentList.get(i);
				//System.out.println(pair);
				for(int j=1; j<=totalRecs;j++) {
					if(map.get(j) == null) {
						// System.out.println("Not valid in: "+j);
						// System.out.println("-------------------------------------");
						continue;
					} else {
						// System.out.println(map.get(j));
						// System.out.println("map.get("+j+").contains"+pair+"= "+map.get(j).contains(pair));
						// System.out.println("-------------------------------------");
						if(map.get(j).contains(pair)) {
							val++;
							guardedRecs.add(j);
							// System.out.println("Atual val: "+val);
							// System.out.println("GuardedRecs: "+guardedRecs);
						}
					}
				}
				if(val > max) {
					max = val;
					// maxGuardedRecs.clear();
					maxGuardedRecs = new ArrayList<>(guardedRecs);
					guardedRecs.clear();
					// System.out.println("Val: "+max);
					System.out.println("Recs: "+maxGuardedRecs);
					System.out.println("Recs222: "+guardedRecs);
				}
				if(max == 3) {
					System.out.println("Aqui");
					break;
				} else {
					val = 0;
					guardedRecs.clear();
				}
			}
			System.out.println("maxGuardedRecs.size(): "+maxGuardedRecs.size());
			for(int j=0;j<maxGuardedRecs.size();j++) {
				haveGuards.add(maxGuardedRecs.get(j));
			    map.remove(maxGuardedRecs.get(j));
			    System.out.println("All rects guarded: "+haveGuards);
			}
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

		createHeapMax(map,n_possibleRectangles,possibleRectangles,n_rectangles);
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