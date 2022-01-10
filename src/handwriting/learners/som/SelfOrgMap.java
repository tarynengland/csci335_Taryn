package handwriting.learners.som;

import handwriting.core.Drawing;

import java.awt.*;

public class SelfOrgMap {
    private FloatDrawing[][] map;
    private double[][] trainingCounts;

    public SelfOrgMap(int side, int drawingWidth, int drawingHeight) {
        map = new FloatDrawing[side][side];
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                map[i][j] = new FloatDrawing(drawingWidth, drawingHeight);
            }
        }
        trainingCounts = new double[side][side];
    }

    public SOMPoint bestFor(Drawing example) {
        return bestFor(new FloatDrawing(example));
    }

    // TODO: Return a SOMPoint corresponding to the map square which has the
    //  smallest distance compared to example.
    private SOMPoint bestFor(FloatDrawing example) {
		// Your code here.
        return null;
    }

    // TODO: Train this SOM with example.
    //  1. Find the best matching node.
    //  2. For every node in the map:
    //     a. Find the distance weight to the best matching node. Call computeDistanceWeight().
    //     b. Add the distance weight to its training count.
    //     c. Find the effective learning rate. Call effectiveLearningRate().
    //     d. Update the node with the average of itself and example, weighted by
    //        the effective learning rate.
    public void train(Drawing example) {
        // Your code here
    }

    // TODO: Find the distance between the locations of sp1 and sp2 in the
    //  self-organizing map. Next, scale the distance based on the map length,
    //  so that it is a value between zero and one. Then, since big distances
    //  should have small weights, subtract it from -1. Finally, make sure it
    //  is not any smaller than zero.
    public double computeDistanceWeight(SOMPoint sp1, SOMPoint sp2) {
		// Your code here
        return 0.0;
    }

    // TODO: First, find the update rate. This is the reciprocal of the training
    //  count. But make sure it is no more than one, even if the training count is
    //  tiny. Then, multiply it by the distance weight.
    public static double effectiveLearningRate(double distWeight, double trainingCounts) {
		// Your code here
        return 0.0;
    }

    public FloatDrawing getNode(int x, int y) {
        return map[x][y];
    }

    public int getMapWidth() {
        return map.length;
    }

    public int getMapHeight() {
        return map[0].length;
    }

    public int getDrawingWidth() {
        return map[0][0].getWidth();
    }

    public int getDrawingHeight() {
        return map[0][0].getHeight();
    }

    public boolean inMap(SOMPoint point) {
        return point.x() >= 0 && point.x() < getMapWidth() && point.y() >= 0 && point.y() < getMapHeight();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SelfOrgMap that) {
            if (this.getMapHeight() == that.getMapHeight() && this.getMapWidth() == that.getMapWidth()) {
                for (int x = 0; x < getMapWidth(); x++) {
                    for (int y = 0; y < getMapHeight(); y++) {
                        if (!map[x][y].equals(that.map[x][y])) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int x = 0; x < getMapWidth(); x++) {
            for (int y = 0; y < getMapHeight(); y++) {
                result.append(String.format("(%d, %d):\n", x, y));
                result.append(getNode(x, y));
            }
        }
        return result.toString();
    }
}
