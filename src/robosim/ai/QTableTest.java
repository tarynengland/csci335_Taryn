package robosim.ai;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class QTableTest {
    QTable testTable;

    @Before
    public void setup() {
        testTable = new QTable(2, 2, 0, 1, 2, 0.5);
        assertEquals(0, testTable.senseActLearn(1, 1.2));
    }

    public void update1() {
        assertEquals(1, testTable.senseActLearn(0, 0.4));
    }

    public void update2() {
        assertEquals(1, testTable.senseActLearn(1, -1.45));
    }

    public void update3() {
        assertEquals(0, testTable.senseActLearn(0, 1.0));
    }

    public void update4() {
        assertEquals(1, testTable.senseActLearn(1, 1.6));
    }

    @Test
    public void easyQ() {
        assertEquals(1.2, testTable.getQ(0, 0), 0.001);
        assertEquals(0.0, testTable.getQ(0, 1), 0.001);
        assertEquals(0.0, testTable.getQ(1, 0), 0.001);
        assertEquals(0.0, testTable.getQ(1, 1), 0.001);
    }

    @Test
    public void testQ() {
        easyQ();
        update1();
        assertEquals(1.2, testTable.getQ(0, 0), 0.001);
        assertEquals(0.0, testTable.getQ(0, 1), 0.001);
        assertEquals(1.0, testTable.getQ(1, 0), 0.001);
        assertEquals(0.0, testTable.getQ(1, 1), 0.001);

        update2();
        assertEquals(1.2, testTable.getQ(0, 0), 0.001);
        assertEquals(-0.95, testTable.getQ(0, 1), 0.001);
        assertEquals(1.0, testTable.getQ(1, 0), 0.001);
        assertEquals(0.0, testTable.getQ(1, 1), 0.001);

        update3();
        assertEquals(1.2, testTable.getQ(0, 0), 0.001);
        assertEquals(-0.95, testTable.getQ(0, 1), 0.001);
        assertEquals(1.0, testTable.getQ(1, 0), 0.001);
        assertEquals(1.6, testTable.getQ(1, 1), 0.001);

        update4();
        assertEquals(2.0, testTable.getQ(0, 0), 0.001);
        assertEquals(-0.95, testTable.getQ(0, 1), 0.001);
        assertEquals(1.0, testTable.getQ(1, 0), 0.001);
        assertEquals(1.6, testTable.getQ(1, 1), 0.001);
    }

    @Test
    public void easyLearningRate() {
        assertEquals(0.66666, testTable.getLearningRate(0, 0), 0.001);
        assertEquals(1.0, testTable.getLearningRate(0, 1), 0.001);
        assertEquals(1.0, testTable.getLearningRate(1, 1), 0.001);
        assertEquals(1.0, testTable.getLearningRate(1, 0), 0.001);
    }

    @Test
    public void testLearningRate() {
        easyLearningRate();
        update1();
        assertEquals(0.66666, testTable.getLearningRate(0, 0), 0.001);
        assertEquals(1.0, testTable.getLearningRate(0, 1), 0.001);
        assertEquals(1.0, testTable.getLearningRate(1, 1), 0.001);
        assertEquals(0.66666, testTable.getLearningRate(1, 0), 0.001);

        update2();
        assertEquals(0.66666, testTable.getLearningRate(0, 0), 0.001);
        assertEquals(0.66666, testTable.getLearningRate(0, 1), 0.001);
        assertEquals(1.0, testTable.getLearningRate(1, 1), 0.001);
        assertEquals(0.66666, testTable.getLearningRate(1, 0), 0.001);

        update3();
        assertEquals(0.66666, testTable.getLearningRate(0, 0), 0.001);
        assertEquals(0.66666, testTable.getLearningRate(0, 1), 0.001);
        assertEquals(0.66666, testTable.getLearningRate(1, 1), 0.001);
        assertEquals(0.66666, testTable.getLearningRate(1, 0), 0.001);

        update4();
        assertEquals(0.5, testTable.getLearningRate(0, 0), 0.001);
        assertEquals(0.66666, testTable.getLearningRate(0, 1), 0.001);
        assertEquals(0.66666, testTable.getLearningRate(1, 1), 0.001);
        assertEquals(0.66666, testTable.getLearningRate(1, 0), 0.001);
    }

    @Test
    public void easyLeastVisitedAction() {
        assertEquals(1, testTable.leastVisitedAction(0));
    }

    @Test
    public void testLeastVisitedAction() {
        easyLeastVisitedAction();
        update1();
        assertEquals(1, testTable.leastVisitedAction(1));

        update2();
        update3();
        update4();
        assertEquals(1, testTable.leastVisitedAction(0));
        assertEquals(0, testTable.leastVisitedAction(1));
    }

    @Test
    public void easyBestAction() {
        assertEquals(0, testTable.getBestAction(0));
    }

    @Test
    public void testBestAction() {
        easyBestAction();

        update1();
        assertEquals(0, testTable.getBestAction(1));

        update2();
        assertEquals(0, testTable.getBestAction(0));

        update3();
        assertEquals(0, testTable.getBestAction(0));

        update4();
        assertEquals(0, testTable.getBestAction(0));
        assertEquals(1, testTable.getBestAction(1));
    }

    @Test
    public void easyIsExploring() {
        assertTrue(testTable.isExploring(0));
        assertTrue(testTable.isExploring(1));
    }

    @Test
    public void testIsExploring() {
        easyIsExploring();
        update1();
        assertTrue(testTable.isExploring(0));
        assertTrue(testTable.isExploring(1));

        update2();
        assertFalse(testTable.isExploring(0));
        assertTrue(testTable.isExploring(1));

        update3();
        assertFalse(testTable.isExploring(0));
        assertFalse(testTable.isExploring(1));

        update4();
        assertFalse(testTable.isExploring(0));
        assertFalse(testTable.isExploring(1));
    }
}
