package search;

import java.util.Optional;

public interface SearchQueue<T> {
    int compare (SearchNode<T> t1, SearchNode<T> t2);

    void enqueue(SearchNode<T> node);
    Optional<SearchNode<T>> dequeue();
}
