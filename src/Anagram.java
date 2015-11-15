import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by hjort on 12/11/15.
 */
public class Anagram {

    private static String[] commonEnglishWords = getCommonWords();
    private static String[] longAnagrams = getLongAnagrams(100, 10000);
    //TODO Add test case with many random strings, no or few anagrams

    public static void runTests() {

        SortingAnagramMap sortAM = new SortingAnagramMap();
        HashAnagramMap hashAM = new HashAnagramMap();
        PrimesAnagramMap primesAM = new PrimesAnagramMap();

        String[] originalTest = {"niste", "stien", "allfarveien", "konsert", "torsken", "stein"};
        System.out.println(new SortingAnagramMap().createMap(originalTest));
        System.out.println(new HashAnagramMap().createMap(originalTest));
        System.out.println(new PrimesAnagramMap().createMap(originalTest));

        long startCES = System.nanoTime();
        Map<String, List<String>> commonEnglishSort = sortAM.createMap(commonEnglishWords);
        long endCES = System.nanoTime();
        long runtimeCES = endCES - startCES;

        long startLAS = System.nanoTime();
        Map longAnagramsSort = sortAM.createMap(longAnagrams);
        long endLAS = System.nanoTime();
        long runtimeLAS = endLAS - startLAS;

        long startCEH = System.nanoTime();
        Map<Map<Character, Integer>, List<String>> commonEnglishHash = hashAM.createMap(commonEnglishWords);
        long endCEH = System.nanoTime();
        long runtimeCEH = endCEH - startCEH;

        long startLAH = System.nanoTime();
        Map longAnagramsHash = hashAM.createMap(longAnagrams);
        long endLAH = System.nanoTime();
        long runtimeLAH = endLAH - startLAH;

        long startCEP = System.nanoTime();
        Map<BigInteger, List<String>> commonEnglishPrime = primesAM.createMap(commonEnglishWords);
        long endCEP = System.nanoTime();
        long runtimeCEP = endCEP - startCEP;

        long startLAP = System.nanoTime();
        Map longAnagramsPrime = primesAM.createMap(longAnagrams);
        long endLAP = System.nanoTime();
        long runtimeLAP = endLAP - startLAP;


        //Logging
        try {
            PrintWriter writer = new PrintWriter("log.md", "UTF-8");

            writer.println("* <a href='#performance'>Performance</a>");
            writer.println("* <a href='#sortingres'>Sorting results</a>");
            writer.println("* <a href='#hashres'>Hash map results</a>");
            writer.println("* <a href='#primesres'>Prime numbers results</a>");
            writer.println();
            writer.println("<a name='performance'></a>Performance (unit = milliseconds)");
            writer.println("=============================\n");
            writer.println("* Common english, sort: " + (runtimeCES / 1000000));
            writer.println("* Common english, hash: " + (runtimeCEH / 1000000));
            writer.println("* Common english, primes: " + (runtimeCEP / 1000000));
            writer.println("* Long anagrams, sort: " + (runtimeLAS / 1000000));
            writer.println("* Long anagrams, hash: " + (runtimeLAH / 1000000));
            writer.println("* Long anagrams, primes: " + (runtimeLAP / 1000000));

            writer.println();
            writer.println("<a name='sortingres'></a>Result from sorting algorithm");
            writer.println("=============================\n");
            writer.println("| key | values |");
            writer.println("|---|--------|");
            for (Map.Entry<String, List<String>> entry : commonEnglishSort.entrySet()) {
                writer.println("| " + entry.getKey() + " | " + entry.getValue().toString() + " |");
            }
            writer.println();
            writer.println("<a name='hashres'></a>Result from hashmap algorithm");
            writer.println("=============================\n");
            writer.println("| key | values |");
            writer.println("|---|--------|");
            for (Map.Entry<Map<Character, Integer>, List<String>> entry : commonEnglishHash.entrySet()) {
                writer.println("| " + entry.getKey() + " | " + entry.getValue().toString() + " |");
            }
            writer.println();
            writer.println("<a name='primesres'></a>Result from primes algorithm");
            writer.println("=============================\n");
            writer.println("| key | values |");
            writer.println("|---|--------|");
            for (Map.Entry<BigInteger, List<String>> entry : commonEnglishPrime.entrySet()) {
                writer.println("| " + entry.getKey() + " | " + entry.getValue().toString() + " |");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Testing finished");

    }

    //Methods for generating testing data

    /**
     * Get a whole bunch of ordinary words
     *
     * @return
     */
    private static String[] getCommonWords() {
        List<String> words = new LinkedList<>();
        try {
            Scanner sc = new Scanner(new File("/usr/share/dict/words"));
            while (sc.hasNext()) {
                words.add(sc.next());
            }
            sc = new Scanner(new File("/usr/share/dict/propernames"));
            while (sc.hasNext()) {
                words.add(sc.next());
            }
            sc = new Scanner(new File("/usr/share/dict/connectives"));
            while (sc.hasNext()) {
                words.add(sc.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return words.toArray(new String[1]);
    }

    /**
     * Get an array of very long words with
     */
    private static String[] getLongAnagrams(int wordLength, int arraySize) {
        // Keep the heap clean by creating a single random-obejct that we use consistently
        Random rand = new Random();
        char[] baseCharArray = new char[wordLength];
        for (int i = 0; i < wordLength; i++) {
            baseCharArray[i] = (char) (rand.nextInt(26) + 'a');
        }

        String[] returnArray = new String[arraySize];
        for (int i = 0; i < arraySize; i++) {
            shuffleArray(baseCharArray);
            returnArray[i] = new String(baseCharArray);
        }

        return returnArray;
    }

    /**
     * Shuffle the array using Fisher–Yates algorithm, which has O(n) complexity.
     */
    private static void shuffleArray(char[] charArray) {
        Random rand = new Random();
        for (int i = charArray.length - 1; i >= 0; i--) {
            int randomCharNbr = rand.nextInt(i + 1);
            char randomChar = charArray[randomCharNbr];
            charArray[randomCharNbr] = charArray[i];
            charArray[i] = randomChar;
        }
    }
}
