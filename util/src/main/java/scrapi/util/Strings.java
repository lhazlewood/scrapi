/*
 * Copyright © 2024 io.jsonwebtoken and Les Hazlewood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package scrapi.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Utility methods for working with Strings to reduce pattern repetition and otherwise
 * increased cyclomatic complexity.
 *
 * @since 0.1
 */
public final class Strings {

    /**
     * Empty String, equal to <code>&quot;&quot;</code>.
     */
    public static final String EMPTY = "";

    public static final String COMMA = ",";

    public static final char[] EMPTY_CHARS = EMPTY.toCharArray();

    private static final CharBuffer EMPTY_BUF = CharBuffer.wrap(EMPTY);

    /**
     * Convenience alias for {@link StandardCharsets#UTF_8}.
     */
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    private Strings() {
    } //prevent instantiation

    //---------------------------------------------------------------------
    // General convenience methods for working with Strings
    //---------------------------------------------------------------------

    /**
     * Check that the given CharSequence is neither <code>null</code> nor of length 0.
     * Note: Will return <code>true</code> for a CharSequence that purely consists of whitespace.
     * <pre>
     * Strings.hasLength(null) = false
     * Strings.hasLength("") = false
     * Strings.hasLength(" ") = true
     * Strings.hasLength("Hello") = true
     * </pre>
     *
     * @param str the CharSequence to check (may be <code>null</code>)
     * @return <code>true</code> if the CharSequence is not null and has length
     * @see #hasText(String)
     */
    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    /**
     * Check whether the given CharSequence has actual text.
     * More specifically, returns <code>true</code> if the string not <code>null</code>,
     * its length is greater than 0, and it contains at least one non-whitespace character.
     * <pre>
     * Strings.hasText(null) = false
     * Strings.hasText("") = false
     * Strings.hasText(" ") = false
     * Strings.hasText("12345") = true
     * Strings.hasText(" 12345 ") = true
     * </pre>
     *
     * @param str the CharSequence to check (may be <code>null</code>)
     * @return <code>true</code> if the CharSequence is not <code>null</code>,
     * its length is greater than 0, and it does not contain whitespace only
     * @see java.lang.Character#isWhitespace
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given String has actual text.
     * More specifically, returns <code>true</code> if the string not <code>null</code>,
     * its length is greater than 0, and it contains at least one non-whitespace character.
     *
     * @param str the String to check (may be <code>null</code>)
     * @return <code>true</code> if the String is not <code>null</code>, its length is
     * greater than 0, and it does not contain whitespace only
     * @see #hasText(CharSequence)
     */
    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    /**
     * Check whether the given CharSequence contains any whitespace characters.
     *
     * @param str the CharSequence to check (may be <code>null</code>)
     * @return <code>true</code> if the CharSequence is not empty and
     * contains at least 1 whitespace character
     * @see java.lang.Character#isWhitespace
     */
    public static boolean containsWhitespace(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given String contains any whitespace characters.
     *
     * @param str the String to check (may be <code>null</code>)
     * @return <code>true</code> if the String is not empty and
     * contains at least 1 whitespace character
     * @see #containsWhitespace(CharSequence)
     */
    public static boolean containsWhitespace(String str) {
        return containsWhitespace((CharSequence) str);
    }

    /**
     * Trim leading and trailing whitespace from the given String.
     *
     * @param str the String to check
     * @return the trimmed String
     * @see java.lang.Character#isWhitespace
     */
    public static String trimWhitespace(String str) {
        return (String) trimWhitespace((CharSequence) str);
    }


    private static CharSequence trimWhitespace(CharSequence str) {
        if (!hasLength(str)) {
            return str;
        }
        final int length = str.length();

        int start = 0;
        while (start < length && Character.isWhitespace(str.charAt(start))) {
            start++;
        }

        int end = length;
        while (start < length && Character.isWhitespace(str.charAt(end - 1))) {
            end--;
        }

        return ((start > 0) || (end < length)) ? str.subSequence(start, end) : str;
    }

    /**
     * Returns the specified string without leading or trailing whitespace, or {@code null} if there are no remaining
     * characters.
     *
     * @param str the string to clean
     * @return the specified string without leading or trailing whitespace, or {@code null} if there are no remaining
     * characters.
     */
    public static String clean(String str) {
        CharSequence result = clean((CharSequence) str);
        return result != null ? result.toString() : null;
    }

    /**
     * Returns the specified {@code CharSequence} without leading or trailing whitespace, or {@code null} if there are
     * no remaining characters.
     *
     * @param str the {@code CharSequence} to clean
     * @return the specified string without leading or trailing whitespace, or {@code null} if there are no remaining
     * characters.
     */
    public static CharSequence clean(CharSequence str) {
        str = trimWhitespace(str);
        if (!hasLength(str)) {
            return null;
        }
        return str;
    }

    /**
     * Returns the specified string's UTF-8 bytes, or {@code null} if the string is {@code null}.
     *
     * @param s the string to obtain UTF-8 bytes
     * @return the specified string's UTF-8 bytes, or {@code null} if the string is {@code null}.
     */
    public static byte[] utf8(CharSequence s) {
        if (s == null) return null;
        CharBuffer cb = s instanceof CharBuffer ? (CharBuffer) s : CharBuffer.wrap(s);
        cb.mark();
        ByteBuffer buf = UTF_8.encode(cb);
        int len = buf.limit();
        byte[] bytes = new byte[len];
        buf.get(bytes, 0, len);
        buf.clear().put(new byte[len]); // clear out any buffered bytes (might be a password)
        cb.reset();
        return bytes;
    }

    /**
     * Returns {@code new String(utf8Bytes, StandardCharsets.UTF_8)}.
     *
     * @param utf8Bytes UTF-8 bytes to use with the {@code String} constructor.
     * @return {@code new String(utf8Bytes, StandardCharsets.UTF_8)}.
     */
    public static String utf8(byte[] utf8Bytes) {
        return new String(utf8Bytes, UTF_8);
    }

    /**
     * Returns {@code new String(asciiBytes, StandardCharsets.US_ASCII)}.
     *
     * @param asciiBytes US_ASCII bytes to use with the {@code String} constructor.
     * @return {@code new String(asciiBytes, StandardCharsets.US_ASCII)}.
     */
    public static String ascii(byte[] asciiBytes) {
        return new String(asciiBytes, StandardCharsets.US_ASCII);
    }

    /**
     * Returns the {@link StandardCharsets#US_ASCII US_ASCII}-encoded bytes of the specified {@code CharSequence}.
     *
     * @param s the {@code CharSequence} to encode to {@code US_ASCII}.
     * @return the {@link StandardCharsets#US_ASCII US_ASCII}-encoded bytes of the specified {@code CharSequence}.
     */
    public static byte[] ascii(CharSequence s) {
        if (s == null) return null;
        CharBuffer cb = s instanceof CharBuffer ? (CharBuffer) s : CharBuffer.wrap(s);
        ByteBuffer buf = StandardCharsets.US_ASCII.encode(cb);
        byte[] bytes = new byte[buf.remaining()];
        buf.get(bytes);
        return bytes;
    }

    /**
     * Returns a {@code CharBuffer} that wraps {@code seq}, or an empty buffer if {@code seq} is null. If
     * {@code seq} is already a {@code CharBuffer}, it is returned unmodified.
     *
     * @param seq the {@code CharSequence} to wrap.
     * @return a {@code CharBuffer} that wraps {@code seq}, or an empty buffer if {@code seq} is null.
     */
    public static CharBuffer wrap(CharSequence seq) {
        if (!hasLength(seq)) return EMPTY_BUF;
        if (seq instanceof CharBuffer) return (CharBuffer) seq;
        return CharBuffer.wrap(seq);
    }

    /**
     * Returns a String representation (1s and 0s) of the specified byte.
     *
     * @param b the byte to represent as 1s and 0s.
     * @return a String representation (1s and 0s) of the specified byte.
     */
    public static String toBinary(byte b) {
        String bString = Integer.toBinaryString(b & 0xFF);
        return String.format("%8s", bString).replace((char) Character.SPACE_SEPARATOR, '0');
    }

    /**
     * Returns a String representation (1s and 0s) of the specified byte array.
     *
     * @param bytes the bytes to represent as 1s and 0s.
     * @return a String representation (1s and 0s) of the specified byte array.
     */
    public static String toBinary(byte[] bytes) {
        StringBuilder sb = new StringBuilder(19); //16 characters + 3 space characters
        for (byte b : bytes) {
            if (sb.length() > 0) {
                sb.append((char) Character.SPACE_SEPARATOR);
            }
            String val = toBinary(b);
            sb.append(val);
        }
        return sb.toString();
    }

    /**
     * Returns a hexadecimal String representation of the specified byte array.
     *
     * @param bytes the bytes to represent as a hexidecimal string.
     * @return a hexadecimal String representation of the specified byte array.
     */
    public static String toHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte temp : bytes) {
            if (result.length() > 0) {
                result.append((char) Character.SPACE_SEPARATOR);
            }
            result.append(String.format("%02x", temp));
        }
        return result.toString();
    }

    /**
     * Trim <i>all</i> whitespace from the given String:
     * leading, trailing, and intermediate characters.
     *
     * @param str the String to check
     * @return the trimmed String
     * @see java.lang.Character#isWhitespace
     */
    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * Trim leading whitespace from the given String.
     *
     * @param str the String to check
     * @return the trimmed String
     * @see java.lang.Character#isWhitespace
     */
    public static String trimLeadingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * Trim trailing whitespace from the given String.
     *
     * @param str the String to check
     * @return the trimmed String
     * @see java.lang.Character#isWhitespace
     */
    public static String trimTrailingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Trim all occurrences of the supplied leading character from the given String.
     *
     * @param str              the String to check
     * @param leadingCharacter the leading character to be trimmed
     * @return the trimmed String
     */
    public static String trimLeadingCharacter(String str, char leadingCharacter) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * Trim all occurrences of the supplied trailing character from the given String.
     *
     * @param str               the String to check
     * @param trailingCharacter the trailing character to be trimmed
     * @return the trimmed String
     */
    public static String trimTrailingCharacter(String str, char trailingCharacter) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    /**
     * Returns {@code true} if the given string starts with the specified case-insensitive prefix, {@code false} otherwise.
     *
     * @param str    the String to check
     * @param prefix the prefix to look for
     * @return {@code true} if the given string starts with the specified case-insensitive prefix, {@code false} otherwise.
     * @see java.lang.String#startsWith
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str == null || prefix == null) {
            return false;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        String lcStr = str.substring(0, prefix.length()).toLowerCase();
        String lcPrefix = prefix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }

    /**
     * Returns {@code true} if the given string ends with the specified case-insensitive suffix, {@code false} otherwise.
     *
     * @param str    the String to check
     * @param suffix the suffix to look for
     * @return {@code true} if the given string ends with the specified case-insensitive suffix, {@code false} otherwise.
     * @see java.lang.String#endsWith
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        if (str == null || suffix == null) {
            return false;
        }
        if (str.endsWith(suffix)) {
            return true;
        }
        if (str.length() < suffix.length()) {
            return false;
        }

        String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
        String lcSuffix = suffix.toLowerCase();
        return lcStr.equals(lcSuffix);
    }

    //---------------------------------------------------------------------
    // Convenience methods for working with formatted Strings
    //---------------------------------------------------------------------

    /**
     * Quote the given String with single quotes.
     *
     * @param str the input String (e.g. "myString")
     * @return the quoted String (e.g. "'myString'"),
     * or <code>null</code> if the input was <code>null</code>
     */
    public static String quote(String str) {
        return (str != null ? "'" + str + "'" : null);
    }

    /**
     * Turn the given Object into a String with single quotes
     * if it is a String; keeping the Object as-is else.
     *
     * @param obj the input Object (e.g. "myString")
     * @return the quoted String (e.g. "'myString'"),
     * or the input object as-is if not a String
     */
    public static Object quoteIfString(Object obj) {
        return (obj instanceof String ? quote((String) obj) : obj);
    }

    /**
     * Unqualify a string qualified by a '.' dot character. For example,
     * "this.name.is.qualified", returns "qualified".
     *
     * @param qualifiedName the qualified name
     * @return an unqualified string by stripping all previous text before (and including) the last period character.
     */
    public static String unqualify(String qualifiedName) {
        return unqualify(qualifiedName, '.');
    }

    /**
     * Unqualify a string qualified by a separator character. For example,
     * "this:name:is:qualified" returns "qualified" if using a ':' separator.
     *
     * @param qualifiedName the qualified name
     * @param separator     the separator
     * @return an unqualified string by stripping all previous text before and including the last {@code separator} character.
     */
    public static String unqualify(String qualifiedName, char separator) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
    }

    /**
     * Capitalize a <code>String</code>, changing the first letter to
     * upper case as per {@link Character#toUpperCase(char)}.
     * No other letters are changed.
     *
     * @param str the String to capitalize, may be <code>null</code>
     * @return the capitalized String, <code>null</code> if null
     */
    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    /**
     * Uncapitalize a <code>String</code>, changing the first letter to
     * lower case as per {@link Character#toLowerCase(char)}.
     * No other letters are changed.
     *
     * @param str the String to uncapitalize, may be <code>null</code>
     * @return the uncapitalized String, <code>null</code> if null
     */
    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str == null || str.length() == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        if (capitalize) {
            sb.append(Character.toUpperCase(str.charAt(0)));
        } else {
            sb.append(Character.toLowerCase(str.charAt(0)));
        }
        sb.append(str.substring(1));
        return sb.toString();
    }


    /**
     * Appends a space character (<code>' '</code>) if the argument is not empty, otherwise does nothing.  This method
     * can be thought of as &quot;non-empty space&quot;.  Using this method allows reduction of this:
     * <blockquote><pre>
     * if (sb.length != 0) {
     *     sb.append(' ');
     * }
     * sb.append(nextWord);</pre></blockquote>
     * <p>To this:</p>
     * <blockquote><pre>
     * nespace(sb).append(nextWord);</pre></blockquote>
     *
     * @param sb the string builder to append a space to if non-empty
     * @return the string builder argument for method chaining.
     * @since 0.12.0
     */
    public static StringBuilder nespace(StringBuilder sb) {
        if (sb == null) {
            return null;
        }
        if (sb.length() != 0) {
            sb.append(' ');
        }
        return sb;
    }

}


