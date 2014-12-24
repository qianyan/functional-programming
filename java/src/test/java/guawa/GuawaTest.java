package guawa;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static guawa.Guawa.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
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
        assertThat(uniq(1, 2, 2, 3, 4, 4, 5), is(_l(1, 2, 3, 4, 5)));
    }

    @Test
    public void flatten() throws Exception {
        assertThat(Guawa.flatten(_l(
                _l(1, 2),
                _l(3, 4),
                _l(5)
        )), is(_l(1, 2, 3, 4, 5)));
    }

    @Test
    public void count() throws Exception {
        assertThat(Guawa.count(_l(1, 2, 3, 2, 4, 5, 2), 2), is(3));
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
    public void union() throws Exception {
        assertThat(uSet(_s(1, 2, 3, 4), _s(2, 3, 6)), is(_s(1, 2, 3, 4, 6)));
    }

    @Test
    public void reverse() throws Exception {
        assertThat(Guawa.reverse(_l(1, 2, 3, 4, 5)), is(_l(5, 4, 3, 2, 1)));
    }

    @Test
    public void difference() throws Exception {
        assertThat(diff(_s(1, 2, 3), _s(2, 4, 5)), is(_s(1, 3)));
    }

    @Test
    public void symmetric_difference() throws Exception {
        assertThat(sdiff(_s(1, 2, 3), _s(2, 3, 5)), is(_s(1, 5)));
    }

    @Test
    public void makeString() throws Exception {
        assertThat(mkString(_l("one", "two", "three", "four"), ":"), is("one:two:three:four"));
    }

    @Test
    public void makeClosedString() throws Exception {
        assertThat(mkString(_l("one", "two", "three", "four"), "{", ":", "}"), is("{one:two:three:four}"));
    }

    @Test
    public void compact() throws Exception {
        assertThat(Guawa.compact(_a(1, null, 2, 3, 4)), is(_a(1, 2, 3, 4)));
        assertThat(Guawa.compact(_a("1", null, "2", "3", "4")), is(_a("1", "2", "3", "4")));
    }

    @Test
    public void reject() throws Exception {
        assertThat(Guawa.reject(_a(1, 2, 3, 4, 5), new Predicate<Integer>(){
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        }), is(_a(1, 3, 5)));
    }

    @Test
    public void findWhere_for_string() throws Exception {
        assertThat(findWhere(_l("1", "22", "333"), _m("length", 2)), is("22"));
    }

    @Test
    public void findWhere_for_an_obj() throws Exception {
        assertThat(findWhere(_l(new Person("A", "female"), new Person("B", "male"), new Person("B", "female")),
                _m("name", "B")), is(new Person("B", "male")));
    }

    @Test
    public void where() throws Exception {
        assertThat(Guawa.where(_l(new Person("A", "female"), new Person("B", "male"), new Person("B", "female")),
                _m("name", "B")), is(_l(new Person("B", "male"), new Person("B", "female"))));
    }

    @Test
    public void without() throws Exception {
        assertThat(Guawa.without(_a(1, 2, 3, 4, 2), 1, 3, 3, 4), is(_a(2, 2)));
    }

    @Test
    public void index_of() throws Exception {
        assertThat(Guawa.indexOf(_a(1, 2, 5, 3, 4), 5), is(2));
    }

    @Test
    public void last_index_of() throws Exception {
        assertThat(Guawa.lastIndexOf(_a(1, 2, 5, 3, 3, 4), 3), is(4));
    }

    @Test
    public void shuffle() throws Exception {
        assertThat(Guawa.shuffle(_a(1, 2, 3, 4, 5)), is(not(_a(1, 2, 3, 4, 5))));
    }

    @Test
    public void sample() throws Exception {
        assertThat(Guawa.sample(_a(1, 3, 4, 5, 10, 20), 5).length, is(5));
        assertThat(Guawa.sample(Guawa.range(30), 10).length, is(10));
    }

    @Test
    public void initial() throws Exception {
        assertThat(Guawa.initial(_a(1, 2, 3, 5, 6), 3), is(_a(1, 2)));
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
        assertThat(Guawa.sortedIndex(_a(1, 2, 5, 6), 5), is(2));
        assertThat(Guawa.sortedIndex(_a(1, 2, 5, 6), 4), is(2));
    }

    @Test
    public void head() throws Exception {
        assertThat(Guawa.head(_a(1, 2, 3, 4, 5)), is(1));
        assertThat(Guawa.head(_a(1)), is(1));
        assertThat(Guawa.head(_a()), nullValue());
    }

    @Test
    public void tail() throws Exception {
        assertThat(Guawa.tail(_a(1, 2, 3, 4, 5)), is(_a(2, 3, 4, 5)));
        assertThat(Guawa.tail(_a(1)), is(_a()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_tail_for_empty_array() throws Exception {
        Guawa.tail(_a());
    }

    @Test
    public void pluck() throws Exception {
        assertThat(Guawa.pluck(_a(new Person("A", "female"),
                        new Person("B", "male"),
                        new Person("B", "female")),
                        _m("name", String.class)),
                is(_a("A", "B", "B")));

        assertThat(Guawa.pluck(_a(new Person("A", "female"),
                        new Person("B", "male"),
                        new Person("B", "female")),
                        _m("gender", String.class)),
                is(_a("female", "male", "female")));
    }

    @Test
    public void times() throws Exception {
        Function<Integer, Object> func = mock(Function.class);
        given(func.apply(anyInt())).willReturn(anyObject());

        Guawa.times(3, func);

        verify(func, Mockito.times(3)).apply(anyInt());
    }

    @Test
    public void sortBy() throws Exception {
        assertThat(Guawa.sortBy(_a(1.0, 2.0, 3.0, 4.0, 5.0, 6.0), new Function<Double, Double>() {
            @Override
            public Double apply(Double elem) {
                return Math.sin(elem);
            }
        }), is(_a(5.0, 4.0, 6.0, 3.0, 1.0, 2.0)));
    }

    @Test
    public void random() throws Exception {
        int random = Guawa.random(0, 100);
        assertThat(random > 0 && random < 100, is(true));
    }

    @Test
    public void indexBy() throws Exception {
        Map<String, Person> expectedMap = new HashMap<>();
        expectedMap.put("A", new Person("A", "female"));
        expectedMap.put("B", new Person("B", "male"));
        expectedMap.put("C", new Person("C", "female"));

        assertThat(Guawa.indexBy(_a(new Person("A", "female"),
                new Person("B", "male"),
                new Person("C", "female")),
                _m("name", String.class)), is(expectedMap));
    }
}
