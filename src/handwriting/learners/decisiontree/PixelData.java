package handwriting.learners.decisiontree;

public class PixelData {
	private PixelUse use;
	private int x, y;
	
	public PixelData(PixelUse use, int x, int y) {
		this.use = use;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public PixelUse getUse() {return use;}

	public String toString() {
		return String.format("%s@(%d,%d)", use.toString(), x, y);
	}

	public boolean equals(Object other) {
		return this.toString().equals(other.toString());
	}
}
