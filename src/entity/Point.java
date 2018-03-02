package entity;

public class Point {

	private Integer x;
	private Integer y;

	public Point(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	public Integer getX() { return x; }

	public Integer getY() { return y; }
	
	public int getDistance(Point p) {
		return Math.abs(x - p.getX()) + Math.abs(y - p.getY());
	}
}
