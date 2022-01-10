package handwriting.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import handwriting.core.*;
import handwriting.learners.som.SelfOrgMap;
import search.core.AIReflector;

@SuppressWarnings("serial")
public class DrawingEditor extends JFrame {
	public final static int DRAWING_WIDTH = 40, DRAWING_HEIGHT = 40;

	private MousePencil c;
	private DrawingPanel view;
	private SampleData data;
	private RecognizerAI ai;
	private SelfOrgMap som;
	
	private JMenuItem open, save, viewSom;
	private JButton clear, recordDrawing, drawErase, visualize;
	private JFileChooser chooser;
	private JComboBox labeler, indexer, learner;
	private AIReflector<RecognizerAI> finder;
	private JButton createLearner, applyLearner;
	private JTextField netLabel;
	private JProgressBar trainingProgress;
	
	private JFrame visualFrame = new JFrame();
	
	private Drawing d() {return view.getDrawing();}
	
	private Drawing makeNewDrawing() {return new Drawing(20, 20);}
	
	public DrawingEditor() {
		setSize(700, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		finder = new AIReflector<>(RecognizerAI.class, "handwriting.learners");
		
		getContentPane().setLayout(new BorderLayout());
		
		view = new DrawingPanel(makeNewDrawing());
		c = new MousePencil(view);
		getContentPane().add(view, BorderLayout.CENTER);
		
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		
		addFileMenu(bar);
		addLearnerControls();
		addViewByLabel();
	}
	
	private void addFileMenu(JMenuBar bar) {
		JMenu fileMenu = new JMenu("File");
		bar.add(fileMenu);
		
		open = new JMenuItem("Open");
		open.addActionListener(new Opener());
		fileMenu.add(open);
		
		save = new JMenuItem("Save");
		save.addActionListener(new Saver());
		fileMenu.add(save);
		
		chooser = new JFileChooser();
	}
	
	private void addLearnerControls() {
		JPanel learnerControls = new JPanel();
		createLearner = new JButton("Create learner");
		createLearner.addActionListener(new Creator());
		learnerControls.add(createLearner);
		
		learner = new JComboBox();
		for (String type: finder.getTypeNames()) {
			learner.addItem(type);
		}
		learnerControls.add(learner);

		trainingProgress = new JProgressBar(0, 100);
		trainingProgress.setValue(0);
		trainingProgress.setStringPainted(true);
		learnerControls.add(trainingProgress);
		
		applyLearner = new JButton("Classify drawing");
		applyLearner.addActionListener(new Applier());
		learnerControls.add(applyLearner);
		
		learnerControls.add(new JLabel("Drawing label:"));
		netLabel = new JTextField(10);
		netLabel.setEditable(false);
		learnerControls.add(netLabel);

		getContentPane().add(learnerControls, BorderLayout.SOUTH);
	}
	
	private void addViewByLabel() {
		data = new SampleData();
		
		JPanel dataPanel = new JPanel();
		labeler = new JComboBox();
		labeler.addActionListener(new Labeler());
		dataPanel.add(labeler);
		
		indexer = new JComboBox();
		indexer.addActionListener(new Swapper());
		dataPanel.add(indexer);
		
		clear = new JButton("Clear");
		clear.addActionListener(new Clearer());
		dataPanel.add(clear);
		
		recordDrawing = new JButton("Record drawing");
		recordDrawing.addActionListener(new Recorder());
		dataPanel.add(recordDrawing);
		
		drawErase = new JButton("Erase");
		drawErase.addActionListener(new DrawEraser());
		dataPanel.add(drawErase);
		
		visualize = new JButton("Visualize");
		visualize.addActionListener(new VisualizeFramer());
		dataPanel.add(visualize);
		
		getContentPane().add(dataPanel, BorderLayout.NORTH);
	}
	
	public static void main(String[] args) {
		DrawingEditor gui = new DrawingEditor();
		gui.setVisible(true);
	}
	
	private class Opener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int choice = chooser.showOpenDialog(null);
			if (choice == JFileChooser.APPROVE_OPTION) {
				try {
					data = SampleData.parseDataFrom(new Scanner(chooser.getSelectedFile()));
					loadLabels();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private void loadLabels() {
		labeler.removeAllItems();
		for (String label: data.allLabels()) {
			labeler.addItem(label);
		}
	}
	
	private class Saver implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int choice = chooser.showSaveDialog(null);
			if (choice == JFileChooser.APPROVE_OPTION) {
				try {
					PrintStream ps = new PrintStream(chooser.getSelectedFile());
					ps.println(data.toString());
					ps.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private class Labeler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (hasCurrentLabel()) {
				String label = getCurrentLabel();
				if (data.hasLabel(label)) {
					indexer.removeAllItems();
					for (int i = 0; i < data.numDrawingsFor(label); ++i) {
						indexer.addItem(i);
					}
					changeIndexedDrawing();
				} else {
					data.addLabel(label);
				}
			}
		}
	}
	
	private boolean hasCurrentLabel() {
		return labeler.getItemCount() > 0;
	}
	
	private String getCurrentLabel() {
		return labeler.getSelectedItem().toString();
	}

	private void changeIndexedDrawing() {
		if (indexer.getItemCount() > 0) {
			String label = getCurrentLabel();
			int index = Integer.parseInt(indexer.getSelectedItem().toString());
			if (data.numDrawingsFor(label) > index) {
				view.resetDrawing(data.getDrawing(label, index));
			}
		}
	}
	
	private class Recorder implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String label = JOptionPane.showInputDialog("Enter drawing label");
			if (label != null) {
				data.addDrawing(label, view.getDrawing());
				loadLabels();
				indexer.addItem(data.numDrawingsFor(label));
			}
		}
	}
	
	private class Clearer implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.resetDrawing(makeNewDrawing());
		}
	}
	
	private class Swapper implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeIndexedDrawing();
		}
	}
	
	private class DrawEraser implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (c.isDrawing()) {
				drawErase.setText("Draw");
				c.erase();
			} else {
				drawErase.setText("Erase");
				c.draw();
			}
		}
	}
	
	private class Creator implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int choice = chooser.showOpenDialog(null);
			if (choice == JFileChooser.APPROVE_OPTION) {
				try {
					ArrayBlockingQueue<Double> progress = new ArrayBlockingQueue<Double>(1);
					SampleData training = SampleData.parseDataFrom(new Scanner(chooser.getSelectedFile()));
					ai = finder.newInstanceOf(learner.getSelectedItem().toString());
					ai.train(training, progress);
					JOptionPane.showMessageDialog(DrawingEditor.this, "Number of correct tests: " + ai.numCorrectTests(data) + " / " + data.numDrawings());
				} catch (FileNotFoundException | InstantiationException | IllegalArgumentException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | SecurityException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	private class Applier implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (ai != null) {
				netLabel.setText(ai.classify(d()));
			}
		}
	}
	
	private class VisualizeFramer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel visPanel = ai.getVisualization();
			visualFrame.setContentPane(visPanel);
			visualFrame.setSize(300, 300);
			visualFrame.setVisible(true);
		}
	}
}
