import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleTwitterMain {
    public static void main(String[] args) throws IOException {
        TextTweet textTweet;
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
            if (ois == null) {
                ois = new ObjectInputStream(socket.getInputStream());
            }

            int counter = 100;

            //String tweetString = "";
            //String timelineString = "";

            while (counter-- > 0) {
                try {
                    textTweet = (TextTweet) ois.readObject();
                    mode = textTweet.getMode();

                    switch (mode) {
                        case 0: //check TL mode
                            break;
                        case 1: //tweet mode
                            textTweet.run();
                            break;
                        default:
                            System.out.println("error, try again");
                            break;
                    }

                    if (oos == null) {
                        oos = new ObjectOutputStream(socket.getOutputStream());
                    }

                    if (oos != null) {
                        oos.writeObject(textTweet);
                        oos.flush();
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}