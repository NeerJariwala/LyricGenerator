import java.util.*;

public class SongBuilder{

    public ArrayList<String> createSong(NGramMatcher nGramMatcher) {
        LinkedList<String> songWords = new LinkedList<>();
        ArrayList<String> song = new ArrayList<>();
        int x = 1;

        for (int i = 0; i < 50; i++) {
            Random r = new Random();
            int randomN = r.nextInt(9 - 7 + 1) + 7;
            String nextWord = nGramMatcher.getNextWord(songWords);

            songWords.add(nextWord);
            song.add(nextWord);
            song.add(" ");
            if(i >= randomN * x) {
                song.add("\n");
                x++;
                System.out.println(randomN);
            }


            if (songWords.size() > nGramMatcher.getN() - 1) {
                songWords.poll();
            }
        }

        return song;
    }

}
