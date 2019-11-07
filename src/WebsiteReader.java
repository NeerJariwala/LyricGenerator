import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebsiteReader {

    public static void main(String[] args) throws Exception{
        Document doc = Jsoup.connect("http://www.absolutelyrics.com/lyrics/view/conan_gray/idle_town").get();
        doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
        doc.select("br").append("\\n");
        doc.select("p").prepend("\\n\\n");
        String s = doc.html().replaceAll("\\\\n", "\n");
        Elements lyrics = doc.select("#view_lyrics");
        System.out.println(lyrics);
    }

}
