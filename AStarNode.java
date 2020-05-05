public class AStarNode implements Comparable<AStarNode> {
    AStarNode parent;
    Integer vertice;
    int h;

    AStarNode(Integer vertice, int h) {
        this.vertice = vertice;
        this.h = h;
        this.parent = null;
    }

    public void setParent(AStarNode parent) {
        this.parent = parent;
    }

    public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;

        AStarNode node = (AStarNode) o;

        System.out.println("this.vertice: " + this.vertice + " node.vertice: " + node.vertice + " = " + (this.vertice == node.vertice));

		// return this.vertice == node.vertice && this.h == node.h && this.parent == node.parent;
		return this.vertice == node.vertice;
    }

    public int compareTo(AStarNode node) {
		if (node.vertice < this.vertice) {
			return 1;
		}
		if (node.vertice > this.vertice) {
			return -1;
		}
		return 0;
    }
    
    public String toString() {
        return "[Node] vertice: " + this.vertice + "| h: " + this.h;
    }
}