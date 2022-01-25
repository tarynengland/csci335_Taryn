package checkers.evaluators;

import checkers.core.Checkerboard;

import java.util.function.ToDoubleFunction;

public class NoPreference implements ToDoubleFunction<Checkerboard> {
    public double applyAsDouble(Checkerboard c) {
        return 0;
    }
}
