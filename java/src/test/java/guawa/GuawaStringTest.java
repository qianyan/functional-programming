package guawa;

import org.junit.Test;

import static guawa.Converter._a;
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

    @Test
    public void decapitalize() throws Exception {
        assertThat(GuawaString.decapitalize(" Hello "), is("hello"));
        assertThat(GuawaString.decapitalize("HELLO"), is("hELLO"));
    }

    @Test
    public void join() throws Exception {
        assertThat(GuawaString.join("", "foo", "bar"), is("foobar"));
        assertThat(GuawaString.join("", null, "bar"), is("bar"));
    }

    @Test
    public void reverse() throws Exception {
        assertThat(GuawaString.reverse("foo"), is("oof"));
        assertThat(GuawaString.reverse("saippuakauppias"), is("saippuakauppias"));
    }

    @Test
    public void clean() throws Exception {
        assertThat(GuawaString.clean(" foo    bar   "), is("foo bar"));
    }

    @Test
    public void chop() throws Exception {
        assertThat(GuawaString.chop("whitespace", 2), is(_a("wh", "it", "es", "pa", "ce")));
        assertThat(GuawaString.chop("whitespace", 3), is(_a("whi", "tes", "pac", "e")));
        assertThat(GuawaString.chop("whitespace", 0), is(_a("whitespace")));
    }

    @Test
    public void splice() throws Exception {
        assertThat(GuawaString.splice("whitespace", 5, 5, "shift"), is("whiteshift"));
        assertThat(GuawaString.splice(
                        "https://edtsech@bitbucket.org/edtsech/underscore.strings", 30, 7, "epeli"),
                is("https://edtsech@bitbucket.org/epeli/underscore.strings"));
    }

    @Test
    public void pred() throws Exception {
        assertThat(GuawaString.pred('b'), is('a'));
        assertThat(GuawaString.pred('B'), is('A'));
        assertThat(GuawaString.pred(','), is('+'));
        assertThat(GuawaString.pred('2'), is('1'));
        assertThat(GuawaString.pred('豈'), is(''));
    }

    @Test
    public void succ() throws Exception {
        assertThat(GuawaString.succ('a'), is('b'));
        assertThat(GuawaString.succ(''), is('豈'));
        assertThat(GuawaString.succ('A'), is('B'));
        assertThat(GuawaString.succ('+'), is(','));
        assertThat(GuawaString.succ('1'), is('2'));
    }

    @Test
    public void titleize() throws Exception {
        assertThat(GuawaString.titleize("the titleize string method"), is("The Titleize String Method"));
        assertThat(GuawaString.titleize("a-dash-separated-string"), is("A-Dash-Separated-String"));
        assertThat(GuawaString.titleize("a_dash_separated_string"), is("A_Dash_Separated_String"));
        assertThat(GuawaString.titleize("A-DASH-SEPARATED-STRING"), is("A-Dash-Separated-String"));
    }

    @Test
    public void camelize() throws Exception {
        assertThat(GuawaString.camelize("the_camelize_string_method"), is("theCamelizeStringMethod"));
        assertThat(GuawaString.camelize("the_camelize___string_method"), is("theCamelizeStringMethod"));
        assertThat(GuawaString.camelize("webkit-transform"), is("webkitTransform"));
        assertThat(GuawaString.camelize("webkit----transform"), is("webkitTransform"));
        assertThat(GuawaString.camelize(" webkit transform"), is("webkitTransform"));
        assertThat(GuawaString.camelize(" webkit     transform "), is("webkitTransform"));
        assertThat(GuawaString.camelize("_webkit   _  transform "), is("WebkitTransform"));
    }

    @Test
    public void dasherize() throws Exception {
        assertThat(GuawaString.dasherize("the_dasherize_string_method"), is("the-dasherize-string-method"));
        assertThat(GuawaString.dasherize("TheDasherizeStringMethod"), is("the-dasherize-string-method"));
        assertThat(GuawaString.dasherize("The_Dasherize-String_-Method"), is("the-dasherize-string-method"));
        assertThat(GuawaString.dasherize("the dasherize string method"), is("the-dasherize-string-method"));
    }

    @Test
    public void underscored() throws Exception {
        assertThat(GuawaString.underscored("the-underscored-string-method"), is("the_underscored_string_method"));
        assertThat(GuawaString.underscored("theUnderscoredStringMethod"), is("the_underscored_string_method"));
        assertThat(GuawaString.underscored("TheUnderscoredStringMethod"), is("the_underscored_string_method"));
        assertThat(GuawaString.underscored("The-Underscored_String_-Method"), is("the_underscored_string_method"));
    }

    @Test
    public void classify() throws Exception {
        assertThat(GuawaString.classify("some_class_name"), is("SomeClassName"));
        assertThat(GuawaString.classify("my wonderfull class_name"), is("MyWonderfullClassName"));
    }

    @Test
    public void humanize() throws Exception {
        assertThat(GuawaString.humanize("the humanize string method"), is("The humanize string method"));
        assertThat(GuawaString.humanize("the_humanize_string_method"), is("The humanize string method"));
        assertThat(GuawaString.humanize("TheHumanizeStringMethod"), is("The humanize string method"));
        assertThat(GuawaString.humanize("the humanize_id string method"), is("The humanize id string method"));
    }
}
