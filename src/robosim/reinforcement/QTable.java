package robosim.reinforcement;

import java.util.Arrays;

public class QTable {
    private double[][] q;
    private int[][] visits;
    private int targetVisits;
    private double discount, rateConstant;
    private int lastState, lastAction;

    // TODO:
    //  Calculate the learning rate using this formula: 1/(1 + total visits for this (state, action) pair/rateConstant)
    //  Should pass QTableTest.testLearningRate().

    // Literally just applied the formula provided to me above and it worked.

    public double getLearningRate(int state, int action) {
        return (1/(1 + (visits[state][action]/rateConstant)));
    }

    // TODO: Find the action for the given state that has the highest q value.
    //  Should pass QTableTest.testBestAction()

    // It took me awhile to notice that the action I was looking for the second
    // indexed item in the array and I could probably just have that be what I
    // was really keeping track of as I was looping through the state.
    // Also having done the isExploring function helped me think about how
    // to loop through the actions of a state

    public int getBestAction(int state) {
        int best = 0;
        for (int i = 0; i < q[state].length; i++){
            if (q[state][best] < q[state][i]){
                best = i;
            }
        }
        return best;
    }

    // TODO: Returns true if any action for this state is below the target
    //  visits. Returns false otherwise.
    //  Should pass QTableTest.testIsExploring()

    // Looking back at the formula provided at the beginning helped me see
    // how I could structure my if statement to see where my actions would
    // be located at to see if it had reached the target value for visits.
    // I figured a for loop would help simplify looking through a state as
    // opposed to a while loop that way I would not have to update anything
    // outside my iterations to stop when I needed to and still see all actions.

    public boolean isExploring(int state) {
        for( int i = 0; i < visits[state].length; i++){
            if ( visits[state][i] < targetVisits){
                return true;
            }
        }
        return false;
    }

    // TODO: Returns the least visited action in state.
    //  Should pass QTableTest.testLeastVisitedAction()

    // I had the same approach as above for the best q value, but I had
    // to switch my lesser than to a greater sign contrary to what I had
    // assumed based on the previous function. I first thought that if
    // the indexed 'i' value was greater to the least value it was least visited
    // yet in coding that was not what was showing on first test through
    // so I switched it then it passed the test.

    public int leastVisitedAction(int state) {
        int least = 0;
        for (int i = 0; i < visits[state].length; i++){
            if( visits[state][least] > visits[state][i]){
                least = i;
            }
        }
        return least;
    }

    // TODO:
    //  1. Calculate the update for the last state and action.
    //  2. Modify the q-value for the last state and action.
    //  3. Increase the visit count for the last state and action.
    //  4. Select the action for the new state.
    //     * If we are exploring, use the least visited action.
    //     * Otherwise, use the best action.
    //  5. Update the last state and action.
    //  6. Return the selected action.
    //  Should pass QTableTest.testSenseActLearn()
    //
    //  Q update formula:
    //    Q(s, a) = (1 - learningRate) * Q(s, a) + learningRate *
    //    (discount * maxa(Q(s', a)) + r(s))


    // https://www.freecodecamp.org/news/an-introduction-to-q-learning-reinforcement-learning-14ac0b4493cc/
    // Steps 4 and 5 from the site above along with my notes from class helped with trying to
    // figure out which pieces went where and how to use some of the variables from above not
    // used within this function for getting the update formula.

    public int senseActLearn(int newState, double reward) {
        // private ints from above used for getLearningRate
        double learnrate = getLearningRate(lastState,lastAction);
        // Saw the getQ function from below and what it did after just typing 'q' trying to see
        // all there was already in this folder dealing with the q-values.
        double updateQ = ((1-learnrate) * getQ(lastState,lastAction)) + learnrate *
                (discount * getQ(newState,getBestAction(newState)) + reward);
        q[lastState][lastAction] = updateQ;
        visits[lastState][lastAction] ++;
        int action = 0;
        if (isExploring(newState)){
            action = leastVisitedAction(newState);
        }
        else{
            action = getBestAction(newState);
        }
        // I feel like there was a way to update both of these variables in one line of code
        // or am I just mixing that up with something else??
        lastAction = action;
        lastState = newState;
        return action;
    }

    public QTable(int states, int actions, int startState, int targetVisits, int rateConstant, double discount) {
        this.targetVisits = targetVisits;
        this.rateConstant = rateConstant;
        this.discount = discount;
        q = new double[states][actions];
        visits = new int[states][actions];
        lastState = startState;
        lastAction = 0;
    }

    private QTable() {}

    static QTable from(String s) {
        QTable result = new QTable();
        String[] lines = s.split("\n");
        String[] values1 = lines[0].split(";");
        result.targetVisits = Integer.parseInt(values1[0].split(":")[1]);
        result.discount = Double.parseDouble(values1[1].split(":")[1]);
        result.rateConstant = Double.parseDouble(values1[2].split(":")[1]);
        result.lastState = Integer.parseInt(values1[3].split(":")[1]);
        result.lastAction = Integer.parseInt(values1[4].split(":")[1]);

        boolean createdArrays = false;
        for (int i = 1; i < lines.length; i++) {
            String[] topSplit = lines[i].split(":");
            int state = Integer.parseInt(topSplit[0]);
            assert state == i - 1;
            String[] nextSplit = topSplit[1].split(",");
            for (int action = 0; action < nextSplit.length; action++) {
                String[] innerSplit = nextSplit[action].split("\\(");
                if (!createdArrays) {
                    int numStates = lines.length - 1;
                    int numActions = innerSplit.length;
                    result.q = new double[numStates][numActions];
                    result.visits = new int[numStates][numActions];
                    createdArrays = true;
                }
                result.q[state][action] = Double.parseDouble(innerSplit[0]);
                result.visits[state][action] = Integer.parseInt(innerSplit[1].substring(0, innerSplit[1].length() - 1));
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("targetVisits:");
        result.append(targetVisits);
        result.append(";discount:");
        result.append(discount);
        result.append(";rateConstant:");
        result.append(rateConstant);
        result.append(";lastState:");
        result.append(lastState);
        result.append(";lastAction:");
        result.append(lastAction);
        result.append('\n');
        for (int state = 0; state < q.length; ++state) {
            result.append(state);
            result.append(':');
            for (int action = 0; action < q[state].length; action++) {
                result.append(q[state][action]);
                result.append('(');
                result.append(visits[state][action]);
                result.append(')');
                result.append(',');
            }
            result.deleteCharAt(result.length() - 1);
            result.append('\n');
        }
        return result.toString();
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