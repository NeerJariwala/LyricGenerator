import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test extends JFrame implements ActionListener {

    private JTextArea output;
    private JButton btnGenerate;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        new test(WIDTH, HEIGHT).setVisible(true);
    }

    private test(int width, int height) {
        // Parameters for the JFrame window
        setTitle("Lyric Generator");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the form
        btnGenerate.addActionListener(this);
        add(output);
        add(btnGenerate);

        // Move window to center of screen
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnGenerate) {
            try {
                System.out.println("Click!");
                WebsiteReader.main(new String[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

}
