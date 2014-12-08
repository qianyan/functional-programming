package helper;

public interface Union<T> {
    public T apply(T seed, T item);
}
