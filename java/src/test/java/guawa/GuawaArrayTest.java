package guawa;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.junit.Test;

import static guawa.Converter._a;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class GuawaArrayTest {
    @Test
    public void initial() throws Exception {
        assertThat(GuawaArray.initial(_a(1, 2, 3, 5, 6), 3), is(_a(1, 2)));
    }

    @Test
    public void shuffle() throws Exception {
        assertThat(GuawaArray.shuffle(_a(1, 2, 3, 4, 5)), is(not(_a(1, 2, 3, 4, 5))));
    }

    @Test
    public void index_of() throws Exception {
        assertThat(GuawaArray.indexOf(_a(1, 2, 5, 3, 4), 5), is(2));
    }

    @Test
    public void last_index_of() throws Exception {
        assertThat(GuawaArray.lastIndexOf(_a(1, 2, 5, 3, 3, 4), 3), is(4));
    }

    @Test
    public void without() throws Exception {
        assertThat(GuawaArray.without(_a(1, 2, 3, 4, 2), 1, 3, 3, 4), is(_a(2, 2)));
    }

    @Test
    public void reject() throws Exception {
        assertThat(GuawaArray.reject(_a(1, 2, 3, 4, 5), new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        }, Integer.class), is(_a(1, 3, 5)));
    }

    @Test
    public void compact() throws Exception {
        assertThat(GuawaArray.compact(_a(1, null, 2, 3, 4), Integer.class), is(_a(1, 2, 3, 4)));
        assertThat(GuawaArray.compact(_a("1", null, "2", "3", "4"), String.class), is(_a("1", "2", "3", "4")));
    }

    @Test
    public void sortBy() throws Exception {
        assertThat(GuawaArray.sortBy(_a(1.0, 2.0, 3.0, 4.0, 5.0, 6.0), new Function<Double, Double>() {
            @Override
            public Double apply(Double elem) {
                return Math.sin(elem);
            }
        }), is(_a(5.0, 4.0, 6.0, 3.0, 1.0, 2.0)));
    }

    @Test
    public void random() throws Exception {
        int random = GuawaArray.random(0, 100);
        assertThat(random > 0 && random < 100, is(true));
    }

    @Test
    public void head() throws Exception {
        assertThat(GuawaArray.head(_a(1, 2, 3, 4, 5)), is(1));
        assertThat(GuawaArray.head(_a(1)), is(1));
        assertThat(GuawaArray.head(_a()), nullValue());
    }

    @Test
    public void tail() throws Exception {
        assertThat(GuawaArray.tail(_a(1, 2, 3, 4, 5)), is(_a(2, 3, 4, 5)));
        assertThat(GuawaArray.tail(_a(1)), is(_a()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_tail_for_empty_array() throws Exception {
        GuawaArray.tail(_a());
    }

    @Test
    public void uniq() throws Exception {
        assertThat(GuawaArray.uniq(_a(1, 2, 2, 3, 4, 2, 5)), is(_a(1, 2, 3, 4, 5)));
    }

    @Test
    public void flatten() throws Exception {
        assertThat(GuawaArray.flatten(Integer.class, _a(1, 2), _a(3, 4), _a(5, 6)), is(_a(1, 2, 3, 4, 5, 6)));
        assertThat(GuawaArray.flatten(Integer.class, _a(1, null), _a(3, 4), _a(5, 6)), is(_a(1, 3, 4, 5, 6)));
    }
}
