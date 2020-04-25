/* 
	Classe par para cada vertice
*/
	
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
