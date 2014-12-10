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
    public void intersection_set_with_excluded_elements() throws Exception {
        assertThat(xSet(_s(1, 2, 3, 4), _s(1, 2, 5)), is(_s(1, 2)));
    }

    @Test
    public void supplementary_set() throws Exception {
        assertThat(nSet(_s(1, 2, 3, 4), _s(1, 2)), is(_s(3, 4)));
    }

    @Test
    public void supplementary_set_with_excluded_elements() throws Exception {
        assertThat(nSet(_s(1, 2, 3, 4), _s(1, 2, 5)), is(_s(3, 4)));
    }

    @Test
    public void union_() throws Exception {
        assertThat(uSet(_s(1, 2, 3, 4), _s(2, 3, 6)), is(_s(1, 2, 3, 4, 6)));
    }

    @Test
    public void reverse_() throws Exception {
        assertThat(reverse(_l(1, 2, 3, 4, 5)), is(_l(5, 4, 3, 2, 1)));
    }

    @Test
    public void difference() throws Exception {
        assertThat(diff(_s(1, 2, 3), _s(2, 4, 5)), is(_s(1, 3)));
    }

    @Test
    public void symmetric_difference() throws Exception {
        assertThat(sdiff(_s(1, 2, 3), _s(2, 3, 5)), is(_s(1, 5)));
    }
}
