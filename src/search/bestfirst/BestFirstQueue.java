package search.bestfirst;

import search.SearchNode;
import search.SearchQueue;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.ToIntFunction;

public class BestFirstQueue<T> implements SearchQueue<T> {
    // TODO: Implement this class
    // HINT: Use java.util.PriorityQueue. It will really help you.
    // https://codereview.stackexchange.com/questions/143206/a-uniform-cost-and-greedy-best-first-search-implementations
    abstract class Comparator implements java.util.Comparator<T> {
    }
    
    private PriorityQueue<SearchNode<T>> priority = new PriorityQueue<>(Comparator);
    private HashMap<T, Integer> visited = new HashMap<>();
    private ToIntFunction<T> heuristic;
    public BestFirstQueue(ToIntFunction<T> heuristic) {
        // TODO: Your code here
        this.heuristic = heuristic;
    }

    @Override
    public int compare(SearchNode<T> t1, SearchNode<T> t2) {
        double cost1 = t1.getDepth() + heuristic.applyAsInt(t1.getValue());
        double cost2 = t2.getDepth() + heuristic.applyAsInt(t2.getValue());
        return Double.compare(cost1, cost2);
    }

    @Override
    public void enqueue(SearchNode<T> node) {
        // TODO: Your code here
        int i = heuristic.applyAsInt(node.getValue()) + node.getDepth();
        if (!visited.containsKey(node.getValue()) || visited.get(node.getValue())>i) {
            priority.add(node);
            visited.put(node.getValue(),i);
        }
    }

    @Override
    public Optional<SearchNode<T>> dequeue() {
        // TODO: Your code here
        // TODO: Replace this line; it is here to let the code compile.
        if (priority.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(priority.remove());
        }
        }
}
