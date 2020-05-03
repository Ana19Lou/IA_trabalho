/* 
	Classe par para cada vertice
*/

class Pair implements Comparable<Pair> {
	public final int x;
	public final int y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int toFlatPoint(int nRectangles) {
		return this.x * nRectangles + this.y;
	}

	public static Pair toPoint(int flatPoint, int nRectangles) {
		int x = flatPoint / nRectangles;
		int y = flatPoint % nRectangles;
		return new Pair(x, y);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;

		Pair pair = (Pair) o;

		return this.x == pair.x && this.y == pair.y;
	}

	public int compareTo(Pair p) {

		if (p.x < this.x) {
			return 1;
		}
		if (p.x > this.x) {
			return -1;
		}
		if (p.y < this.y) {
			return 1;
		}
		if (p.y > this.y) {
			return -1;
		}
		return 0;
	}
}
