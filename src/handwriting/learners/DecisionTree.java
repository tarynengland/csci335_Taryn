package handwriting.learners;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import handwriting.core.Drawing;
import handwriting.core.RecognizerAI;
import handwriting.core.SampleData;
import handwriting.gui.PixelUse;
import handwriting.gui.PixelUseVisualizer;
import handwriting.gui.PixelUser;
import handwriting.learners.decisiontree.DTNode;
import handwriting.learners.decisiontree.DTTrainer;
import handwriting.learners.decisiontree.PixelUsePattern;

import javax.swing.*;

public class DecisionTree implements RecognizerAI, PixelUser {
	private DTNode root;
	private int width, height;
	private String visualLabel;

	@Override
	public void train(SampleData data, ArrayBlockingQueue<Double> progress) throws InterruptedException {
		DTTrainer trainer = new DTTrainer(data, progress);
		this.width = data.getDrawingWidth();
		this.height = data.getDrawingHeight();
		root = trainer.train();
	}

	@Override
	public String classify(Drawing d) {
		return root.classify(d);
	}

	@Override
	public JPanel getVisualization() {
		return new PixelUseVisualizer(this);
	}

	@Override
	public String toString() {
		return root.toString();
	}

	@Override
	public Set<String> getLabels() {
		Set<String> result = new HashSet<>();
		root.addAllLabels(result);
		return result;
	}

	@Override
	public PixelUsePattern getPixelUse(String label) {
		PixelUsePattern pixels = new PixelUsePattern(width, height);
		root.visualize(visualLabel, new ArrayList<>(), pixels);
		return pixels;
	}
}
