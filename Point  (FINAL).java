

public class Point implements Comparable	{
    private int x;
    private int y;

   public Point(int n, int m){
        x = n;
        y = m;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Object obj) {
    	Point p = (Point) obj;
        return (x == p.getX()) && (y == p.getY());
    }
    
    public String toString (){
    	return "(" + this.x + ", " + this.y + ")";
    }

	@Override
	public int compareTo(Object obj) {
		// TODO Auto-generated method stub
		Point p = (Point) obj;
        return (p.getX() + p.getY()) - (x+y);
	}


}