package guawa;

import com.google.common.base.*;

import java.util.regex.Pattern;

import static com.google.common.collect.Iterables.toArray;

public class GuawaString {

    private static final Pattern BEFORE_UPPER_CASE = Pattern.compile("(?=\\p{Upper})");

    public static String capitalize(String word) {
        return onCapitalize(word, true);
    }

    private static String onCapitalize(String word, boolean on) {
        String trimWord = word.trim();
        return (trimWord.isEmpty())
                ? trimWord
                : new StringBuilder(trimWord.length())
                .append(on ? Ascii.toUpperCase(trimWord.charAt(0)) : Ascii.toLowerCase(trimWord.charAt(0)))
                .append(trimWord.substring(1))
                .toString();
    }

    public static String slugify(String s) {
        String s1 = CharMatcher.JAVA_LETTER.negate().removeFrom(s);
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, s1);
    }

    public static String trim(String word, String match) {
        return CharMatcher.anyOf(match).trimFrom(word);
    }

    public static String trim(String word) {
        return CharMatcher.WHITESPACE.trimFrom(word);
    }

    public static String ltrim(String word) {
        return CharMatcher.WHITESPACE.trimLeadingFrom(word);
    }

    public static String ltrim(String word, String match) {
        return CharMatcher.anyOf(match).trimLeadingFrom(word);
    }

    public static String rtrim(String word) {
        return CharMatcher.WHITESPACE.trimTrailingFrom(word);
    }

    public static String rtrim(String word, String match) {
        return CharMatcher.anyOf(match).trimTrailingFrom(word);
    }

    public static String repeat(String word) {
        return repeat(word, 0);
    }

    public static String repeat(String word, int count) {
        return Strings.repeat(word, count);
    }

    public static <T> String repeat(String word, int count, T insert) {
        String repeat = repeat(word + insert, count);
        return repeat.substring(0, repeat.length() - insert.toString().length());
    }

    public static String decapitalize(String word) {
        return onCapitalize(word, false);
    }

    public static String join(String... args) {
        return Joiner.on("").skipNulls().join(args);
    }

    public static String reverse(String word) {
        return new StringBuilder(word).reverse().toString();
    }

    public static String clean(String word) {
        return CharMatcher.WHITESPACE.collapseFrom(trim(word), ' ');
    }

    public static String[] chop(String word, int fixedLength) {
        Preconditions.checkArgument(fixedLength >= 0, "fixedLength must greater than or equal to zero");
        if (fixedLength == 0) return new String[]{word};
        return toArray(Splitter.fixedLength(fixedLength).split(word), String.class);
    }

    public static String splice(String word, int start, int length, String replacement) {
        return new StringBuilder(word).replace(start, start + length, replacement).toString();
    }

    public static char pred(char ch) {
        return (char) (ch - 1);
    }

    public static char succ(char ch) {
        return (char) (ch + 1);
    }

    public static String titleize(String sentence) {
        String trimedSentence = trim(sentence);
        StringBuilder sb = new StringBuilder();
        int length = trimedSentence.length();
        boolean capitalizeNext = true;
        for (int i = 0; i < length; i++) {
            char c = trimedSentence.charAt(i);
            if (CharMatcher.anyOf("_- ").matches(c)) {
                sb.append(c);
                capitalizeNext = true;
            } else if (capitalizeNext) {
                sb.append(Character.toTitleCase(c));
                capitalizeNext = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    public static String camelize(String sentence) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, CharMatcher.anyOf("- ").collapseFrom(trim(sentence), '_'));
    }

    public static String dasherize(String sentence) {
        String to = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, CharMatcher.WHITESPACE.collapseFrom(trim(upperBy_(sentence)), '-'));
        return cleanBy(to, '-');
    }

    private static String upperBy_(String sentence) {
        return Joiner.on('_').join(Splitter.on(BEFORE_UPPER_CASE).split(sentence));
    }

    public static String underscored(String sentence) {
        String to = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_UNDERSCORE, CharMatcher.anyOf("- ").collapseFrom((upperBy_(trim(sentence))), '_'));
        return cleanBy(to, '_');
    }

    private static String cleanBy(String to, char underscore) {
        return CharMatcher.anyOf(String.valueOf(underscore)).collapseFrom(to, underscore);
    }

    public static String classify(String sentence) {
        return capitalize(camelize(sentence));
    }

    public static String humanize(String sentence) {
        return capitalize(replace(underscored(sentence), '_', ' '));
    }

    private static String replace(String sentence, char from, char to) {
        return CharMatcher.anyOf(String.valueOf(from)).replaceFrom(sentence, to);
    }
}
