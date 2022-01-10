package handwriting.learners;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import handwriting.core.Drawing;
import handwriting.core.RecognizerAI;
import handwriting.core.SampleData;
import handwriting.gui.PixelUseVisualizer;
import handwriting.gui.PixelUser;
import handwriting.learners.decisiontree.DTNode;
import handwriting.learners.decisiontree.DTSampleData;
import handwriting.learners.decisiontree.DTTrainer;
import handwriting.learners.decisiontree.PixelUsePattern;

import javax.swing.*;

public class RandomForest implements RecognizerAI, PixelUser {
	private ArrayList<DTNode> treeRoots;
	private int width, height, numTrees;
	private String visualLabel;
	
	public RandomForest() {
		this(30);
	}
	
	public RandomForest(int numTrees) {
		this.numTrees = numTrees;		
	}

	@Override
	public void train(SampleData data, ArrayBlockingQueue<Double> progress) throws InterruptedException {
		DTSampleData dtData = new DTSampleData(data);
		this.treeRoots = new ArrayList<>();
		this.width = data.getDrawingWidth();
		this.height = data.getDrawingHeight();
		for (int i = 0; i < numTrees; i++) {
			DTTrainer trainer = new DTTrainer(dtData.resample(), progress, true, i * 1.0 / numTrees, 1.0 / numTrees);
			this.treeRoots.add(trainer.train());
		}
	}

	@Override
	public String classify(Drawing d) {
		// TODO: Ask each tree root for its classifiation of the Drawing.
		//  Pick the plurality winner as the winner. Consider using a Histogram.
		return "Unknown";
	}

	@Override
	public JPanel getVisualization() {
		return new PixelUseVisualizer(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (DTNode root: treeRoots) {
			sb.append(root.toString());
		}
		return sb.toString();
	}

	@Override
	public Set<String> getLabels() {
		Set<String> result = new HashSet<>();
		for (DTNode root: treeRoots) {
			root.addAllLabels(result);
		}
		return result;
	}

	@Override
	public PixelUsePattern getPixelUse(String label) {
		PixelUsePattern pixels = new PixelUsePattern(this.width, this.height);
		for (DTNode root: treeRoots) {
			root.visualize(visualLabel, new ArrayList<>(), pixels);
		}
		return pixels;
	}
}
