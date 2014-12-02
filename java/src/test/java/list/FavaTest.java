package list;

import helper.Union;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static list.Fava.foldLeft;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FavaTest {
    private List<Integer> preLists;

    @Before
    public void setUp() throws Exception {
        preLists = new ArrayList<>();
        for(int i=0; i<1000; i++) {
            preLists.add(1);
        }
    }

    @Test
    public void foldLeftTest() throws Exception {
        assertThat(foldLeft(0, preLists.iterator(), sum()), is(1000));
    }

    @Test
    public void foldLeftNiceTest() throws Exception {
        assertThat(foldLeft(0, preLists, sum()), is(1000));
    }

    private Union<Integer, Integer> sum() {
        return new Union<Integer, Integer>() {
            @Override
            public Integer apply(Integer seed, Integer item) {
                return seed + item;
            }
        };
    }
}
