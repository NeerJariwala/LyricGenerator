import java.util.*;

public class SongBuilder{

    public ArrayList<String> createSong(NGramMatcher nGramMatcher) {
        LinkedList<String> songWords = new LinkedList<>();
        ArrayList<String> song = new ArrayList<>();

        for (int i = 0; i < 50; i++) {

            String nextWord = nGramMatcher.getNextWord(songWords);

            songWords.add(nextWord);
            song.add(nextWord);

            if (songWords.size() > nGramMatcher.getN() - 1) {
                songWords.poll();
            }
        }

        return song;
    }

}
