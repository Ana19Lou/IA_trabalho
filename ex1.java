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
}

/*    	Implementacao de fila de prioridade (por heap de maximo)
  		Credito: A.P.Tomas
 */
class Qnode {
    int vert;
    int vertkey;
    
    Qnode(int v, int key) {
	vert = v;
	vertkey = key;
    }
}

class Heapmax {
    private static int posinvalida = 0;
    int sizeMax,size;
    
    Qnode[] a;
    int[] pos_a;

    Heapmax(int vec[], int n) {
	a = new Qnode[n + 1];
	pos_a = new int[n + 1];
	sizeMax = n;
	size = n;
	for (int i = 1; i <= n; i++) {
	    a[i] = new Qnode(i,vec[i]);
	    pos_a[i] = i;
	}

	for (int i = n/2; i >= 1; i--)
	    heapify(i);
    }

    boolean isEmpty() {
	if (size == 0) return true;
	return false;
    }

    int extractMax() {
	int vertv = a[1].vert;
	swap(1,size);
	pos_a[vertv] = posinvalida;  // assinala vertv como removido
	size--;
	heapify(1);
	return vertv;
    }

    void increaseKey(int vertv, int newkey) {

	int i = pos_a[vertv];
	a[i].vertkey = newkey;

	while (i > 1 && compare(i, parent(i)) > 0) { 
	    swap(i, parent(i));
	    i = parent(i);
	}
    }


    void insert(int vertv, int key)
    { 
	if (sizeMax == size)
	    new Error("Heap is full\n");
	
	size++;
	a[size].vert = vertv;
	pos_a[vertv] = size;   // supondo 1 <= vertv <= n
	increaseKey(vertv,key);   // aumenta a chave e corrige posicao se necessario
    }

    void write_heap(){
	System.out.printf("Max size: %d\n",sizeMax);
	System.out.printf("Current size: %d\n",size);
	System.out.printf("(Vert,Key)\n---------\n");
	for(int i=1; i <= size; i++)
	    System.out.printf("(%d,%d)\n",a[i].vert,a[i].vertkey);
	
	System.out.printf("-------\n(Vert,PosVert)\n---------\n");

	for(int i=1; i <= sizeMax; i++)
	    if (pos_valida(pos_a[i]))
		System.out.printf("(%d,%d)\n",i,pos_a[i]);
    }
    
    private int parent(int i){
	return i/2;
    }
    private int left(int i){
	return 2*i;
    }
    private int right(int i){
	return 2*i+1;
    }

    private int compare(int i, int j) {
	if (a[i].vertkey < a[j].vertkey)
	    return -1;
	if (a[i].vertkey == a[j].vertkey)
	    return 0;
	return 1;
    }

  
    private void heapify(int i) {
	int l, r, largest;

	l = left(i);
	if (l > size) l = i;

	r = right(i);
	if (r > size) r = i;

	largest = i;
	if (compare(l,largest) > 0)
	    largest = l;
	if (compare(r,largest) > 0)
	    largest = r;
	
	if (i != largest) {
	    swap(i, largest);
	    heapify(largest);
	}
	
    }

    private void swap(int i, int j) {
	Qnode aux;
	pos_a[a[i].vert] = j;
	pos_a[a[j].vert] = i;
	aux = a[i];
	a[i] = a[j];
	a[j] = aux;
    }
    
    private boolean pos_valida(int i) {
	return (i >= 1 && i <= size);
    }
}

/*Estrategia greedy para colocar os guardas nos vertices dos retangulos */

public class ex1 {
	public static void createHeapMax(HashMap<Integer,ArrayList<Pair>> map,
			int n_possibleRectangles, ArrayList<Integer> possibleRectangles,
			int totalRecs) {

		int currentRec;
		ArrayList<Pair> currentList;
		int[] dist = new int[totalRecs+1];
		for(int i=1; i<=totalRecs;i++) {
			dist[i] = 0;
		}
		for(int i=0; i<n_possibleRectangles; i++) {
			currentRec = possibleRectangles.get(i);
			currentList = map.get(currentRec);
			dist[i] = currentList.size();
		}
		Heapmax h = new Heapmax(dist,n_possibleRectangles);
		while(!h.isEmpty()) {
			currentRec = h.extractMax();
			//Agora falta ver qual destes vertices ocorre mais vezes nos rectandulos a considerar
			//Escolher o vertice e retirar de todos os rectangulos
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