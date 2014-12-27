package guawa;

import com.google.common.base.Ascii;
import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;

public class GuawaString {

    public static String capitalize(String word) {
        String trimWord = word.trim();
        return (trimWord.isEmpty())
                ? trimWord
                : new StringBuilder(trimWord.length())
                .append(Ascii.toUpperCase(trimWord.charAt(0)))
                .append(Ascii.toLowerCase(trimWord.substring(1)))
                .toString();
    }

    public static String slugify(String s) {
        String s1 = CharMatcher.JAVA_LETTER.negate().removeFrom(s);
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, s1);
    }

    public static String trim(String word, String match) {
        return CharMatcher.anyOf(match).removeFrom(word);
    }

    public static String trim(String word) {
        return word.trim();
    }

    public static String ltrim(String word) {
        return word.substring(word.indexOf(trim(word)));
    }

    public static String ltrim(String word, String match) {
        return word.substring(word.indexOf(trim(word, match)));
    }

    public static String rtrim(String word) {
        return reverse(ltrim(reverse(word)));
    }

    private static String reverse(String word) {
        return new StringBuffer(word).reverse().toString();
    }

    public static String rtrim(String word, String match) {
        return reverse(ltrim(reverse(word), match));
    }
}
