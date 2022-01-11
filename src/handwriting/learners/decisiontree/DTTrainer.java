package handwriting.learners.decisiontree;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import handwriting.core.SampleData;
import learning.Duple;

public class DTTrainer {
	private ArrayBlockingQueue<Double> progress;
	private DTSampleData baseData;
	private double currentProgress, tick;
	private boolean restrictFeatures;
	
	public DTTrainer(SampleData data, ArrayBlockingQueue<Double> progress, boolean restrictFeatures, double startProgress, double tickPortion) throws InterruptedException {
		baseData = new DTSampleData(data);
		this.progress = progress;
		this.currentProgress = startProgress;
		progress.put(currentProgress);
		this.tick = tickPortion * 1.0 / data.numDrawings();	
		this.restrictFeatures = restrictFeatures;
	}
	
	public DTTrainer(SampleData data, ArrayBlockingQueue<Double> progress) throws InterruptedException {
		this(data, progress, false, 0, 1);
	}
	
	public static ArrayList<Duple<Integer,Integer>> allFeatures(SampleData data) {
		ArrayList<Duple<Integer,Integer>> result = new ArrayList<>();
		for (int x = 0; x < data.getDrawingWidth(); x++) {
			for (int y = 0; y < data.getDrawingHeight(); y++) {
				result.add(new Duple<>(x, y));
			}
		}
		return result;
	}

	// TODO: Call allFeatures() to get the feature list. Then shuffle the list, retaining
    //  only targetNumber features.
	public static ArrayList<Duple<Integer,Integer>> reducedFeatures(SampleData data, int targetNumber) {
        return null;
    }
	
	public DTNode train() throws InterruptedException {
		return train(baseData);
	}

	public static double gain(DTSampleData parent, DTSampleData child1, DTSampleData child2) {
	    // TODO: Calculate the gain of the split. Add the gini values for the children.
        //  Subtract that sum from the gini value for the parent.
        return 0;
    }
	
	private DTNode train(DTSampleData data) throws InterruptedException {
		// TODO: Implement the decision tree learning algorithm
		if (data.numLabels() == 1) {
			currentProgress += tick * data.numDrawings();
			progress.put(currentProgress);
			// TODO: Return a leaf node
			return null;
		} else {
			// TODO: Return an interior node. Call allFeatures() to get a complete list of coordinates.
			//  If restrictFeatures is false, consider every pixel when splitting.
			//  If restrictFeatures is true, pick sqrt(# features) of the possible
			//  pixels as candidates for the split.
            return null;
		}		
	}
}
