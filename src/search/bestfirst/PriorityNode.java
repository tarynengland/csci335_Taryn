package search.bestfirst;

import search.SearchNode;

public class PriorityNode<T> implements Comparable<PriorityNode<T>> {
    private SearchNode<T> node;
    private int estimate, total;

    public PriorityNode(SearchNode<T> node, int estimate) {
        this.node = node;
        this.estimate = estimate;
        this.total = node.getDepth() + estimate;
    }

    public int getTotalEstimate() {return total;}

    public SearchNode<T> getNode() {return node;}

    @Override
    public int compareTo(PriorityNode<T> o) {
        return this.total - o.total;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof PriorityNode<?> that) {
            return this.compareTo((PriorityNode<T>)that) == 0;
        } else {
            return false;
        }
    }
}
