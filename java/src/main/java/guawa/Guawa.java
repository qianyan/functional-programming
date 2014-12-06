package guawa;

import com.google.common.base.Function;
import com.google.common.collect.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Iterables.limit;
import static com.google.common.collect.Lists.newArrayList;

public class Guawa {
    public static <T> T[] _a(T... args) {
        return args;
    }

    public static <T> List<T> _l(T... args) {
        return Arrays.asList(args);
    }
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

    public static <T> List<T> uniq(T... list) {
        List<T> aList = newArrayList();
        PeekingIterator<T> iterator = Iterators.peekingIterator(Arrays.asList(list).iterator());
        while (iterator.hasNext()) {
            T current = iterator.next();
            while (iterator.hasNext() && iterator.peek().equals(current)) {
                iterator.next();
            }
            aList.add(current);
        }
        return aList;
    }

    public static <T> List<T> flatten(Iterable<List<T>> lists) {
        return newArrayList(Iterables.concat(lists));
    }
}
