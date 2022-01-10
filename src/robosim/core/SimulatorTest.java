package robosim.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimulatorTest {

	@Test
	public void testObjs() {
		Simulator sim = new Simulator(100, 200);
		SimObject s1 = new SimObject(10, 20, 2);
		sim.add(s1);
		SimObject s2 = new SimObject(100, 100, 2);
		sim.add(s2);

		Robot bot = new Robot(50, 100, 0);
		sim.add(bot);

		assertTrue(bot.withinSonar(s2));
		assertFalse(bot.withinSonar(s1));

		assertEquals(45, sim.findClosestObject(), 0.01);

		sim.add(new SimObject(80, 100, 2));
		assertEquals(25, sim.findClosestObject(), 0.01);
	}

	@Test
	public void testWalls() {
		int numHeadings = (int)(2 * Math.PI / Robot.ANGULAR_VELOCITY);
		assertEquals(8, numHeadings);

		Simulator sim = new Simulator(100, 200);
		Robot bot = new Robot(50, 100, 0);
		sim.add(bot);

		double[] targets = new double[]{45, 65.711, 95, 65.711, 45, 65.711, 95, 65.711};
		assertEquals(numHeadings, targets.length);
		bot.turn(Direction.FWD);
		for (int i = 0; i < targets.length; i++) {
			assertEquals(i * Robot.ANGULAR_VELOCITY, bot.getHeading(), 0.01);
			assertEquals(targets[i], sim.findClosestEdge(), 0.01);
			bot.update();
		}

		assertEquals(2.0 * Math.PI, bot.getHeading(), 0.01);
		sim.add(new SimObject(75, 100, 0));
		assertEquals(20.0, sim.findClosest(), 0.01);
	}

	@Test
	public void testStr() {
		SimObject obj = new SimObject(10, 11, 12);
		assertEquals(obj.toString(), "{(10.000000,11.000000);12.000000}");
		assertEquals(obj, new SimObject(obj.toString()));
	}
}
