import java.util.*;

public class SongBuilder{

    LinkedList<String> songWords = new LinkedList<>();
    ArrayList<String> song;

    public LinkedList<String> createSong() {

        NGramMatcher matcher = new NGramMatcher(3);

        if(songWords.size() > 3) {
            songWords.removeLast();
        }

        String nextWord = matcher.getNextWord(songWords);

        songWords.add(nextWord);
        song.add(nextWord);

        return songWords;
    }

}
