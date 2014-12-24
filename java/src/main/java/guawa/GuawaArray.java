package guawa;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.FluentIterable.of;

public class GuawaArray {
    public static <T> T head(T[] args) {
        return Iterables.getFirst(Converter._l(args), null);
    }

    public static <T> T[] tail(T[] args) {
        return (T[]) Converter._l(args).subList(1, args.length).toArray();
    }

    public static <T> T[] uniq(T[] args) {
        return (T[]) Converter._s(args).toArray();
    }

    public static <T> int indexOf(T[] args, final T arg) {
        return Iterables.indexOf(Converter._l(args), new Predicate<T>() {
            @Override
            public boolean apply(T input) {
                return input.equals(arg);
            }
        });
    }

    public static <T> int lastIndexOf(T[] args, final T arg) {
        return Converter._l(args).lastIndexOf(arg);
    }

    public static <T> T[] shuffle(T[] args) {
        List<T> list = Converter._l(args);
        Collections.shuffle(list);
        return (T[]) list.toArray();
    }

    public static <T> T[] initial(T[] args, int lastCount) {
        checkArgument(lastCount >= 0 && lastCount <= args.length, "last counts is not allowed");
        return Arrays.copyOf(args, args.length - lastCount);
    }

    public static <T extends Comparable<T>> int sortedIndex(T[] args, T obj) {
        int index = Collections.binarySearch(Converter._l(args), obj);
        return index > 0 ? index : index + args.length + 1;
    }

    public static <T extends Comparable<T>> T[] sortBy(T[] args, final Function<T, T> func) {
        T[] copyOfArgs = args.clone();
        Arrays.sort(copyOfArgs, new Comparator<T>() {
            @Override
            public int compare(T pre, T next) {
                return func.apply(pre).compareTo(func.apply(next));
            }
        });

        return copyOfArgs;
    }

    public static <T> T[] reject(T[] args, Predicate<T> predicate) {
        return of(args).filter(Predicates.not(predicate)).toArray((Class<T>) Object.class);
    }

    public static int random(int start, int end) {
        Random random = new Random();
        int number = random.nextInt(end - start);
        return number + start;
    }

    public static <T> T[] compact(T[] arr) {
        return of(arr).filter(new Predicate<T>() {
            @Override
            public boolean apply(T a) {
                return a != null;
            }
        }).toArray((Class<T>) Object.class);
    }

    public static <T> T[] without(T[] args, T... without) {
        Set<T> set = Converter._s(without);
        List<T> list = new ArrayList<>();
        for (T arg : args) {
            if (!set.contains(arg)) {
                list.add(arg);
            }
        }
        return (T[]) list.toArray();
    }
}
