import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hjort on 14/11/15.
 */
public abstract class AnagramMap<K> {
    public Map<K, List<String>> createMap(String[] inputStrings) {
        Map<K, List<String>> returnMap =
                Arrays.asList(inputStrings)
                        .stream()
                        .collect(Collectors.groupingBy(s -> getKey(s)));
        return (HashMap<K, List<String>>)removeDuplicates(returnMap);
    }

     /**
     * Using the Java Stream api, filter the map so that we only have entries with at least two words (words that had anagrams
     * in the list), and then make it a new map.
     *
     * @param map
     */
    private Map<K, List<String >> removeDuplicates(Map<K, List<String>> map) {
        map = map.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        return map;
    }

    protected abstract K getKey(String string);

}
