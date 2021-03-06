package guawa;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.FluentIterable.of;
import static com.google.common.collect.Iterables.limit;
import static com.google.common.collect.Lists.newArrayList;

public class Guawa {

    public static int sumCubes(int floor, int ceil) {
        int sum = 0;
        Iterable<Integer> cubes = limit(cube(from(floor)), ceil);
        for (Integer cube : cubes) {
            sum += cube;
        }
        return sum;
    }

    private static Iterable<Integer> from(final int start, final int... step) {
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

                        return i = (step.length > 0) ? i + step[0] : i + 1;
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

    public static <T> List<T> continuniq(T... list) {
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

    public static <T> Integer count(List<T> list, T obj) {
        return Iterables.frequency(list, obj);
    }

    public static <T> Set<T> xSet(Set<T> removeFrom, Set<T> retained) {
        Iterables.retainAll(removeFrom, retained);
        return removeFrom;
    }

    public static <T> Set<T> nSet(Set<T> removeFrom, Set<T> removed) {
        Iterables.removeAll(removeFrom, removed);
        return removeFrom;
    }

    public static <T> List<T> reverse(List<T> list) {
        return Lists.reverse(list);
    }

    public static <T> Set<T> uSet(Set<T> set0, Set<T> set1) {
        return Sets.union(set0, set1);
    }

    public static <T> Set<T> diff(Set<T> set0, Set<T> set1) {
        return Sets.difference(set0, set1);
    }

    public static <T> Set<T> sdiff(Set<T> set0, Set<T> set1) {
        return Sets.symmetricDifference(set0, set1);
    }

    public static <T> String mkString(Iterable<T> args, String seperator) {
        return Joiner.on(seperator).join(args);
    }

    public static <T> String mkString(List<T> args, String start, String seperator, String end) {
        return start + mkString(args, seperator) + end;
    }

    public static <T> Map<String, T> _m(String key, T value) {
        HashMap<String, T> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public static <T, F> T findWhere(List<T> objs, Map<String, F> prop) {
        Map.Entry<String, F> first = prop.entrySet().iterator().next();
        for (T obj : objs) {
            try {
                Object returnValue = obj.getClass().getMethod(first.getKey()).invoke(obj);
                if (returnValue.equals(first.getValue())) {
                    return obj;
                }
            } catch (NoSuchMethodException e) {
                try {
                    Field field = obj.getClass().getDeclaredField(first.getKey());
                    field.setAccessible(true);
                    Object fieldValue = field.get(obj);

                    if (fieldValue.equals(first.getValue())) {
                        return obj;
                    }
                } catch (NoSuchFieldException | IllegalAccessException e1) {
                    e1.printStackTrace();
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static <T> T[] sample(T[] args, int count) {
        return Arrays.copyOf(GuawaArray.shuffle(args), count);
    }

    public static Integer[] range(int stop) {
        return range(0, stop);
    }

    public static Integer[] range(int start, int stop) {
        return range(start, stop, 1);
    }

    public static Integer[] range(int start, int stop, int step) {
        int len = stop - start;
        checkArgument(step > 0 && step <= len, "step is not allowed");
        int count = len / step;
        return newArrayList(limit(from(start, step), count)).toArray(new Integer[count]);
    }

    public static <T, F> F[] pluck(T[] args, final Map<String, Class<F>> name$Type) {
        final Map.Entry<String, Class<F>> entry = name$Type.entrySet().iterator().next();
        return of(args).transform(toField(entry)).toArray(entry.getValue());
    }

    private static <T, F> Function<T, F> toField(final Map.Entry<String, Class<F>> entry) {
        return new Function<T, F>() {
            @Override
            public F apply(T obj) {
                return fieldOf(entry.getKey(), obj);
            }
        };
    }

    private static <T, F> F fieldOf(String name, T obj) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return (F) field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void times(int times, Function func) {
        for (int i = 0; i < times; i++) {
            func.apply(i);
        }
    }

    public static <T, F> Map<F, T> indexBy(T[] args, final Map<String, Class<F>> name$Type) {
        final Map.Entry<String, Class<F>> entry = name$Type.entrySet().iterator().next();
        return of(args).uniqueIndex(toField(entry));
    }

    public static <T, F> List<T> where(List<T> args, Map<String, F> prop) {
        final Map.Entry<String, F> first = firstEntry(prop);
        return FluentIterable.from(args).filter(new Predicate<T>() {
            @Override
            public boolean apply(T input) {
                return fieldOf(first.getKey(), input).equals(first.getValue());
            }
        }).toList();
    }

    private static <F> Map.Entry<String, F> firstEntry(Map<String, F> prop) {
        return prop.entrySet().iterator().next();
    }

    public static <T, F> Map<F, Collection<T>> groupBy(T[] args, Map<String, Class<F>> name$Type) {
        Map.Entry<String, Class<F>> entry = firstEntry(name$Type);
        return FluentIterable.of(args).index(toField(entry)).asMap();
    }
}
