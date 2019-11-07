import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebsiteReader {

    public static void main(String[] args) throws Exception{
        Document doc = Jsoup.connect("http://www.absolutelyrics.com/lyrics/view/conan_gray/idle_town").get();
        doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
        doc.select("br").remove();
        String lyrics = doc.select("#view_lyrics").html();
        System.out.println(lyrics);
    }

}
