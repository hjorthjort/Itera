import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by hjort on 12/11/15.
 */
public class AnagramTest {

    private static String[] commonEnglishWords = getCommonWords();
    private static String[] longAnagrams = getLongAnagrams(1000, 10000);
    private static String[] longRandomStrings = getRandomStrings(1000, 10000);

    public static void runTests() {

        SortingAnagramMap sortAM = new SortingAnagramMap();
        HashAnagramMap hashAM = new HashAnagramMap();
        PrimesAnagramMap primesAM = new PrimesAnagramMap();

        String[] originalTest = {"niste", "stien", "allfarveien", "konsert", "torsken", "stein"};
        System.out.println(new SortingAnagramMap().createMap(originalTest));
        System.out.println(new HashAnagramMap().createMap(originalTest));
        System.out.println(new PrimesAnagramMap().createMap(originalTest));


        //Run sorting algortihm

        long startCES = System.nanoTime();
        Map<String, List<String>> commonEnglishSort = sortAM.createMap(commonEnglishWords);
        long endCES = System.nanoTime();
        long runtimeCES = endCES - startCES;

        long startLAS = System.nanoTime();
        Map longAnagramsSort = sortAM.createMap(longAnagrams);
        long endLAS = System.nanoTime();
        long runtimeLAS = endLAS - startLAS;

        long startRSS = System.nanoTime();
        Map randomStringsSort = sortAM.createMap(longRandomStrings);
        long endRSS = System.nanoTime();
        long runtimeRSS = endRSS - startRSS;

        //Run hash map algortihm

        long startCEH = System.nanoTime();
        Map<Map<Character, Integer>, List<String>> commonEnglishHash = hashAM.createMap(commonEnglishWords);
        long endCEH = System.nanoTime();
        long runtimeCEH = endCEH - startCEH;

        long startLAH = System.nanoTime();
        Map longAnagramsHash = hashAM.createMap(longAnagrams);
        long endLAH = System.nanoTime();
        long runtimeLAH = endLAH - startLAH;

        long startRSH = System.nanoTime();
        Map randomStringsHash = hashAM.createMap(longRandomStrings);
        long endRSH = System.nanoTime();
        long runtimeRSH = endRSH - startRSH;

        //Run prime numbers algorithm

        long startCEP = System.nanoTime();
        Map<BigInteger, List<String>> commonEnglishPrimes = primesAM.createMap(commonEnglishWords);
        long endCEP = System.nanoTime();
        long runtimeCEP = endCEP - startCEP;

        long startLAP = System.nanoTime();
        Map longAnagramsPrime = primesAM.createMap(longAnagrams);
        long endLAP = System.nanoTime();
        long runtimeLAP = endLAP - startLAP;

        long startRSP = System.nanoTime();
        Map randomStringsPrimes = primesAM.createMap(longRandomStrings);
        long endRSP = System.nanoTime();
        long runtimeRSP = endRSP - startRSP;

        Map<String, String> results = new LinkedHashMap<>();
        results.put("Common english, sort", "" + (runtimeCES / 1000000));
        results.put("Common english, hash", "" + (runtimeCEH / 1000000));
        results.put("Common english, primes", "" + (runtimeCEP / 1000000));
        results.put("Long anagrams, sort", "" + (runtimeLAS / 1000000));
        results.put("Long anagrams, hash", "" + (runtimeLAH / 1000000));
        results.put("Long anagrams, primes", "" + (runtimeLAP / 1000000));
        results.put("Random strings, sort", "" +   (runtimeRSS / 1000000));
        results.put("Random strings, hash", "" +   (runtimeRSH / 1000000));
        results.put("Random strings, primes", "" + (runtimeRSP / 1000000));

        //Logging
        try {

            Logger logger = new Logger("test_results.md");
            String[] tableOfContents = {
                    "<a href='#performance'>Performance</a>",
                    "<a href='#sorting'>Sorting results</a>",
                    "<a href='#hashres'>Hash map results</a>",
                    "<a href='#primesres'>Prime numbers results</a>"
            };

            logger.writeBulletlist(tableOfContents);

            logger.writeAnchor("performance");
            logger.writeHeader("Performance", 1);

            logger.writeParagraph("These are the running times of the different algorithms, on the different data sets");

            logger.writeTableMap("Algorithm, data set", "Running time (ms)", results);

            logger.writeAnchor("sorting");
            logger.writeHeader("Output from grouping ordinary words, sorting algorithm", 2);
            logger.writeTableMap("Key", "Value", commonEnglishSort);

            logger.writeAnchor("hash");
            logger.writeHeader("Output from grouping ordinary words, hash map algorithm", 2);
            logger.writeTableMap("Key", "Value", commonEnglishHash);

            logger.writeAnchor("sorting");
            logger.writeHeader("Output from grouping ordinary words, primes algorithm", 2);
            logger.writeTableMap("Key", "Value", commonEnglishPrimes);

            logger.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Testing finished");

    }

    //GENERATE TEST DATA

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

        char[] baseCharArray = getRandomString(wordLength).toCharArray();
        String[] returnArray = new String[arraySize];
        for (int i = 0; i < arraySize; i++) {
            shuffleArray(baseCharArray);
            returnArray[i] = new String(baseCharArray);
        }
        return returnArray;
    }

    /**
     * Get an array of desired number random strings of desired length
     * @param wordLength lenght of each string
     * @param numberOfWords number of strings in the array
     * @return an array of size numberOfWords with strings of length wordLength
     */
    private static String[] getRandomStrings(int wordLength, int numberOfWords) {
        String[] retArray = new String[numberOfWords];
        for (int i = 0; i < numberOfWords; i++) {
            retArray[i] = getRandomString(wordLength);
        }
        return retArray;
    }

    /**
     * Get a string of desired length, consisting of random letters a-z
     * @param wordLength
     * @return
     */
    private static String getRandomString(int wordLength) {
        Random rand = new Random();
        char[] stringCharArray = new char[wordLength];
        for (int i = 0; i < wordLength; i++) {
            stringCharArray[i] = (char) (rand.nextInt(26) + 'a');
        }
        return new String(stringCharArray);
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
