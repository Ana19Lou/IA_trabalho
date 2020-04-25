/**
 * Classe Par para cada vertice
 */

class Pair {
    private static int x;   
    private static int y;  

    public Pair(int x, int y) {
	this.x = x;
	this.y = y;
    }

    public String toString() {
	return "(" + x + ", " + y + ")";
    }

    public static int getX(){
	return x;
    }
    public static int getY(){
	return y;
    }
}
