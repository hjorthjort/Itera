import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hjort on 14/11/15.
 */
public class SortingAnagramMap extends AnagramMap<String> {
    /**
     *
     * @param string to be sorted
     * @return a sorted version of this string
     */
    public String getKey(String s) {
        //Sort the letters in the word
        char[] charArray = s.toCharArray();
        Arrays.sort(charArray);
        String sortedString = new String(charArray);
        return sortedString;
    }
}
