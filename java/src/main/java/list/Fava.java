package list;

import helper.Union;

import java.util.Iterator;
import java.util.List;

public class Fava {
    public static <T> T foldLeft(T seed, Iterator<T> iterator, Union<T, T> f) {
        if (!iterator.hasNext()) {
            return seed;
        }
        return foldLeft(f.apply(seed, iterator.next()), iterator, f);
    }

    public static <T> T foldLeft(T seed, List<T> list, Union<T, T> union) {
        checkNotNull(list, "list cannot be null");
        T meta = seed;
        for (T elem : list) {
            meta = union.apply(meta, elem);
        }
        return meta;
    }

    private static void checkNotNull(Object obj, String message) throws IllegalArgumentException {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
