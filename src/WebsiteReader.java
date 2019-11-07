import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class WebsiteReader {

    public static void main(String[] args) throws Exception{

        //connect to website
        Document doc = Jsoup.connect("http://www.absolutelyrics.com/lyrics/view/conan_gray/idle_town").get();

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

        //print lyrics
        //System.out.println(lyrics);




        String[] words;
        String delimiter = "\\s";

        //split string using delimiter
        words = lyrics.split(delimiter);

        List<String> list = new ArrayList<String>();

        for(String s : words) {
            if(s.length() >= 1) {
                list.add(s);
            }
        }

        words = list.toArray(new String[list.size()]);

        //print words
        for (int i = 0; i < words.length; i++) {
            System.out.println(words[i]);
            //System.out.println(words[i].length());
            }
    }

}
