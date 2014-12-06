package guawa;

import org.junit.Test;

import static guawa.Guawa.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GuawaTest {
    @Test
    public void infinite_sequence_sum_cubes() throws Exception {
        assertThat(sumCubes(1, 4), is(100));
    }

    @Test
    public void remove_continuous_duplicated_elements_of_list() throws Exception {
        assertThat(uniq(1, 2, 2, 3, 4, 4, 5), is(_l(1, 2, 3, 4, 5)));
    }

    @Test
    public void flatten_iterables() throws Exception {
        assertThat(flatten(_l(
                _l(1, 2),
                _l(3, 4),
                _l(5)
        )), is(_l(1, 2, 3, 4, 5)));
    }
}
