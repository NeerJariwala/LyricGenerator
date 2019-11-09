import java.util.*;

class NGramMatcher {

    // List of all the NGrams
    // Organized in an outer list of 1-grams, 2-grams, ..., n-grams
    private List<List<NGram>> allNGrams = new ArrayList<>();

    // The max length for the n-grams stored
    private int n;

    /**
     * Constructs an NGramMatcher that will create NGrams up to length n
     * @param n Maximum length of NGrams to create
     */
    NGramMatcher(int n) {
        this.n = n;
        for (int i = 0; i < n + 1; i++) {
            allNGrams.add(new ArrayList<>());
        }
    }

    /**
     * Construct all of the NGram data from an array of contiguous words
     * @param input Words to construct NGrams from
     */
    void makeNGramsFromInput(String[] input) {
        // Queue of the recent seen words in order
        Queue<String> recentWords = new LinkedList<>();

        for (String word : input) {
            // Add most recent word to start of queue
            recentWords.add(word);

            // Ensure n or fewer recent words
            while (recentWords.size() > n) {
                recentWords.poll();
            }

            // Add the grams
            addNGrams(recentWords.toArray(new String[0]));
        }
    }

    /**
     * Recursively adds all of the chains made by the array of words by
     * removing one word from the front of the array for each call
     * @param words Words to add NGrams from
     * @return Number of NGrams added
     */
    private int addNGrams(String[] words) {
        // Terminal condition: no more words
        if (words.length == 1) {
            addNGram(words);
            return 1;
        }

        // Add n-gram for this chain and recursive call on the next
        addNGram(words);
        return addNGrams(Arrays.copyOfRange(words, 1, words.length)) + 1;
    }

    /**
     * Adds the given NGram, or adds a next word occurrence if an NGram with
     * this pattern of words already exists
     * @param words Chain of words to add as an NGram
     */
    private void addNGram(String[] words) {
        int numWords = words.length;
        String[] prevWords;

        if (numWords == 1) {
            // No previous words with only one word
            prevWords = new String[0];
        } else {
            // Get previous words
            prevWords = Arrays.copyOfRange(words, 0, numWords - 1);
        }
        // Check if an n-gram with these previous words exists
        for (NGram nGram : allNGrams.get(numWords)) {
            if (nGram.matchPrev(prevWords)) {
                // Found one, add occurrence of next word and return
                nGram.addWordOccurrence(words[numWords - 1]);
                return;
            }
        }

        // Couldn't find one, need to create it
        NGram newGram = new NGram(prevWords, words[numWords - 1]);
        allNGrams.get(numWords).add(newGram);
    }

    /**
     * Prints out all of the NGrams recorded by this NGramMatcher
     */
    void printNGrams() {
        for (List<NGram> nGramList : allNGrams) {
            for (NGram nGram : nGramList) {
                System.out.println(nGram + " " + nGram.getNextWeights());
            }
        }
    }

}

/**
 * Class that represents n-grams (chains of n words). Stores n-1 words that
 * come a map that stores all of the potential next words and their weights
 * used for generating words that follow that chain.
 */
class NGram {

    // The chain of words that comes before
    private String[] prev;

    // The weights of the words that could possibly follow the previous chain
    private HashMap<String, Integer> nextWeights = new HashMap<>();

    // Length of chain, including next word
    private int n;

    /**
     * Constructs an NGram with the given chain of words.
     * Begins the map of weights by adding this next word
     * @param prev Words that come previously in the chain
     * @param next Single word that follows in the chain
     */
    NGram(String[] prev, String next) {
        this.prev = prev;
        this.n = prev.length + 1;
        addWordOccurrence(next);
    }

    /**
     * Checks if the previous words in the NGram match the given array of words
     * @param otherPrev Chain of words to check
     * @return true if the chains of words match, false otherwise
     */
    boolean matchPrev(String[] otherPrev) {
        // Store number of previous words to match
        int prevLength = n - 1;

        // Check if matching same number of words
        if (otherPrev.length != prevLength) {
            return false;
        }

        for (int i = 0; i < prevLength; i++) {
            if (!this.prev[i].equals(otherPrev[i])) {
                return false;
            }
        }

        // Arrays of Strings match
        return true;
    }

    /**
     * Increments the number of occurrences of this word in the weight map.
     * Creates a new entry in the map if the word is new.
     * @param word Word that follows the previous words in the NGram
     */
    void addWordOccurrence(String word) {
        int numOccurrences = 1;

        if (nextWeights.containsKey(word)) {
            // Word has been seen before, increment count
            numOccurrences = nextWeights.get(word) + 1;
            nextWeights.replace(word, numOccurrences);
        } else {
            // New word, create new entry
            nextWeights.put(word, numOccurrences);
        }
    }

    /**
     * Gets the words that make up the beginning of this NGram
     * @return Previous words in the NGram
     */
    public String[] getPrev() {
        return prev;
    }

    /**
     * Get the map of weights for what words could possibly follow this NGram's
     * previous words
     * @return Map of words and their weights
     */
    HashMap<String, Integer> getNextWeights() {
        return nextWeights;
    }

    @Override
    public String toString() {
        String outStr = "";

        // Put the words in the chain together to make one String
        for (int i = 0; i < prev.length; i++) {
            outStr += prev[i];
            if (i < prev.length - 1) {
                outStr += " ";
            }
        }

        return outStr;
    }

}
