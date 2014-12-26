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
}
