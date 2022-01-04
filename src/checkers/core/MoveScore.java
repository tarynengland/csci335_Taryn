package checkers.core;

public class MoveScore {
    public MoveScore(Move m, double score) {
        this.m = m;
        this.score = score;
    }
    
    public Move getMove() {return m;}
    public double getScore() {return score;}
    
    public void negateScore() {score = -score;}
    
    private Move m;
    private double score;
}

