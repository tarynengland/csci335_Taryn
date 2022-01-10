package handwriting.learners.som.alt;

import handwriting.core.Drawing;

public class FloatDrawing {
	private double[][] drawing;
	
	public FloatDrawing(int width, int height) {
		drawing = new double[width][height];
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				drawing[x][y] = Math.random();
			}
		}
	}
	
	public FloatDrawing(Drawing d) {
        /* Write code to construct a FloatDrawing from a Drawing */
	}
	
	public double distance(FloatDrawing that) {
        /* Write a distance function for FloatDrawings */
        return 0;
	}
	
	public void train(FloatDrawing that, double scale) {
        /* Write code to train this FloatDrawing */
	}
	
	public double at(int x, int y) {
		return drawing[x][y];
	}
	
	public int getWidth() {
		return drawing.length;
	}
	
	public int getHeight() {
		return drawing[0].length;
	}
}
