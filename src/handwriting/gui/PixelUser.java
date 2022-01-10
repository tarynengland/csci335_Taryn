package handwriting.gui;

import handwriting.learners.decisiontree.PixelUsePattern;

import java.util.Set;

public interface PixelUser {
	Set<String> getLabels();
	PixelUsePattern getPixelUse(String label);
}
