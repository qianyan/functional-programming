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
    public void flatten_() throws Exception {
        assertThat(flatten(_l(
                _l(1, 2),
                _l(3, 4),
                _l(5)
        )), is(_l(1, 2, 3, 4, 5)));
    }

    @Test
    public void count_() throws Exception {
        assertThat(count(_l(1, 2, 3, 2, 4, 5, 2), 2), is(3));
    }

    @Test
    public void intersection_set() throws Exception {
        assertThat(xSet(_s(1, 2, 3, 4), _s(1, 2)), is(_s(1, 2)));
    }

    @Test
    public void supplementary_set() throws Exception {
        assertThat(nSet(_s(1, 2, 3, 4), _s(1, 2)), is(_s(3, 4)));
    }

}
