import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test extends JFrame implements ActionListener {

    private JTextArea output;
    private JButton Generate = new JButton("Generate");


    public static void main(String[] args) {


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WebsiteReader result = new WebsiteReader();
        //output.append(result);
    }
}
