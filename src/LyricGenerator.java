import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class LyricGenerator extends JFrame implements ActionListener {

    private JTextArea output;
    private JButton btnGenerate;
    private JPanel panel;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

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
        }
        else if (args.length == 1) {
            nGramMatcher = NGramMatcher.createFromFile(args[0]);
            new LyricGenerator(WIDTH, HEIGHT).setVisible(true);
        }
        else {
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SongBuilder songBuilder = new SongBuilder();
        ArrayList<String> generatedSong;

        if (e.getSource() == btnGenerate) {
            try {
                System.out.println(nGramMatcher.getN());
                generatedSong = songBuilder.createSong(nGramMatcher);
                for (String lyric : generatedSong) {
                    output.append(lyric);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

}
