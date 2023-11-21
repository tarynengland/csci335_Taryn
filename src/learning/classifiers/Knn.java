package learning.classifiers;

import core.Duple;
import learning.core.Classifier;
import learning.core.Histogram;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.ToDoubleBiFunction;

// KnnTest.test() should pass once this is finished.
public class Knn<V, L> implements Classifier<V, L> {
    private ArrayList<Duple<V, L>> data = new ArrayList<>();
    private ToDoubleBiFunction<V, V> distance;
    private int k;

    public Knn(int k, ToDoubleBiFunction<V, V> distance) {
        this.k = k;
        this.distance = distance;
    }

    @Override
    public L classify(V value) {
        PriorityQueue<Duple<V,L>> queue = new PriorityQueue<>((v1,v2) ->
            Double.compare(distance.applyAsDouble(value,v1.getFirst()),
                           distance.applyAsDouble(value, v2.getFirst())));
        Histogram<L> histogram = new Histogram<>();
        queue.addAll(data);
        for (int i =0; i<k; i++){
            histogram.bump(queue.remove().getSecond());
        }
        return histogram.getPluralityWinner();
    }

    @Override
    public void train(ArrayList<Duple<V, L>> training) {
        data.addAll(training);
    }
}
