import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class WebsiteReader extends JFrame{

    public void getWebsites(NGramMatcher nGramMatcher) throws Exception{

        for (char alphabet = 'a'; alphabet <='z'; alphabet++) {
            Document top50 = Jsoup.connect("http://www.absolutelyrics.com/lyrics/songlist/" + alphabet).get();

            ArrayList<String> linksList = new ArrayList<>();
            String[] links;

            //get all 12,116 songs
            int numOfLinks = 501;
            switch (alphabet) {
                case 'q':
                    numOfLinks = 339;
                    break;
                case 'x':
                    numOfLinks = 104;
                    break;
                case 'z':
                    numOfLinks = 176;
                    break;
            }

            for(int i = 1; i < numOfLinks; i++) {
                //grab the song links from the website
                String link = top50.select("#left > div.songlist > ul > li:nth-child(" + i + ") > a").attr("abs:href");
                linksList.add(link);
            }

            links = linksList.toArray(new String[linksList.size()]);



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
                lyrics = lyrics.replaceAll("\"", "");
                lyrics = lyrics.replaceAll("&amp;#\\d+;", " ");

                String[] words;
                String delimiter = "\\s";

                //split string using delimiter
                words = lyrics.split(delimiter);

                List<String> list = new ArrayList<>();

                for(String ss : words) {
                    if(ss.length() >= 1) {
                        list.add(ss.toLowerCase());
                    }
                }

                words = list.toArray(new String[list.size()]);


                nGramMatcher.makeNGramsFromInput(words);
            }
        }
    }
}