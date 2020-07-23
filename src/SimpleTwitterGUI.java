import javafx.scene.control.cell.CheckBoxListCell;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class SimpleTwitterGUI extends JFrame {
    JPanel pane;
    JPanel timelinePanel, timelineControlPanel, tweetPanel, tweetControlPanel;
    JTextArea timelineArea, tweetArea;
    JTextField countTL;
    JButton tweetButton, viewTLButton;
    JLabel countLabel, tweetLabel;

    BufferedReader reader = null;
    Socket socket = null;
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;

    TextTweet textTweet = null;

    String timelineString = "";
    String tweetString = "";

    public static void main(String[] args) {
        JFrame w = new SimpleTwitterGUI("SimpleTwitterClient");
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(400, 600);
        w.setVisible(true);
        //w.pack();
    }

    public SimpleTwitterGUI(String title) { //コンストラクタ
        super(title);
        initializeGUI();

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Server name? >");
            String serverName = reader.readLine();
            socket = new Socket(serverName, 5001);
            System.out.println("Connection established");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ViewTLAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int count = Integer.parseInt(countTL.getText());
                textTweet = new TextTweet(Integer.toString(count), TextTweet.VIEW_MODE);

                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(textTweet);
                oos.flush();

                ois = new ObjectInputStream(socket.getInputStream());
                textTweet = (TextTweet) ois.readObject();
                timelineString = textTweet.getMessage();

                timelineArea.setText(timelineString);
            } catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        }
    }

    class TweetAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private void initializeGUI() {
        pane = (JPanel)getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        timelinePanel = new JPanel();
        timelineControlPanel = new JPanel();

        timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));

        timelineArea = new JTextArea();
        timelineArea.setBorder(new LineBorder(Color.gray, 1, true));
        timelinePanel.add(timelineArea);

        timelineControlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 2));
        countLabel = new JLabel("Tweets to show:");
        timelineControlPanel.add(countLabel);
        countTL = new JTextField("5");
        countTL.setPreferredSize(new Dimension(50, 25));
        timelineControlPanel.add(countTL);
        Action viewTLAction = new ViewTLAction();
        viewTLAction.putValue(Action.NAME, "Update Timeline");
        viewTLButton = new JButton(viewTLAction);
        timelineControlPanel.add(viewTLButton);

        timelinePanel.add(timelineControlPanel);
        timelinePanel.setBorder(new TitledBorder("Timeline"));
        pane.add(timelinePanel);


        tweetPanel = new JPanel();
        tweetControlPanel = new JPanel();

        tweetPanel.setLayout(new BoxLayout(tweetPanel, BoxLayout.Y_AXIS));
        //tweetLabel = new JLabel("What's happening?");
        //tweetPanel.add(tweetLabel);

        tweetControlPanel.setLayout(new FlowLayout((FlowLayout.RIGHT), 5, 2));
        tweetButton = new JButton();
        tweetControlPanel.add(tweetButton);
        tweetPanel.add(tweetControlPanel);

        tweetArea = new JTextArea();
        tweetArea.setBorder(new LineBorder(Color.gray, 1, true));
        tweetPanel.add(tweetArea);
        tweetPanel.setBorder(new TitledBorder("Tweet"));
        pane.add(tweetPanel);
    }
}
