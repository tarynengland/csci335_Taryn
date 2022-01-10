package handwriting.learners.decisiontree;

import java.util.ArrayList;
import java.util.Set;

import handwriting.core.Drawing;

public class DTLeaf implements DTNode {
	private String label;
	
	public DTLeaf(String label) {this.label = label;}

	@Override
	public String classify(Drawing d) {
		return label;
	}

	@Override
	public void addAllLabels(Set<String> labels) {
		labels.add(label);
	}

	public String getLabel() {return label;}

	@Override
	public void visualize(String label, ArrayList<PixelData> pixels, PixelUsePattern pattern) {
		if (label.equals(this.label)) {
			for (PixelData pixel: pixels) {
				pattern.update(pixel);
			}
		}
	}

	@Override
	public String toStringHelp(int numTabs) {
		return tabs(numTabs) +  "DTLeaf: " + label + "\n";
	}

	public String toString() {
		return toStringHelp(0);
	}

	public boolean equals(Object other) {
		return this.toString().equals(other.toString());
	}
}
