package checkers.searchers;

import checkers.core.Checkerboard;
import checkers.core.EvalFunc;
import checkers.core.MoveScore;
import checkers.core.Searcher;

public class OnePlyShuffle extends Searcher {
    private int numNodes;
    
    public OnePlyShuffle(EvalFunc e) {
        super(e);
    }
    
    public int numNodesExpanded() {return numNodes;}
    
    // Pre: getEvaluator() != null
    // Post: Returns a move selected by combining search and evaluator
    public MoveScore selectMove(Checkerboard board) {
        java.util.ArrayList<Checkerboard> boards = board.getNextBoards();
        numNodes = boards.size();
        int whichOne = (int)(Math.random() * boards.size());
        Checkerboard result = boards.get(whichOne);
        return new MoveScore(result.getLastMove(), getEvaluator().evaluate(result));
    }
}
