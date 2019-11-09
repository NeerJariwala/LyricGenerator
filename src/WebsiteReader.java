import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.swing.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;


public class WebsiteReader extends JFrame{



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

            //print lyrics
            //System.out.println(lyrics);


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

            //print words
            for (int i = 0; i < words.length; i++) {
                //System.out.println(words[i]);
                //System.out.println(words[i].length());
            }

            nGramMatcher.makeNGramsFromInput(words);
        }

        // Print out what it found for NGrams
        System.out.println(nGramMatcher.getNGramString());
    }
}