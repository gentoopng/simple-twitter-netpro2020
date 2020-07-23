import com.sun.tools.javac.comp.Flow;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class SimpleTwitterGUI extends JFrame {
    JPanel pane, paneCenter;
    JPanel topPanel, bottomPanel;
    JPanel timelinePanel, timelineControlPanel, tweetPanel, tweetControlPanel;
    JTextArea timelineArea, tweetArea;
    JTextField addressField, countTL;
    JButton connectButton, tweetButton, viewTLButton;
    JLabel addressLabel, countLabel, tweetLabel, statusLabel;
    JScrollPane scrollPane;

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
    }

    //タイムラインを取得して表示するアクション
    class ViewTLAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            statusLabel.setText("Checking the timeline...");
            try {
                int count = Integer.parseInt(countTL.getText());    //表示したいツイート数を取得
                textTweet = new TextTweet(Integer.toString(count), TextTweet.VIEW_MODE);    //送るオブジェクトに取得したいツイート数と動作モードをセットする

                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(textTweet);
                oos.flush();    //サーバに送信

                ois = new ObjectInputStream(socket.getInputStream());   //送り返されるオブジェクトを受信
                textTweet = (TextTweet) ois.readObject();
                timelineString = textTweet.getMessage();    //取得したタイムラインを表示

                timelineArea.setText(timelineString);
                statusLabel.setText("Complete");
            } catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
                statusLabel.setText("Something went wrong");
            }
        }
    }

    //ツイートを投稿するアクション
    class TweetAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            tweetString = tweetArea.getText();  //TextAreaからツイート内容を取得
            statusLabel.setText("Sending...");

            if (!tweetString.equals("")) {
                textTweet = new TextTweet(tweetString, TextTweet.TWEET_MODE);   //送るオブジェクトに投稿したいツイート内容と動作モードをセットする
                try {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(textTweet);
                    oos.flush();    //サーバに送信

                    ois = new ObjectInputStream(socket.getInputStream());   //送り返されるオブジェクトを受信
                    textTweet = (TextTweet) ois.readObject();
                    tweetArea.setText("");  //TextAreaをリセット（空欄に）
                    System.out.println(textTweet.getMessage());
                    statusLabel.setText(textTweet.getMessage());    //送り返されてきたメッセージを表示
                } catch (IOException | ClassNotFoundException exception) {
                    exception.printStackTrace();
                    statusLabel.setText("Something went wrong");
                }
            } else {
                tweetArea.setText("Please enter something here!");
                statusLabel.setText(":/");
            }
        }
    }

    //サーバに接続するアクション
    class ConnectAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                reader = new BufferedReader(new InputStreamReader(System.in));
                String serverName = addressField.getText();
                socket = new Socket(serverName, 5001);
                System.out.println("Connection established: " + serverName);
                statusLabel.setText("Connection established");
                timelineArea.setText("To reveal your timeline here, \nplease click \"Update Timeline\" below.\n\nYou can specify the number of tweets to view.");
            } catch (IOException exception) {
                exception.printStackTrace();
                System.out.println("Connection failed");
                statusLabel.setText("Connection failed");
                timelineArea.setText("Failed! \nPlease make sure the server is running and the address is correct, then retry.");
            }
        }
    }

    private void initializeGUI() {
        pane = (JPanel)getContentPane();
        pane.setLayout(new BorderLayout());
        paneCenter = new JPanel();
        paneCenter.setLayout(new BoxLayout(paneCenter, BoxLayout.Y_AXIS));

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        addressLabel = new JLabel("Server Address: ");
        topPanel.add(addressLabel);
        addressField = new JTextField("localhost");
        addressField.setPreferredSize(new Dimension(150, 25));
        topPanel.add(addressField);
        Action connectAction = new ConnectAction();
        connectAction.putValue(Action.NAME, "Connect");
        connectButton = new JButton(connectAction);
        topPanel.add(connectButton);
        pane.add(topPanel, BorderLayout.PAGE_START);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
        statusLabel = new JLabel("First of all, please connect to the server.");
        bottomPanel.add(statusLabel);
        pane.add(bottomPanel, BorderLayout.PAGE_END);

        timelinePanel = new JPanel();
        timelineControlPanel = new JPanel();

        timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));

        timelineArea = new JTextArea(23, 40);
        timelineArea.setLineWrap(true);
        //timelineArea.setBorder(new LineBorder(Color.gray, 1, true));
        scrollPane = new JScrollPane(timelineArea);
        timelinePanel.add(scrollPane);
        //timelinePanel.add(timelineArea);

        timelineControlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 2));
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
        paneCenter.add(timelinePanel, BorderLayout.CENTER);

        tweetPanel = new JPanel();
        tweetControlPanel = new JPanel();

        tweetPanel.setLayout(new BoxLayout(tweetPanel, BoxLayout.Y_AXIS));
        //tweetLabel = new JLabel("What's happening?");
        //tweetPanel.add(tweetLabel);

        tweetControlPanel.setLayout(new FlowLayout((FlowLayout.RIGHT), 5, 2));
        Action tweetAction = new TweetAction();
        tweetAction.putValue(Action.NAME, "Tweet");
        tweetButton = new JButton(tweetAction);
        tweetControlPanel.add(tweetButton);
        tweetPanel.add(tweetControlPanel);

        tweetArea = new JTextArea(5, 40);
        tweetArea.setLineWrap(true);
        tweetArea.setBorder(new LineBorder(Color.gray, 1, true));
        tweetPanel.add(tweetArea);
        tweetPanel.setBorder(new TitledBorder("Tweet"));
        paneCenter.add(tweetPanel);

        pane.add(paneCenter, BorderLayout.CENTER);
    }
}
