public class AStarNode {
    Integer parent;
    Integer rectangle;
    
    int guardedRectangles;

    AStarNode(Integer parent, Integer rectangle, int guardedRectangles) {
        this.parent = parent;
        this.rectangle = rectangle;
        this.guardedRectangles = guardedRectangles;
    }
}