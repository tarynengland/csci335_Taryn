package robosim.ai;

// Q(s, a) = (1 - α) * Q(s, a) + α * (γ * maxa(Q(s', a)) + r(s))

public class QTable {
    private double[][] q;
    private int[][] visits;
    private int targetVisits;
    private double discount, rateConstant;
    private int lastState, lastAction;

    public QTable(int states, int actions, int startState, int targetVisits, int rateConstant, double discount) {
        this.targetVisits = targetVisits;
        this.rateConstant = rateConstant;
        this.discount = discount;
        q = new double[states][actions];
        visits = new int[states][actions];
        lastState = startState;
        lastAction = 0;
    }

    // TODO:
    //  1. Calculate the update for the last state and action.
    //  2. Modify the q-value for the last state and action.
    //  3. Increase the visit count for the last state and action.
    //  4. Select the best action for the new state.
    //  5. Update the last state and action.
    //  6. Return the best action.
    public int senseActLearn(int newState, double reward) {
        return -1;
    }

    // TODO:
    //  Calculate the learning rate using this formula: 1/(1 + total visits for this (state, action) pair/rateConstant)
    public double getLearningRate(int state, int action) {
        return 0.0;
    }

    // TODO: Find the action for the given state that has the highest q value.
    public int getBestAction(int state) {
        return -1;
    }

    // TODO: Returns true if any action for this state is below the target
    //  visits. Returns false otherwise.
    public boolean isExploring(int state) {
        return false;
    }

    // TODO: Returns the least visited action in state.
    public int leastVisitedAction(int state) {
        return -1;
    }

    public double getQ(int state, int action) {
        return q[state][action];
    }

    public int getLastState() {
        return lastState;
    }

    public int getLastAction() {
        return lastAction;
    }
}