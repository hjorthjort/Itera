import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hjorthjort
 */
public class PrimesAnagramMap extends AnagramMap<BigInteger> {

    private static Map<Character, Integer> primes = new HashMap<>();
    private static int largestPrime = 2;
    private static final int INITIAL_PRIMELIST_SIZE = 1000;

    public PrimesAnagramMap() {
        if (primes.size() < INITIAL_PRIMELIST_SIZE) {
            initiatePrimes();
        }
    }

    @Override
    public BigInteger getKey(String s) {
        char[] charArray = s.toCharArray();
        BigInteger primeRepresentation = BigInteger.ONE;
        for (char c : charArray) {
            BigInteger prime = new BigInteger(Integer.toString(getPrime(c)));
            primeRepresentation = primeRepresentation.multiply(prime);
        }
        return primeRepresentation;
    }

    /**
     * Get the prime number assigned to the given character.
     * @param c
     * @return a prime number unique for the given character
     */
    private static int getPrime(char c) {
        if (primes.containsKey(c)) {
            return primes.get(c);
        } else {
            return generatePrime(c);
        }
    }

    /**
     * Generate a new unique prime number and associate it with the given character.
     * @param c
     * @return
     */
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

    /**
     * Pre-create many primes to save time during execution
     */
    private static void initiatePrimes() {
        for (int i = 0; i < INITIAL_PRIMELIST_SIZE; i++) {
            generatePrime((char) i);
        }
    }

}
