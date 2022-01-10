package handwriting.learners.decisiontree;

import java.util.ArrayList;
import java.util.Set;

import handwriting.core.Drawing;

public class DTInterior implements DTNode {
	private DTNode left, right;
	private int x, y;
	
	public DTInterior(int x, int y, DTNode left, DTNode right) {
		this.left = left;
		this.right = right;
		this.x = x;
		this.y = y;
	}

	@Override
	public String classify(Drawing d) {
		// TODO: If the targeted pixel is set, ask the left subtree.
		//       Otherwise, ask the right subtree.
		return "Unknown";
	}

	@Override
	public void addAllLabels(Set<String> labels) {
		left.addAllLabels(labels);
		right.addAllLabels(labels);
	}

	@Override
	public void visualize(String label, ArrayList<PixelData> pixels, PixelUsePattern pattern) {
		ArrayList<PixelData> leftData = new ArrayList<>(pixels);
		leftData.add(new PixelData(PixelUse.ON, x, y));
		left.visualize(label, leftData, pattern);
		
		ArrayList<PixelData> rightData = new ArrayList<>(pixels);
		rightData.add(new PixelData(PixelUse.OFF, x, y));
		right.visualize(label, rightData, pattern);
	}

	@Override
	public String toStringHelp(int numTabs) {
		StringBuilder sb = new StringBuilder();
		sb.append(tabs(numTabs));
		sb.append("DTInterior: (" + x + ", " + y + ")\n");
		sb.append(left.toStringHelp(numTabs + 1));
		sb.append(right.toStringHelp(numTabs + 1));
		return sb.toString();
	}

	@Override
	public String toString() {
		return toStringHelp(0);
	}

	public boolean equals(Object other) {
		return this.toString().equals(other.toString());
	}
}
