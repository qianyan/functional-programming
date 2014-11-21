import com.google.common.collect.Lists;
import helper.Function;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ListModuleTest {

    private ArrayList<Integer> list;

    @Before
    public void setUp() throws Exception {
        list = Lists.newArrayList();
        for(int i=0; i<1000; i++) {
            list.add(1);
        }
    }

    @Test
    public void should_foldLeft_is_available() throws Exception {
        double start = System.nanoTime();

        int sum = foldLeft(0, list.iterator(), sum());

        double end = System.nanoTime();
        System.out.println("FoldLeft: " + (end - start) / 10e9);

        assertThat(sum, is(1000));
    }

    @Test
    public void should_sum_with_seed() throws Exception {
        double start = System.nanoTime();

        int sum = 0;
        for (int i : list) {
            sum = sum + i;
        }

        double end = System.nanoTime();
        System.out.println("Sum: " + (end - start) / 10e9);

        assertThat(sum, is(1000));
    }

    private Function<Integer, Integer> sum() {
        return new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer seed, Integer item) {
                return seed + item;
            }
        };
    }

    public static Integer foldLeft(Integer seed, Iterator<Integer> iterator, Function<Integer, Integer> f) {
        if(!iterator.hasNext()) {
            return seed;
        }
        return foldLeft(f.apply(seed, iterator.next()), iterator, f);
    }
}
