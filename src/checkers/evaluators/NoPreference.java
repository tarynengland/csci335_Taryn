package checkers.evaluators;

import checkers.core.Checkerboard;
import checkers.core.EvalFunc;

public class NoPreference implements EvalFunc {
    public double evaluate(Checkerboard c) {
        return 0;
    }
}
