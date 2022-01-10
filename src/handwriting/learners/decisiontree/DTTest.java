package handwriting.learners.decisiontree;

import handwriting.core.Drawing;
import handwriting.core.Duple;
import handwriting.core.SampleData;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DTTest {
    Drawing d1 = new Drawing("3|3|XXX|OOO|XXO");
    Drawing d2 = new Drawing("3|3|XOX|OXO|OXX");
    Drawing d3 = new Drawing("3|3|OXO|OXO|XOX");
    Drawing d4 = new Drawing("3|3|XOX|XOX|XOX");
    Drawing d5 = new Drawing("3|3|OOO|XXX|OOO");
    DTSampleData dtMain, dt1, dt2;

    @Before
    public void setup() {
        dtMain = new DTSampleData();
        dtMain.addDrawing("A", d1);
        dtMain.addDrawing("A", d2);
        dtMain.addDrawing("B", d3);
        dtMain.addDrawing("B", d4);
        dtMain.addDrawing("B", d5);

        dt1 = new DTSampleData();
        dt1.addDrawing("A", d1);
        dt1.addDrawing("B", d3);
        dt1.addDrawing("B", d5);

        dt2 = new DTSampleData();
        dt2.addDrawing("A", d2);
        dt2.addDrawing("B", d4);
    }

    @Test
    public void testInterior() {
        DTLeaf left = new DTLeaf("left");
        DTLeaf right = new DTLeaf("right");
        DTInterior nodeO = new DTInterior(0, 1, left, right);
        DTInterior nodeX = new DTInterior(1, 1, left, right);

        assertEquals(left.getLabel(), nodeX.classify(d5));
        assertEquals(right.getLabel(), nodeO.classify(d5));
    }

    @Test
    public void testGini() {
        assertEquals(0.4, dtMain.getPortionFor("A"), 0.001);
        assertEquals(0.6, dtMain.getPortionFor("B"), 0.001);
        assertEquals(.48, dtMain.getGini(), 0.001);
    }

    @Test
    public void testSplit() {
        Duple<DTSampleData,DTSampleData> splits = dtMain.splitOn(1, 1);
        assertFalse(isInside(d1, "A", splits.getFirst()));
        assertTrue(isInside(d1, "A", splits.getSecond()));

        assertTrue(isInside(d2, "A", splits.getFirst()));
        assertFalse(isInside(d2, "A", splits.getSecond()));

        assertTrue(isInside(d3, "B", splits.getFirst()));
        assertFalse(isInside(d3, "B", splits.getSecond()));

        assertFalse(isInside(d4, "B", splits.getFirst()));
        assertTrue(isInside(d4, "B", splits.getSecond()));

        assertTrue(isInside(d5, "B", splits.getFirst()));
        assertFalse(isInside(d5, "B", splits.getSecond()));
    }

    public boolean isInside(Drawing d, String label, SampleData samples) {
        for (Duple<String, Drawing> sample: samples.allSamples()) {
            if (sample.getFirst().equals(label) && sample.getSecond().equals(d)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testGain() {
        assertEquals(-.46444, DTTrainer.gain(dtMain, dt1, dt2), .001);
    }

    @Test
    public void testTrain() throws InterruptedException {
        DTTrainer trainer = new DTTrainer(dtMain, new ArrayBlockingQueue<>(1000));
        DTNode tree = trainer.train();
        assertEquals("A", tree.classify(d1));
        assertEquals("A", tree.classify(d2));
        assertEquals("B", tree.classify(d3));
        assertEquals("B", tree.classify(d4));
        assertEquals("B", tree.classify(d5));
    }

    @Test
    public void testReduced() {
        ArrayList<Duple<Integer,Integer>> features = DTTrainer.allFeatures(dtMain);
        ArrayList<Duple<Integer,Integer>> reduced = DTTrainer.reducedFeatures(dtMain, dtMain.getDrawingHeight());
        assertEquals(dtMain.getDrawingHeight(), reduced.size());
        for (Duple<Integer,Integer> feature: reduced) {
            assertTrue(features.contains(feature));
        }
    }
}
