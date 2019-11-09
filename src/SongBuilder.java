import java.util.*;

public class SongBuilder{

    public ArrayList<String> createSong(NGramMatcher nGramMatcher) {
        LinkedList<String> songWords = new LinkedList<>();
        ArrayList<String> song = new ArrayList<>();

        if(songWords.size() > nGramMatcher.getN()) {
            songWords.poll();
        }

        String nextWord = nGramMatcher.getNextWord(songWords);

        songWords.add(nextWord);
        song.add(nextWord);

        return song;
    }

}
