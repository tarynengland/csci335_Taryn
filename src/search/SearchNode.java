package search;

import java.util.ArrayDeque;
import java.util.Optional;

public class SearchNode<T> {
    private T value;
    private Optional<SearchNode<T>> parent;
    private int depth;

    public SearchNode(T value, Optional<SearchNode<T>> parent) {
        this.value = value;
        this.parent = parent;
        this.depth = parent.map(node -> 1 + node.depth).orElse(0);
    }

    private void updateSearchPath(ArrayDeque<T> path) {
        path.addFirst(value);
        parent.ifPresent(p -> p.updateSearchPath(path));
    }

    public ArrayDeque<T> searchPath() {
        ArrayDeque<T> path = new ArrayDeque<>();
        updateSearchPath(path);
        return path;
    }

    public T getValue() {return value;}

    public int getDepth() {return depth;}
}
