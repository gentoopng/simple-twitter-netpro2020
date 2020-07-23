import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleTwitterMain {
    public static void main(String[] args) throws IOException {
        TextTweet textTweet = null;
        Integer mode;

        ServerSocket server = null;
        Socket socket = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            server = new ServerSocket(5001);
            socket = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            int counter = 100;

            //String tweetString = "";
            String timelineString = "";

            while (counter-- > 0) {
                try {
                    assert socket != null;
                    ois = new ObjectInputStream(socket.getInputStream());
                    textTweet = (TextTweet) ois.readObject();
                    mode = textTweet.getMode();

                    switch (mode) {
                        case 0: //check TL mode
                            timelineString = GetTimeline.get(Integer.parseInt(textTweet.getMessage()));
                            textTweet.setMessage(timelineString);
                            break;
                        case 1: //tweet mode
                            textTweet.run();
                            textTweet.setMessage("Successfully Tweeted!");
                            break;
                        default:
                            System.out.println("error, try again");
                            break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    if (textTweet != null)
                        textTweet.setMessage("Somethig went wrong. Please try again.");
                }

                if (textTweet == null) {
                    textTweet = new TextTweet("Something went wrong. Please try again.", 0);
                }

                if (oos == null) {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                }

                if (oos != null) {
                    oos.writeObject(textTweet);
                    oos.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}