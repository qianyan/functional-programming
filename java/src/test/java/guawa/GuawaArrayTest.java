package guawa;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static guawa.Converter._a;
import static guawa.Guawa._m;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class GuawaArrayTest {
    @Test
    public void initial() throws Exception {
        assertThat(Guawa.initial(_a(1, 2, 3, 5, 6), 3), is(_a(1, 2)));
    }

    @Test
    public void shuffle() throws Exception {
        assertThat(Guawa.shuffle(_a(1, 2, 3, 4, 5)), is(not(_a(1, 2, 3, 4, 5))));
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
    public void without() throws Exception {
        assertThat(Guawa.without(_a(1, 2, 3, 4, 2), 1, 3, 3, 4), is(_a(2, 2)));
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
    public void compact() throws Exception {
        assertThat(Guawa.compact(_a(1, null, 2, 3, 4)), is(_a(1, 2, 3, 4)));
        assertThat(Guawa.compact(_a("1", null, "2", "3", "4")), is(_a("1", "2", "3", "4")));
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
    public void head() throws Exception {
        assertThat(GuawaArray.head(_a(1, 2, 3, 4, 5)), is(1));
        assertThat(GuawaArray.head(_a(1)), is(1));
        assertThat(GuawaArray.head(_a()), nullValue());
    }

    @Test
    public void tail() throws Exception {
        assertThat(GuawaArray.tail(Converter._a(1, 2, 3, 4, 5)), is(Converter._a(2, 3, 4, 5)));
        assertThat(GuawaArray.tail(Converter._a(1)), is(Converter._a()));
    }
}
