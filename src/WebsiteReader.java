import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class WebsiteReader {

    public static void main(String[] args) throws Exception{


        Document top50 = Jsoup.connect("http://www.absolutelyrics.com/lyrics/top50").get();

        ArrayList<String> linksList = new ArrayList<>();
        String[] links;

        for(int i = 1; i < 51; i++) {
            //grab the song links from the website
            String link = top50.select("#left > div > ol > li:nth-child(" + i + ") > a").attr("abs:href");
            linksList.add(link);
        }

        links = linksList.toArray(new String[linksList.size()]);
        //print lyrics
        System.out.println(links);


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

            //print lyrics
            //System.out.println(lyrics);


            String[] words;
            String delimiter = "\\s";

            //split string using delimiter
            words = lyrics.split(delimiter);

            List<String> list = new ArrayList<String>();

            for(String ss : words) {
                if(ss.length() >= 1) {
                    list.add(ss);
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
}