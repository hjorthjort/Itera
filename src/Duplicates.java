import java.util.HashSet;
import java.util.Set;

/**
 * Created by hjort on 14/11/15.
 */
public class Duplicates {

    public static boolean containsDuplicates(int[] array) {
        Set<Integer> set = new HashSet<>();
        for (int number: array) {
            if (!set.add(number)) {
                return true;
            }
        }
        return false;
    }
}
