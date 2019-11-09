import java.util.*;

public class SongBuilder{

    public LinkedList<String> songWords = new LinkedList<>();
    public ArrayList<String> song;

    public ArrayList<String> createSong(NGramMatcher nGramMatcher) {

        if(songWords.size() > nGramMatcher.getN()) {
            songWords.poll();
        }

        String nextWord = nGramMatcher.getNextWord(songWords);

        songWords.add(nextWord);
        song.add(nextWord);

        return song;
    }

}
