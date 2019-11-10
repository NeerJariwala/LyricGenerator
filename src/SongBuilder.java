import javax.swing.*;
import java.util.*;

public class SongBuilder{

    public ArrayList<String> createSong(NGramMatcher nGramMatcher, JProgressBar progBar) {
        LinkedList<String> songWords = new LinkedList<>();
        ArrayList<String> song = new ArrayList<>();
        Random s = new Random();
        int stanzas = s.nextInt(9 - 6 + 1) + 6;

        progBar.setMinimum(0);
        progBar.setMaximum(stanzas * 7);
        progBar.setVisible(true);

        for (int k = 0; k < stanzas; k++) {
            Random l = new Random();
            int lines = l.nextInt(9 - 5 + 1) + 5;

            if (k == 0) {
                // Generate title
                lines = 1;
            }

            for (int j = 0; j < lines; j++) {
                Random r = new Random();
                int randomN;

                if (k == 0) {
                    // Title
                    randomN = r.nextInt(6) + 1;
                } else {
                    // Not title
                    randomN = r.nextInt(9 - 7 + 1) + 7;
                }

                for (int i = 0; i < randomN; i++) {
                    String nextWord = nGramMatcher.getNextWord(songWords);
                    if(i == 0) {
                        nextWord = nextWord.substring(0, 1).toUpperCase() + nextWord.substring(1);
                    }

                    if(nextWord.contains("_")) {
                        nextWord = censorWord(nextWord);
                    }
                    songWords.add(nextWord);
                    if (k == 0) {
                        // Title
                        song.add(nextWord.toUpperCase());
                    } else {
                        // Not title
                        song.add(nextWord);
                    }
                    song.add(" ");


                    if (songWords.size() > nGramMatcher.getN() - 1) {
                        songWords.poll();
                    }
                }
                song.add("\n");

                // Update progress bar after each line
                progBar.setValue(Math.max(k * 7 + j, progBar.getValue()));
            }

            song.add("\n");
            song.add("\n");
        }

        return song;
    }

    private String censorWord(String word) {
        return word.substring(0, 1) + "****";
    }

}
