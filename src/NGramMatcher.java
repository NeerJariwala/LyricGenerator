import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public class NGramMatcher {

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
