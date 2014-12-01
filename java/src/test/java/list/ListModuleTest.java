package list;

import helper.Union;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static list.ListModule.List;
import static list.ListModule.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ListModuleTest {

    private ArrayList<Integer> list;

    @Before
    public void setUp() throws Exception {
        list = new ArrayList<>();
        for(int i=0; i<1000; i++) {
            list.add(1);
        }
    }

    @Test
    public void should_foldLeft_is_available() throws Exception {
        double start = System.nanoTime();

        int sum = foldLeft(0, list.iterator(), new Union<Integer, Integer>() {
            @Override
            public Integer apply(Integer seed, Integer item) {
                return seed + item;
            }
        });

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

    @Test
    public void should_foldLeft_listModule_structure() throws Exception {
        List<Integer> list = of(1, 2, 3, 4, 5);
        int sum = list.foldLeft(0, new Union<Integer, Integer>() {
            @Override
            public Integer apply(Integer seed, Integer item) {
                return seed + item;
            }
        });

        assertThat(sum, is(15));
    }

    public static Integer foldLeft(Integer seed, Iterator<Integer> iterator, Union<Integer, Integer> f) {
        if(!iterator.hasNext()) {
            return seed;
        }
        return foldLeft(f.apply(seed, iterator.next()), iterator, f);
    }
}
