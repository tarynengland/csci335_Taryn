package robosim.ai;

import robosim.core.Simulator;

public interface Controller {
	void control(Simulator sim);

	default String getStatus() {return "";}
}
