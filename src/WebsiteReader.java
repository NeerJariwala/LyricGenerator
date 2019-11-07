import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;

public class WebsiteReader {

    public static void main(String[] args) throws Exception {

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

        //print lyrics
        System.out.println(lyrics);
    }

}
