import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        if (e.getSource() == btnGenerate) {
            try {
                System.out.println("Click!");
                //reader.getWebsites();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

}
