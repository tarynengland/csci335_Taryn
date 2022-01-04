package checkers.core;

import checkers.core.Checkerboard;

public interface EvalFunc {
    // Every EvalFunc class must have a zero-argument constructor
    
    // Pre: c != null
    // Post: Returns a value between -1 and 1
    //       -1 signifies a loss for c.getCurrentPlayer()
    //       1 signifies a victory for c.getCurrentPlayer()
    //       0 signifies a draw
    //       Decimal values in between reflect degrees of confidence about an outcome
    public double evaluate(Checkerboard c);
}
