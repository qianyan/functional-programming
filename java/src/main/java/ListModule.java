import helper.Function;

public class ListModule {
    public static interface List<T> {
        public T head();
        public List<T> tail();
        public boolean isEmpty();
        public <T1> T1 foldLeft(T1 seed, Function<T1, T> calculus);
    }

    public static final class NonEmptyList<T> implements List<T> {
        private final T head;
        private final List<T> tail;

        public NonEmptyList(T head, List<T> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public List<T> tail() {
            return tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public <T1> T1 foldLeft(T1 seed, Function<T1, T> calculus) {
            return tail().foldLeft(calculus.apply(seed, head()), calculus);
        }
    }

    public static final class EmptyList<T> implements List<T> {

        @Override
        public T head() {
            throw new RuntimeException("");
        }

        @Override
        public List<T> tail() {
            throw new RuntimeException("");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public <T1> T1 foldLeft(T1 seed, Function<T1, T> calculus) {
            return null;
        }
    }
}
