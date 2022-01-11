package handwriting.learners.som;

import handwriting.core.Drawing;
import learning.Duple;
import handwriting.core.RecognizerAI;
import handwriting.core.SampleData;
import handwriting.gui.SelfOrgMapPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;

public class SOMRecognizer implements RecognizerAI {
    private String[][] labels;
    private SelfOrgMap som;

    public final static int K = 11;

    public SOMRecognizer(int mapSide, int drawingWidth, int drawingHeight) {
        som = new SelfOrgMap(mapSide, drawingWidth, drawingHeight);
        labels = new String[mapSide][mapSide];
    }

    @Override
    public void train(SampleData data, ArrayBlockingQueue<Double> progress) throws InterruptedException {
        double prog = 0.0;
        ArrayList<Duple<String, Drawing>> allSamples = data.allSamples();
        Collections.shuffle(allSamples);
        for (int i = 0; i < allSamples.size(); i++) {
            Duple<String, Drawing> sample = allSamples.get(i);
            som.train(sample.getSecond());
            prog += 1.0 / (2.0 * allSamples.size());
            progress.put(prog);
        }

        for (int x = 0; x < som.getMapWidth(); x++) {
            for (int y = 0; y < som.getMapHeight(); y++) {
                labels[x][y] = findLabelFor(som.getNode(x, y), K, allSamples);
                prog += 1.0 / (2.0 * som.getMapHeight() * som.getMapWidth());
                progress.put(prog);
            }
        }
    }

    // TODO: Perform a k-nearest-neighbor retrieval to return the label that
    //  best matches the current node.
    static String findLabelFor(FloatDrawing currentNode, int k, ArrayList<Duple<String, Drawing>> allSamples) {
        // Your code here
		return "I have no idea";
    }

    @Override
    public String classify(Drawing d) {
        SOMPoint where = som.bestFor(d);
        return labels[where.x()][where.y()];
    }

    @Override
    public JPanel getVisualization() {
        return new SelfOrgMapPanel(som);
    }
}
