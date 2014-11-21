package helper;

public interface Function<T1, F> {
    public T1 apply(T1 seed, F item);
}
