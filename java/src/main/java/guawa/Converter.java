package guawa;

import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Converter {
    public static <T> T[] _a(T... args) {
        return args;
    }

    @SafeVarargs
    public static <T> List<T> _l(T... args) {
        return Arrays.asList(args);
    }

    public static <T> Set<T> _s(T... args) {
        return Sets.newHashSet(args);
    }
}
