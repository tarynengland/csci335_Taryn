package handwriting.learners.decisiontree;

import handwriting.core.SampleData;
import learning.Duple;

public class DTSampleData extends SampleData {
	public DTSampleData() {super();}
	
	public DTSampleData(SampleData src) {
		for (int i = 0; i < src.numDrawings(); i++) {
			this.addDrawing(src.getLabelFor(i), src.getDrawing(i));
		}
	}

	// TODO: Generate a new DTSampleData object by sampling randomly with replacement.
	public DTSampleData resample() {
		return null;
	}

	// TODO: Calculate the portion p (0 <= p <= 1) of examples that
	//  are labeled with "label"
	public double getPortionFor(String label) {
		return 0.0;
	}
	
	public double getGini() {
		// TODO: Calculate the Gini coefficient:
		//  For each label, calculate its portion of the whole (pi)
		//  by callling getPortionFor().
		//  Gini coefficient is 1 - sum(for all i, pi^2)
		return 1.0;
	}
	
	public Duple<DTSampleData,DTSampleData> splitOn(int x, int y) {
		// TODO: 
		//  Returns a duple of two new DTSampleData sets.
		//  The first returned set should be everything from this set for which
		//  (x, y) is set. The second returned set should be everything else from
		//  this set.
		return null;
	}
}
