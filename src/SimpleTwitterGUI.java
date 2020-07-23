import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleTwitterGUI extends JFrame {
    JPanel pane;
    JPanel timelinePanel, timelineControlPanel, tweetPanel;
    JTextArea timelineArea, tweetArea;
    JTextField countTL;
    JButton tweetButton, viewTLButton;
    JLabel countLabel;

    public static void main(String[] args) {
        JFrame w = new SimpleTwitterGUI("SimpleTwitterClient");
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(400, 600);
        w.setVisible(true);
        //w.pack();
    }

    public SimpleTwitterGUI(String title) { //コンストラクタ
        super(title);
        pane = (JPanel)getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        timelinePanel = new JPanel();
        timelineControlPanel = new JPanel();

        timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));

        timelineArea = new JTextArea();
        timelineArea.setBorder(new LineBorder(Color.gray, 1, true));
        timelinePanel.add(timelineArea);

        timelineControlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 2));
        countLabel = new JLabel("Tweets to view:");
        timelineControlPanel.add(countLabel);
        countTL = new JTextField("5");
        countTL.setPreferredSize(new Dimension(50, 25));
        timelineControlPanel.add(countTL);
        viewTLButton = new JButton();
        timelineControlPanel.add(viewTLButton);

        timelinePanel.add(timelineControlPanel);
        timelinePanel.setBorder(new TitledBorder("Timeline"));
        pane.add(timelinePanel);

        //tweetPanel = new JPanel();
    }
}
