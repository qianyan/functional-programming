package guawa;

import org.junit.Test;

import static guawa.Guawa.sumCubes;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GuawaTest {
    @Test
    public void infinite_sequence_sum_cubes() throws Exception {
        assertThat(sumCubes(1, 4), is(100));
    }
}
