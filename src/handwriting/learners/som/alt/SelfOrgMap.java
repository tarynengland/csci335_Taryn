package handwriting.learners.som.alt;

import handwriting.core.*;
import handwriting.learners.som.alt.FloatDrawing;

public class SelfOrgMap {
	
	private FloatDrawing[][] grid;
	
	public SelfOrgMap(int gridSize, int iterations, SampleData data) {
		grid = new FloatDrawing[gridSize][gridSize];
		for (int x = 0; x < gridSize; ++x) {
			for (int y = 0; y < gridSize; ++y) {
				grid[x][y] = new FloatDrawing(data.getDrawingWidth(), data.getDrawingHeight());
			}
		}

        /* Write training code here */
	}
	
	public SelfOrgMapNeuron bestMatchFor(FloatDrawing target) {
		SelfOrgMapNeuron best = new SelfOrgMapNeuron(0, 0);
        /* Write code to identify best neuron here */
		return best;
	}
	
	public SelfOrgMapNeuron at(int x, int y) {
		return new SelfOrgMapNeuron(x, y);
	}
	
	public int getGridSide() {return grid.length;}
	
	public class SelfOrgMapNeuron {
		private int x, y;
		
		private SelfOrgMapNeuron(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {return x;}
		public int getY() {return y;}
		
		public double distanceTo(int x, int y) {
            /* Write the map's distance function here */
            return 0;
		}
		
		public FloatDrawing getIdealInput() {return grid[x][y];}
	}
}
