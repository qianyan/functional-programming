package guawa;

import org.junit.Test;

import java.util.Arrays;

import static guawa.Guawa.fold;
import static guawa.Guawa.sumCubes;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GuawaTest {
    @Test
    public void infinite_sequence_sum_cubes() throws Exception {
        assertThat(sumCubes(1, 4), is(100));
    }

    @Test
    public void remove_continuous_duplicated_elements_of_list() throws Exception {
        assertThat(fold(1, 2, 2, 3, 4, 4, 5), is(Arrays.asList(1, 2, 3, 4, 5)));
    }
}
