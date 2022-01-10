package handwriting.learners.decisiontree;

import java.awt.*;

public class PixelUsePattern {
	private PixelUse[][] pattern;
	
	public PixelUsePattern(int width, int height) {
		pattern = new PixelUse[width][height];
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				pattern[x][y] = PixelUse.IGNORED;
			}
		}
	}
	
	public int getWidth() {return pattern.length;}
	public int getHeight() {return pattern[0].length;}
	
	public void update(PixelData update) {
		pattern[update.getX()][update.getY()] = pattern[update.getX()][update.getY()].updated(update.getUse());
	}

	public void draw(Graphics g, int gWidth, int gHeight) {
		int width = pattern.length;
		for (int x = 0; x < width; ++x) {
			int height = pattern[x].length;
			for (int y = 0; y < height; ++y) {
				g.setColor(pattern[x][y].getColor());
				g.fillRect(x * gWidth / width, y * gHeight / height, (x+1) * gWidth / width, (y+1) * gHeight / height);
			}
		}
	}
}
