import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class LyricGenerator extends JFrame implements ActionListener {

    private JTextPane output;
    private JButton btnGenerate;
    private JPanel panel;
    private JProgressBar progBar;

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 900;

    // Generated song output
    private final StringBuilder songOutput = new StringBuilder();
    private final String SONG_DIVIDER = "********************************\n\n";

    // Build data variables
    private static final int N = 3;
    private static NGramMatcher nGramMatcher;

    public static void main(String[] args) {
        if (args.length == 2 && args[0].equals("--build")) {
            // Create reader and n-gram matcher objects
            WebsiteReader reader = new WebsiteReader();
            nGramMatcher = new NGramMatcher(N);

            // Try to read in data
            try {
                reader.readWebsiteData(nGramMatcher);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

            // Write to the file
            nGramMatcher.writeToFile(args[1]);
        } else if (args.length == 1) {
            LyricGenerator lyricGenerator = new LyricGenerator(WIDTH, HEIGHT);
            lyricGenerator.setVisible(true);
            nGramMatcher = NGramMatcher.createFromFile(args[0]);
            lyricGenerator.progBar.setString("");
            lyricGenerator.progBar.setIndeterminate(false);
            lyricGenerator.progBar.setVisible(false);
        } else {
            System.out.println("\nPlease specify a filepath for NGram data.");
        }
    }

    private LyricGenerator(int width, int height) {
        // Parameters for the JFrame window
        setTitle("Lyric Generator");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the form
        add(panel);
        btnGenerate.addActionListener(this);

        // Move window to center of screen
        setLocationRelativeTo(null);

        // Make text field centered
        StyledDocument doc = output.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGenerate) {
            progBar.setVisible(true);
            progBar.setValue(0);
            progBar.setString("Generating song...");

            new Thread(() -> {
                SongBuilder songBuilder = new SongBuilder();
                ArrayList<String> generatedSong;

                try {
                    generatedSong = songBuilder.createSong(nGramMatcher, progBar);

                    for (String lyric : generatedSong) {
                        songOutput.append(lyric);
                    }
                    songOutput.append(SONG_DIVIDER);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                output.setText(songOutput.toString());
                progBar.setVisible(false);
                progBar.setValue(0);
            }).start();
        }
    }

}
