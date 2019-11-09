import java.util.*;

public class NGramMatcher {

    // List of all the NGrams, organized in an outer list of 1-grams, 2-grams, ..., n-grams
    List<List<NGram>> allNGrams;

    public void makeNGramsFromInput(String[] words, int n) {
        // Queue of the recent seen words in order
        Queue<String> recentWords = new LinkedList<>();

        for (String word : words) {
            // Add most recent word to start of queue
            recentWords.add(word);

            if (recentWords.size() > n) {
                addNGrams(recentWords.toArray(new String[0]));
            }

        }
    }

    private void addNGrams(String[] words) {
        // Terminal condition: no more words
        if (words.length == 1) {
            addNGram(words);
            return;
        }

        // Add n-gram for this chain and recursive call on the next
        addNGram(words);
        addNGrams(Arrays.copyOfRange(words, 1, words.length - 1));
    }

    private void addNGram(String[] words) {
        int numWords = words.length;

        if (numWords == 1) {
            // Add the word occurrence to the only existing 1-gram
            allNGrams.get(numWords).get(0).addWordOccurrence(words[0]);
        } else {
            // Get previous words
            String[] prevWords = Arrays.copyOfRange(words, 0, words.length - 2);

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

}

class NGram {

    String[] prev;
    HashMap<String, Integer> nextWeights;
    int n;

    NGram(String[] prev, String next) {
        this.prev = prev;
        this.nextWeights.put(next, 1);
        this.n = prev.length + 1;
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
