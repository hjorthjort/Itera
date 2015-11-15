# Itera

Testing 3 different algorithms for grouping anagrams together. Each algorithm creates a unique 
and identical key for all words which are anagrams of each other.

## Algorithms

### Sorting algorithm

The sorting algorithm sorts all the characters in a string and uses this as a key which to group anagrams by.

### Hash map algorithm

The hash map algorithm creates a hash map with all characters in a word as key, and the number of occurences of each character as value.

### Prime numbers algorithm

The prime numbers algorithm assigns a unique prime number to each character, and obtains the key by multiplying all the primes in a word.

## Test data sets

### Common english words

Uses the standard dictionary of any Unix system: the `/usr/share/dict` files `words`, `connectives` and `propernames`.

### Long anagrams
 
A set of many very long words which are all anagrams of each other. These are genrated by first creating long string of random characters, then shuffling them to obtain new strings.

## Results

The tests suggest that the sorting algorithm shines for many short, simple words, whereas the hash map algorithm is excellent at handling many very long, similar words. The primes algorithm consistently performs worse than the sorting algortihm.

To see the output (in markdown format) of these algorithms on a complete unix dictionary, with 
some data on performance time, go to [test_results.md](https://raw.githubusercontent.com/hjorthjort/Itera/master/test_results.md).

Just running times can be found in the [wiki](https://github.com/hjorthjort/Itera/wiki).
