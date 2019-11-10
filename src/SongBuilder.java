import java.util.*;

public class SongBuilder{

    public ArrayList<String> createSong(NGramMatcher nGramMatcher) {
        LinkedList<String> songWords = new LinkedList<>();
        ArrayList<String> song = new ArrayList<>();
        Random s = new Random();
        int stanzas = s.nextInt(8 - 5 + 1) + 5;

        for (int k = 0; k < stanzas; k++) {
            Random l = new Random();
            int lines = l.nextInt(9 - 5 + 1) + 5;

            for (int j = 0; j < lines; j++) {
                Random r = new Random();
                int randomN = r.nextInt(9 - 7 + 1) + 7;

                for (int i = 0; i < randomN; i++) {
                    String nextWord = nGramMatcher.getNextWord(songWords);
                    if(i == 0) {
                        nextWord = nextWord.substring(0, 1).toUpperCase() + nextWord.substring(1);
                    }
                    songWords.add(nextWord);
                    song.add(nextWord);
                    song.add(" ");


                    if (songWords.size() > nGramMatcher.getN() - 1) {
                        songWords.poll();
                    }
                }
                song.add("\n");
            }

            song.add("\n");
            song.add("\n");
        }

        return song;
    }

}
