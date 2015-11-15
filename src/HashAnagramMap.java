import java.util.HashMap;
import java.util.Map;

/**
 * @author hjorthjort
 */
public class HashAnagramMap extends AnagramMap<Map<Character, Integer>> {

    @Override
    public Map<Character, Integer> getKey(String string) {
        Map<Character, Integer> returnMap = new HashMap<>();
        for (char c : string.toCharArray()) {
            if (returnMap.containsKey(c)) {
                int occurences = returnMap.get(c);
                occurences++;
                returnMap.put(c, occurences);
            } else {
                returnMap.put(c, 1);
            }
        }
        return returnMap;
    }
}
