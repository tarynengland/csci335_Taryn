package handwriting.learners.decisiontree;

import java.util.ArrayList;
import java.util.Set;

import handwriting.core.Drawing;

public interface DTNode {
	String classify(Drawing d);
	void addAllLabels(Set<String> labels);
	void visualize(String label, ArrayList<PixelData> pixels, PixelUsePattern pattern);
	String toStringHelp(int numTabs);

	default String tabs(int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append("\t");
		}
		return sb.toString();
	}
}
