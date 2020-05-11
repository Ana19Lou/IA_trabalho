import java.util.ArrayList;
import java.util.TreeSet;

public class BFSState {
    private TreeSet<Integer> visitedVertices;
    private ArrayList<Integer> unguardedRectangles;
    private ArrayList<Integer> path;

    BFSState(Integer initialVertice, ArrayList<Integer> rectanglesToGuard) {
        this.visitedVertices = new TreeSet<>();
        this.unguardedRectangles = new ArrayList<>(rectanglesToGuard);
        this.path = new ArrayList<>();

        this.path.add(initialVertice);
        this.visitedVertices.add(initialVertice);
    }

    BFSState(BFSState state) {
        this.visitedVertices = new TreeSet<>(state.visitedVertices);
        this.unguardedRectangles = new ArrayList<>(state.unguardedRectangles);
        this.path = new ArrayList<>(state.path);
    }

    ArrayList<Integer> getPath() {
        return this.path;
    }

    ArrayList<Integer> getUnguardedRectangles() {
        return this.unguardedRectangles;
    }

    Integer getCurrentVertice() {
        return this.path.get(this.path.size() - 1);
    }

    void guardRectangles(ArrayList<Integer> rectangles) {
        this.unguardedRectangles.removeAll(rectangles);
    }

    void visitVertice(Integer vertice) {
        this.visitedVertices.add(vertice);
    }

    void addVerticeToPath(Integer vertice) {
        this.path.add(vertice);
    }

    boolean allRectanglesGuarded() {
        return this.unguardedRectangles.size() == 0;
    }

    boolean hasVerticeBeenVisited(Integer vertice) {
        return this.visitedVertices.contains(vertice) || this.path.contains(vertice);
    }

    boolean hasRectangleBeenGuarded(Integer rectangle) {
        return !this.unguardedRectangles.contains(rectangle);
    }
}