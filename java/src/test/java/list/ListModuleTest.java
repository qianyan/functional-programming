package list;

import helper.Union;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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

}
