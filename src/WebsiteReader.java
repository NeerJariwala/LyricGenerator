import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class WebsiteReader extends JFrame{

    public String main(String[] args) throws Exception{

        Document top50 = Jsoup.connect("http://www.absolutelyrics.com/lyrics/top50").get();

        ArrayList<String> linksList = new ArrayList<>();
        String[] links;

        for(int i = 1; i < 11; i++) {
            //grab the song links from the website
            String link = top50.select("#left > div > ol > li:nth-child(" + i + ") > a").attr("abs:href");
            linksList.add(link);
        }

        links = linksList.toArray(new String[linksList.size()]);

        // Create the NGramMatcher instance
        NGramMatcher nGramMatcher = new NGramMatcher(3);

        for (String s : links) {
            //connect to website
            Document doc = Jsoup.connect(s).get();

            //keep break lines
            doc.outputSettings(new Document.OutputSettings().prettyPrint(false));

            //removes break lines from html
            doc.select("br").remove();

            //grab the lyrics from the website
            String lyrics = doc.select("#view_lyrics").html();

            //removes all text between [] and the [] themselves
            lyrics = lyrics.replaceAll("\\[.*?]", "");
            lyrics = lyrics.replaceAll("\\(", "");
            lyrics = lyrics.replaceAll("\\)", "");
            lyrics = lyrics.replaceAll(",", "");
            lyrics = lyrics.replaceAll("&amp;#\\d+;", " ");

            String[] words;
            String delimiter = "\\s";

            //split string using delimiter
            words = lyrics.split(delimiter);

            List<String> list = new ArrayList<String>();

            for(String ss : words) {
                if(ss.length() >= 1) {
                    list.add(ss.toLowerCase());
                }
            }

            words = list.toArray(new String[list.size()]);

            nGramMatcher.makeNGramsFromInput(words);
        }

        // Print out what it found for NGrams
        return nGramMatcher.getNGramString();
    }
}