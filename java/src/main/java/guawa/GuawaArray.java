package guawa;

import com.google.common.collect.Iterables;

public class GuawaArray {
    public static <T> T head(T[] args) {
        return Iterables.getFirst(Converter._l(args), null);
    }

    public static <T> T[] tail(T[] args) {
        return (T[]) Converter._l(args).subList(1, args.length).toArray();
    }
}
