import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hjort on 12/11/15.
 */
public class Anagram {

    private static String[] commonEnglishWords = getCommonWords();
    private static String[] longAnagrams = getLongAnagrams(100, 10000);
    private static Map<Character, Integer> primes = new HashMap<>();
    private static int largestPrime = 2;


    public static void runTests() {

        initiatePrimes();

        String[] originalTest = {"niste", "stien", "allfarveien", "konsert", "torsken", "stein"};
        System.out.println(anagramMapSortMethod(originalTest));
        System.out.println(anagramMapHashMethod(originalTest));
        System.out.println(anagramMapPrimesMethod(originalTest));

        long startCES = System.nanoTime();
        Map<String, List<String>> commonEnglishSort = new SortingAnagramMap().createMap(commonEnglishWords);
        long endCES = System.nanoTime();
        long runtimeCES = endCES - startCES;

        long startLAS = System.nanoTime();
        Map longAnagramsSort = new SortingAnagramMap().createMap(longAnagrams);
        long endLAS = System.nanoTime();
        long runtimeLAS = endLAS - startLAS;

        long startCEH = System.nanoTime();
        Map<Map<Character, Integer>, List<String>>  commonEnglishHash = anagramMapHashMethod(commonEnglishWords);
        long endCEH = System.nanoTime();
        long runtimeCEH = endCEH - startCEH;

        long startLAH = System.nanoTime();
        Map longAnagramsHash = anagramMapHashMethod(longAnagrams);
        long endLAH = System.nanoTime();
        long runtimeLAH = endLAH - startLAH;

        long startCEP = System.nanoTime();
        Map<BigInteger, List<String>> commonEnglishPrime = anagramMapPrimesMethod(commonEnglishWords);
        long endCEP = System.nanoTime();
        long runtimeCEP = endCEP - startCEP;

        long startLAP = System.nanoTime();
        Map longAnagramsPrime = anagramMapPrimesMethod(longAnagrams);
        long endLAP = System.nanoTime();
        long runtimeLAP = endLAP - startLAP;


        //Logging
        try {
            PrintWriter writer = new PrintWriter("log.txt", "UTF-8");

            writer.println("Performance (unit = milliseconds");
            writer.println("=============================\n");
            writer.println("Common english, sort: " + (runtimeCES / 1000000));
            writer.println("Common english, hash: " + (runtimeCEH / 1000000));
            writer.println("Common english, primes: " + (runtimeCEP / 1000000));
            writer.println("Long anagrams, sort: " + (runtimeLAS / 1000000));
            writer.println("Long anagrams, hash: " + (runtimeLAH / 1000000));
            writer.println("Long anagrams, primes: " + (runtimeLAP / 1000000));

            writer.println("\n");
            writer.println("Result from sorting algorithm");
            writer.println("=============================\n");
            for (Map.Entry<String, List<String>> entry : commonEnglishSort.entrySet()) {
                writer.println(entry.getKey() + ": " + entry.getValue().toString());
            }
            writer.println("\n");
            writer.println("Result from hashmap algorithm");
            writer.println("=============================\n");
            for (Map.Entry<Map<Character, Integer>, List<String>> entry : commonEnglishHash.entrySet()) {
                writer.println(entry.getKey() + ": " + entry.getValue().toString());
            }
            writer.println("\n");
            writer.println("Result from primes algorithm");
            writer.println("=============================\n");
            for (Map.Entry<BigInteger, List<String>> entry : commonEnglishPrime.entrySet()) {
                writer.println(entry.getKey() + ": " + entry.getValue().toString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Testing finished");

    }

    /**
     * Pre-create many primes to save time during execution
     *
     */
    private static void initiatePrimes() {
        for (int i = 0; i < 1000; i++) {
            generatePrime((char) i);
        }
    }

    //SORT METHOD

    /**
     * Return a map of anagrams created by sorting every words by characters, and put all words with the same ordering
     * in a list with the same key. The key for every list is the sorted word.
     *
     * @param inputStrings
     * @return
     */
    public static Map<String, List<String>> anagramMapSortMethod(String[] inputStrings) {
        Map<String, List<String>> returnMap =
                Arrays.asList(inputStrings)
                        .stream()
                        .collect(Collectors.groupingBy(s -> getSortedVersion(s)));
        return (HashMap<String, List<String>>)removeDuplicates(returnMap);

    }

    /**
     *
     * @param string to be sorted
     * @return a sorted version of this string
     */
    private static String getSortedVersion(String s) {
        //Sort the letters in the word
        char[] charArray = s.toCharArray();
        Arrays.sort(charArray);
        String sortedString = new String(charArray);
        return sortedString;
    }

    //HASH METHOD

    public static Map<Map<Character, Integer>, List<String>> anagramMapHashMethod(String[] inputStrings) {

        Map<Map<Character, Integer>, List<String>> returnMap =
                Arrays.asList(inputStrings)
                        .stream()
                        .collect(Collectors.groupingBy(s -> getCharacterCount(s)));
        return (HashMap<Map<Character, Integer>, List<String>>)removeDuplicates(returnMap);
    }

    /**
     *
     * @param string to be mapped
     * @return a map containing the characters of the string as keys, and occurences as value
     */
    private static Map<Character, Integer> getCharacterCount(String string) {
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

    //PRIMES

    public static Map<BigInteger, List<String>> anagramMapPrimesMethod(String[] inputStrings) {
        Map<BigInteger, List<String>> returnMap =
                Arrays.asList(inputStrings)
                        .stream()
                        .collect(Collectors.groupingBy(s -> getPrimeRepresentation(s)));

        return (HashMap<BigInteger, List<String>>)removeDuplicates(returnMap);
    }

    /**
     *
     * @param string to be primalized
     * @return the product of the characters in the string, where each characters has been assigned a unique prime number
     */
    private static BigInteger getPrimeRepresentation(String s) {
        char[] charArray = s.toCharArray();
        BigInteger primeRepresentation = BigInteger.ONE;
        for (char c : charArray) {
            try {
                BigInteger prime = new BigInteger(Integer.toString(getPrime(c)));
                primeRepresentation = primeRepresentation.multiply(prime);
            } catch (NullPointerException e) {
                System.out.println(c);
            }
        }
        return primeRepresentation;
    }

    private static int getPrime(char c) {
        if (primes.containsKey(c)) {
            return primes.get(c);
        } else {
            return generatePrime(c);
        }
    }

    private static int generatePrime(char c) {
        for (int j = largestPrime + 1; ; j++) {
            if (isPrime(j)) {
                primes.put(c, j);
                largestPrime = j;
                break;
            }
        }
        return largestPrime;
    }

    private static boolean isPrime(int p) {
        if (p % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= p; i += 2) {
            if (p % i == 0)
                return false;
        }
        return true;
    }
    //UTILITY

    /**
     * Using the Java Stream api, filter the map so that we only have entries with at least two words (words that had anagrams
     * in the list), and then make it a new map.
     *
     * @param map
     */
    private static Map<?, List<String >> removeDuplicates(Map<?, List<String>> map) {
        map = map.entrySet()
                .stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
        return map;
    }

    //TESTING
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
