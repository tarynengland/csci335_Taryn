package robosim.ai;

import robosim.core.Simulator;

public interface Controller {
	public void control(Simulator sim);

	default public String getStatus() {return "";}
}
