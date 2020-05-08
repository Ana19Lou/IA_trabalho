import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class BFSSolver {
    ArrayList<ArrayList<Integer>> allPaths;

    Integer numberOfRectanglesToGuard;

    HashMap<Integer, ArrayList<Integer>> rectangleVertice;
    HashMap<Integer, ArrayList<Integer>> verticeRectangle;

    BFSSolver(int nRec, ArrayList<Integer> rectanglesToGuard, HashMap<Integer, ArrayList<Integer>> rectangleVertice, HashMap<Integer, ArrayList<Integer>> verticeRectangle) {
        this.allPaths = new ArrayList<>();

        this.rectangleVertice = rectangleVertice;
        this.verticeRectangle = verticeRectangle;

        this.numberOfRectanglesToGuard = nRec;

        this.solve(nRec, rectanglesToGuard);
    }

    public void solve(Integer numberOfRectanglesToGuard, ArrayList<Integer> rectanglesToGuard) {
        for (Integer rectangle : rectangleVertice.keySet()) {
            for (Integer vertice : rectangleVertice.get(rectangle)) {
                LinkedList<Integer> queue = new LinkedList<Integer>(); 
                queue.add(vertice);
                ArrayList<Integer> relativeRectanglesToGuard = new ArrayList<>(rectanglesToGuard);
                ArrayList<Integer> path = new ArrayList<>();
                ArrayList<Integer> visit = new ArrayList<>();
                while(true) {
                    int current = queue.pop();
                    visit.add(current);
                    //System.out.println(current);
                    ArrayList<Integer> relativeRectanglesToGuardCopy = new ArrayList<>(relativeRectanglesToGuard);
                    for(Integer rec : verticeRectangle.get(current)) {
                        if(relativeRectanglesToGuardCopy.contains(rec)) {
                            relativeRectanglesToGuardCopy.remove(rec);
                        }
                    }
                    if (relativeRectanglesToGuardCopy.size() < relativeRectanglesToGuard.size()) {
                        path.add(current);
                        relativeRectanglesToGuard = relativeRectanglesToGuardCopy;
                    }
                    if(relativeRectanglesToGuard.size() == 0) {
                        allPaths.add(path);
                        System.out.println(path);
                        break;
                    } else {
                        for(Integer vert: verticeRectangle.keySet()) {
                            if(!visit.contains(vert) || !queue.contains(vert)) {
                                queue.add(vert);
                            }
                        }
                    }
                }
            }
        }

        // def bestFirst(self):
        // closed = list()
        // leaves = Queue()
        // leaves.put(self.start)
        // while True:
        //     if leaves.empty():
        //         return None
        //     actual = leaves.get()
        //     if actual.goalState():
        //         return actual
        //     elif actual.state.puzzle not in closed:
        //         closed.append(actual.state.puzzle)
        //         succ = actual.succ()
        //         while not succ.empty():
        //             leaves.put(succ.get())
        // for (Integer rectangle : lowerVertices(rectanglesToGuard)) {         
        //     for (Integer vertice : candidateVertices(rectangle)) {
        //         boolean visited[] = new boolean[numberOfRectanglesToGuard];
        //         LinkedList<Integer> queue = new LinkedList<Integer>(); 
        //         visited[rectangle] = true;
        //         queue.add(vertice); 
        //         while(queue.size() != 0) {
        //             int s = queue.pop();
        //             for(Integer rec : verticeRectangle.get(vertice)) {
        //                 visited[rec] = true;
        //             }
        //         }
        //     }
        // }
        // if (guardedRectangles.size() == this.numberOfRectanglesToGuard) {
        //     allPaths.add(path);
        // } else {
        //     for (Integer rectangle : lowerVertices(rectanglesToGuard)) {
        //         for (Integer vertice : candidateVertices(rectangle)) {
        //             if (!path.contains(vertice)) {
        //                 ArrayList<Integer> relativeBestPath = new ArrayList<>(path);
        //                 ArrayList<Integer> relativeGuardedRectangles = new ArrayList<>(guardedRectangles);
        //                 ArrayList<Integer> relativeRectanglesToGuard = new ArrayList<>(rectanglesToGuard);

        //                 relativeBestPath.add(vertice);

        //                 // Rectangles
        //                 for (Integer r : this.verticeRectangle.get(vertice)) {
        //                     int index = relativeRectanglesToGuard.indexOf(r);
        //                     if (index >= 0) {
        //                         relativeRectanglesToGuard.remove(index);
        //                     }
        //                     if (!relativeGuardedRectangles.contains(r)) {
        //                         relativeGuardedRectangles.add(r);
        //                     }
        //                 }
        //                 this.solve(relativeBestPath, relativeGuardedRectangles, relativeRectanglesToGuard);
        //             }
        //         }
        //     }
        // }
    }

        // // prints BFS traversal from a given source s 
        // void BFS(int s) 
        // { 
        //     // Mark all the vertices as not visited(By default 
        //     // set as false) 
        //     boolean visited[] = new boolean[V]; 
      
        //     // Create a queue for BFS 
        //     LinkedList<Integer> queue = new LinkedList<Integer>(); 
      
        //     // Mark the current node as visited and enqueue it 
        //     visited[s]=true; 
        //     queue.add(s); 
      
        //     while (queue.size() != 0) 
        //     { 
        //         // Dequeue a vertex from queue and print it 
        //         s = queue.poll(); 
        //         System.out.print(s+" "); 
      
        //         // Get all adjacent vertices of the dequeued vertex s 
        //         // If a adjacent has not been visited, then mark it 
        //         // visited and enqueue it 
        //         Iterator<Integer> i = adj[s].listIterator(); 
        //         while (i.hasNext()) 
        //         { 
        //             int n = i.next(); 
        //             if (!visited[n]) 
        //             { 
        //                 visited[n] = true; 
        //                 queue.add(n); 
        //             } 
        //         } 
        //     } 
        // } 

    // private ArrayList<Integer> lowerVertices(ArrayList<Integer> rectangles) {
	// 	int min = Integer.MAX_VALUE;
	// 	ArrayList<Integer> result = new ArrayList<>();
	// 	for (Integer rec : rectangles) {
	// 		if (this.rectangleVertice.get(rec).size() <= min) {
	// 			if(this.rectangleVertice.get(rec).size() < min) {
	// 				min = this.rectangleVertice.get(rec).size();
	// 				result.clear();
	// 			}
	// 			result.add(rec);
	// 		} 
    //     }
	// 	return result;
    // }
    
    private ArrayList<Integer> candidateVertices(Integer rectangle) {
		ArrayList<Integer> vertices = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> solution = new HashMap<>();
        
		for (Integer vertice : this.rectangleVertice.get(rectangle)) {
			solution.put(vertice, this.verticeRectangle.get(vertice));
			vertices.add(vertice);
        }

        ArrayList<Integer> finalVertices = new ArrayList<>(vertices);
        for(Integer vertice1: vertices) {
            if(finalVertices.size() == 1) break;
            for(Integer vertice2: vertices) {
                ArrayList<Integer> tempvertices1 = new ArrayList<>(solution.get(vertice1));
                ArrayList<Integer> tempvertices2 = new ArrayList<>(solution.get(vertice2));
                if(vertice1 == vertice2) break;
                if(finalVertices.contains(vertice1) && finalVertices.contains(vertice2)) {
                    if(tempvertices1.size() <= tempvertices2.size()) {
                        tempvertices1.retainAll(tempvertices2);
                        if(tempvertices1.size() == solution.get(vertice1).size()){
                            if(finalVertices.contains(vertice1)) {
                                finalVertices.remove(vertice1);
                            }
                        }
                    }
                }
            }
        }
		return finalVertices;
	}
}