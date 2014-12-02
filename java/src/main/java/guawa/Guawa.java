package guawa;

import com.google.common.base.Function;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.Iterables;

import java.util.Iterator;

import static com.google.common.collect.Iterables.limit;

public class Guawa {
    public static int sumCubes(int floor, int ceil) {
        int sum = 0;
        Iterable<Integer> cubes = limit(cube(from(floor)), ceil);
        for (Integer cube : cubes) {
            sum += cube;
        }
        return sum;
    }

    private static Iterable<Integer> from(final int start) {
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new AbstractSequentialIterator<Integer>(start) {
                    private int i = start;
                    @Override
                    protected Integer computeNext(Integer previous) {
                        if (i == Integer.MAX_VALUE) {
                            return null;
                        }

                        return ++i;
                    }
                };
            }
        };
    }

    private static Iterable<Integer> cube(Iterable<Integer> integers) {
        return Iterables.transform(integers, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                return input * input * input;
            }
        });
    }
}
