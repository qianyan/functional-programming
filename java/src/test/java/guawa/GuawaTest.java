package guawa;

import com.google.common.base.Function;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static guawa.Guawa.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GuawaTest {
    @Test
    public void infinite_sequence_sum_cubes() throws Exception {
        assertThat(sumCubes(1, 4), is(100));
    }

    @Test
    public void remove_continuous_duplicated_elements_of_list() throws Exception {
        assertThat(continuniq(1, 2, 2, 3, 4, 4, 5), is(Converter._l(1, 2, 3, 4, 5)));
    }

    @Test
    public void flatten() throws Exception {
        assertThat(Guawa.flatten(Converter._l(
                Converter._l(1, 2),
                Converter._l(3, 4),
                Converter._l(5)
        )), is(Converter._l(1, 2, 3, 4, 5)));
    }

    @Test
    public void count() throws Exception {
        assertThat(Guawa.count(Converter._l(1, 2, 3, 2, 4, 5, 2), 2), is(3));
    }

    @Test
    public void intersection_set() throws Exception {
        assertThat(xSet(Converter._s(1, 2, 3, 4), Converter._s(1, 2)), is(Converter._s(1, 2)));
    }

    @Test
    public void intersection_set_with_excluded_elements() throws Exception {
        assertThat(xSet(Converter._s(1, 2, 3, 4), Converter._s(1, 2, 5)), is(Converter._s(1, 2)));
    }

    @Test
    public void supplementary_set() throws Exception {
        assertThat(nSet(Converter._s(1, 2, 3, 4), Converter._s(1, 2)), is(Converter._s(3, 4)));
    }

    @Test
    public void supplementary_set_with_excluded_elements() throws Exception {
        assertThat(nSet(Converter._s(1, 2, 3, 4), Converter._s(1, 2, 5)), is(Converter._s(3, 4)));
    }

    @Test
    public void union() throws Exception {
        assertThat(uSet(Converter._s(1, 2, 3, 4), Converter._s(2, 3, 6)), is(Converter._s(1, 2, 3, 4, 6)));
    }

    @Test
    public void reverse() throws Exception {
        assertThat(Guawa.reverse(Converter._l(1, 2, 3, 4, 5)), is(Converter._l(5, 4, 3, 2, 1)));
    }

    @Test
    public void difference() throws Exception {
        assertThat(diff(Converter._s(1, 2, 3), Converter._s(2, 4, 5)), is(Converter._s(1, 3)));
    }

    @Test
    public void symmetric_difference() throws Exception {
        assertThat(sdiff(Converter._s(1, 2, 3), Converter._s(2, 3, 5)), is(Converter._s(1, 5)));
    }

    @Test
    public void makeString() throws Exception {
        assertThat(mkString(Converter._l("one", "two", "three", "four"), ":"), is("one:two:three:four"));
    }

    @Test
    public void makeClosedString() throws Exception {
        assertThat(mkString(Converter._l("one", "two", "three", "four"), "{", ":", "}"), is("{one:two:three:four}"));
    }

    @Test
    public void findWhere_for_string() throws Exception {
        assertThat(findWhere(Converter._l("1", "22", "333"), _m("length", 2)), is("22"));
    }

    @Test
    public void findWhere_for_an_obj() throws Exception {
        assertThat(findWhere(Converter._l(new Person("A", "female"), new Person("B", "male"), new Person("B", "female")),
                _m("name", "B")), is(new Person("B", "male")));
    }

    @Test
    public void where() throws Exception {
        assertThat(Guawa.where(Converter._l(new Person("A", "female"), new Person("B", "male"), new Person("B", "female")),
                _m("name", "B")), is(Converter._l(new Person("B", "male"), new Person("B", "female"))));
    }

    @Test
    public void sample() throws Exception {
        assertThat(Guawa.sample(Converter._a(1, 3, 4, 5, 10, 20), 5).length, is(5));
        assertThat(Guawa.sample(Guawa.range(30), 10).length, is(10));
    }

    @Test
    public void range() throws Exception {
        assertThat(Guawa.range(5).length, is(5));
    }

    @Test
    public void range_with_start() throws Exception {
        assertThat(Guawa.range(5, 10).length, is(5));
    }

    @Test
    public void range_with_step() throws Exception {
        assertThat(Guawa.range(5, 30, 5).length, is(5));
    }

    @Test
    public void sorted_index() throws Exception {
        assertThat(Guawa.sortedIndex(Converter._a(1, 2, 5, 6), 5), is(2));
        assertThat(Guawa.sortedIndex(Converter._a(1, 2, 5, 6), 4), is(2));
    }


    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_tail_for_empty_array() throws Exception {
        GuawaArray.tail(Converter._a());
    }

    @Test
    public void times() throws Exception {
        Function<Integer, Object> func = mock(Function.class);
        given(func.apply(anyInt())).willReturn(anyObject());

        Guawa.times(3, func);

        verify(func, Mockito.times(3)).apply(anyInt());
    }

    @Test
    public void groupBy() throws Exception {
        Map<String, Collection<Person>> expectedGroup = new HashMap<>();
        expectedGroup.put("A", Converter._l(new Person("A", "female")));
        expectedGroup.put("B", Converter._l(new Person("B", "male"), new Person("B", "female")));

        assertThat(Guawa.groupBy(Converter._a(new Person("A", "female"),
                        new Person("B", "male"),
                        new Person("B", "female")), _m("name", String.class)),
                is(expectedGroup));
    }
}
