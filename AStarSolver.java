import java.util.*;

public class AStarSolver {
    Integer numberOfRectanglesToGuard;

    HashMap<Integer, ArrayList<Integer>> verticeRectangle;
    HashMap<Integer, ArrayList<Integer>> rectangleVertice;
    ArrayList<Integer> rectanglesToGuard;
    ArrayList<Integer> path;

    AStarSolver(int nRec,  ArrayList<Integer> rectanglesToGuard, HashMap<Integer, ArrayList<Integer>> verticeRectangle, HashMap<Integer, ArrayList<Integer>> rectangleVertice) {
        this.verticeRectangle = verticeRectangle;
        this.rectangleVertice = rectangleVertice;
        this.rectanglesToGuard = rectanglesToGuard;

        this.numberOfRectanglesToGuard = nRec;

        this.path = new ArrayList<>();

        this.solve(leastH(nRec));
    }

    public void solve(Integer startVertice) {
        TreeSet<AStarNode> openList = new TreeSet<>();
        TreeSet<AStarNode> closedList = new TreeSet<>();

        AStarNode start = new AStarNode(startVertice, getH(startVertice));
        openList.add(start);
        
        while(openList.size() != 0) {
            AStarNode current = leastHAStarNode(openList);
            this.path.add(current.vertice);
            System.out.println(current);
            
            rectanglesToGuard.removeAll(this.verticeRectangle.get(current.vertice));
            if (rectanglesToGuard.size() == 0) break;
            closedList.add(current);

            for (AStarNode neighbor : this.getNeighbors(current, closedList)) {
                if (!closedList.contains(neighbor)) {
                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    } else {
                        int openListNeighborH = Integer.MAX_VALUE;
                        AStarNode openListNeighbor = null;
                        for (AStarNode node : openList) {
                            if (node.vertice == neighbor.vertice) {
                                openListNeighborH = node.h;
                                openListNeighbor = node;
                            }
                        }
                        if (openListNeighbor != null && neighbor.h < openListNeighborH) {
                            openListNeighbor.h = neighbor.h;
                            openListNeighbor.parent = neighbor.parent;
                        }
                    }
                }
            }
        }
    }

    private AStarNode leastHAStarNode(TreeSet<AStarNode> openList) {
        int minH = Integer.MAX_VALUE;
        AStarNode minNode = openList.first();
        for (AStarNode node : openList) {
            if (node.h < minH) {
                minNode = node;
                minH = node.h;
            }
        }
        return minNode;
    }

    private TreeSet<AStarNode> getNeighbors(AStarNode node, TreeSet<AStarNode> closedList) {
        TreeSet<AStarNode> verticeNodes = new TreeSet<>();
        ArrayList<Integer> closedListVertices = this.getVerticesFromAStarNodeList(closedList);
        for (Integer rectangle : this.rectanglesToGuard) {
            ArrayList<Integer> allVertices = this.rectangleVertice.get(rectangle);
            for (Integer vertice : allVertices) {
                if (vertice != node.vertice && !closedListVertices.contains(vertice)) {
                    verticeNodes.add(new AStarNode(vertice, getH(vertice)));
                }
            }
        }
        return verticeNodes;
    }

    private ArrayList<Integer> getVerticesFromAStarNodeList(TreeSet<AStarNode> list) {
        ArrayList<Integer> vertices = new ArrayList<>();
        for (AStarNode node: list) {
            vertices.add(node.vertice);
        }
        return vertices;
    }

    public int getH(Integer vertice) {
        ArrayList<Integer> rectanglesLeft = new ArrayList<>(this.rectanglesToGuard);
        rectanglesLeft.removeAll(verticeRectangle.get(vertice));
        return rectanglesLeft.size();
    }

    private int leastH(int nRectanglesLeft) {
        int vertice = 0;
        int min = Integer.MAX_VALUE;
        for(Map.Entry<Integer, ArrayList<Integer>> entry : verticeRectangle.entrySet()) {
            if((nRectanglesLeft - entry.getValue().size()) < min) {
                min = nRectanglesLeft - entry.getValue().size();
                vertice = entry.getKey();
            }
        }
        return vertice;
    }
}