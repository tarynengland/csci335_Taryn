package checkers.core;

import search.core.AIReflector;

import javax.swing.JOptionPane;

abstract public class Searcher {
    
    private EvalFunc eval;
    private int maxDepth;

    // Pre: e != null
    // Post: getEvaluator() == e; getDepthLimit() == 4
    // Note: Every Searcher class must have a one-argument constructor that
    //       takes an EvalFunc
    public Searcher(EvalFunc e) {
        eval = e;
        maxDepth = 4;
    }
    
    // Pre: None
    // Post: if limit > 0, getDepthLimit() == limit; otherwise, limit = 2
    public void setDepthLimit(int limit) {
        maxDepth = (limit > 0) ? limit : 2;
    }
    
    // Pre: None
    // Post: Returns the maximum search depth for this Searcher.  Every
    //       Searcher reserves the right to exceed this depth as it sees fit.
    public int getDepthLimit() {return maxDepth;}

    // Pre: None
    // Post: Returns total # of node expansions
    abstract public int numNodesExpanded();

    // Pre: getEvaluator() != null
    // Post: Returns a move selected by combining search and evaluator
    abstract public MoveScore selectMove(Checkerboard board);

    // Pre: None
    // Post: Returns evaluation function for this search strategy
    public EvalFunc getEvaluator() {return eval;}
    
    public static Searcher makeSearcher(String searcherName, String evalName, AIReflector<EvalFunc> funcs, AIReflector<Searcher> searchers) {
        try {
            EvalFunc ef = funcs.newInstanceOf(evalName);
            return searchers.constructorFor(searcherName, EvalFunc.class).newInstance(ef);
        } catch (ClassCastException cce) {
            JOptionPane.showMessageDialog(null, "Class Cast: " + cce.getMessage());
            return null;
        } catch (NoSuchMethodException nsme) {
            JOptionPane.showMessageDialog(null, "Constructor not found: " + nsme.getMessage());
            return null;
        } catch (InstantiationException ie) {
            JOptionPane.showMessageDialog(null, "Undefined methods: " + ie.getMessage());
            return null;
        } catch (IllegalAccessException iae) {
            JOptionPane.showMessageDialog(null, "private/protected violation: " + iae.getMessage());
            return null;
        } catch (java.lang.reflect.InvocationTargetException ite) {
            JOptionPane.showMessageDialog(null, "constructor threw exception: " + ite.getMessage());
            return null;
        }
    }
}
