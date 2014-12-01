package helper;

public interface Union<T1, F> {
    public T1 apply(T1 seed, F item);
}
