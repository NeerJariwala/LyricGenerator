import java.util.*;

public class NGramMatcher {

    // List of all the NGrams, organized in an outer list of 1-grams, 2-grams, ..., n-grams
    private List<List<NGram>> allNGrams = new ArrayList<>();

    // The max length for the n-grams stored
    private int n;

    public NGramMatcher(int n) {
        this.n = n;
        for (int i = 0; i < n + 1; i++) {
            allNGrams.add(new ArrayList<>());
        }
    }

    public void makeNGramsFromInput(String[] words) {
        // Queue of the recent seen words in order
        Queue<String> recentWords = new LinkedList<>();

        for (String word : words) {
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

}

class NGram {

    private String[] prev;
    private HashMap<String, Integer> nextWeights = new HashMap<>();
    private int n;

    NGram(String[] prev, String next) {
        this.prev = prev;
        this.n = prev.length + 1;
        addWordOccurrence(next);
    }

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

    void addWordOccurrence(String word) {
        int numOccurrences = 1;

        if (nextWeights.containsKey(word)) {
            numOccurrences = nextWeights.get(word) + 1;
            nextWeights.replace(word, numOccurrences);
        } else {
            nextWeights.put(word, numOccurrences);
        }
    }

    @Override
    public String toString() {
        String outStr = "";

        // Put the words in the chain together to make one String
        for (String w : prev) {
            outStr += w + " ";
        }

        return outStr.substring(0, outStr.length() - 1);
    }

}
