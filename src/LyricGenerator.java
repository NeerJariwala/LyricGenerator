import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LyricGenerator extends JFrame implements ActionListener {

    private JTextArea output;
    private JButton btnGenerate;
    private JPanel panel;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        new LyricGenerator(WIDTH, HEIGHT).setVisible(true);
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

        WebsiteReader reader = new WebsiteReader();
        NGramMatcher nGramMatcher = new NGramMatcher(3);
        SongBuilder songBuilder = new SongBuilder();
        ArrayList<String> generatedSong;

        if (e.getSource() == btnGenerate) {
            try {
                reader.getWebsites(nGramMatcher);
                generatedSong = songBuilder.createSong(nGramMatcher);

                for (String lyric: generatedSong) {
                    output.append(lyric);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

}
