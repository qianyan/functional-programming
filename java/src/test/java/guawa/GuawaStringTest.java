package guawa;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GuawaStringTest {
    @Test
    public void capitalize() throws Exception {
        assertThat(GuawaString.capitalize(" hello "), is("Hello"));
    }
}
