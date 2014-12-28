package guawa;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GuawaStringTest {
    @Test
    public void capitalize() throws Exception {
        assertThat(GuawaString.capitalize(" hello "), is("Hello"));
    }

    @Test
    public void slugify() throws Exception {
        assertThat(GuawaString.slugify(" hello World!"), is("hello-world"));
    }

    @Test
    public void trim() throws Exception {
        assertThat(GuawaString.trim(" foo "), is("foo"));
        assertThat(GuawaString.trim("foo", "f"), is("oo"));
        assertThat(GuawaString.trim("fooff", "f"), is("oo"));
        assertThat(GuawaString.trim("_-foo-_", "_-"), is("foo"));
    }

    @Test
    public void ltrim() throws Exception {
        assertThat(GuawaString.ltrim(" foo"), is("foo"));
        assertThat(GuawaString.ltrim(" foo "), is("foo "));
        assertThat(GuawaString.ltrim("foof", "f"), is("oof"));
        assertThat(GuawaString.ltrim("_-foo-_", "_-"), is("foo-_"));
    }

    @Test
    public void rtrim() throws Exception {
        assertThat(GuawaString.rtrim("foo "), is("foo"));
        assertThat(GuawaString.rtrim(" foo "), is(" foo"));
        assertThat(GuawaString.rtrim("foof", "f"), is("foo"));
        assertThat(GuawaString.rtrim("_-foo-_", "_-"), is("_-foo"));
    }

    @Test
    public void repeat() throws Exception {
        assertThat(GuawaString.repeat("foo"), is(""));
        assertThat(GuawaString.repeat(""), is(""));
        assertThat(GuawaString.repeat("foo", 3), is("foofoofoo"));
        assertThat(GuawaString.repeat("foo", 3, "*"), is("foo*foo*foo"));
        assertThat(GuawaString.repeat("foo", 3, 3), is("foo3foo3foo"));
        assertThat(GuawaString.repeat("foo", 3, new Person("ryan", "male")),
                is("fooPerson{name=ryan, gender=male}fooPerson{name=ryan, gender=male}foo"));
    }
}
