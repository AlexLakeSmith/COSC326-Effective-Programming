import java.util.Comparator;

/**
 * Comparator class for sorting an arraylist of words in decreasing order of length, and in
 * alphabetical order, if two words happen to be of the same length.
 * 
 * @author Mathew Shields, Alex Lake-Smith
 */
public class LengthComparator implements Comparator<String> {
    @Override

    /**
     * Compares two strings.
     *
     * @param Two strings
     * @return Either the difference in length of the two strings, or the difference in value between
     * the two strings, if they are of the same length.
     */
    public int compare (String s1, String s2) {
        if (s1.length() != s2.length()) {
            return s2.length() - s1.length();
        }
        return s1.compareTo(s2);
    }
}
