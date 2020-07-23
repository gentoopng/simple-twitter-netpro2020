import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleTwitterMain {
    public static void main(String[] args) {
        TextTweet textTweet = null;
        Integer mode;

        ServerSocket server = null;
        Socket socket = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {   //サーバーを準備
            server = new ServerSocket(5001);
            socket = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {   //最大100回まで処理
            int counter = 100;

            //String tweetString = "";
            String timelineString = "";

            while (counter-- > 0) {
                try {
                    //クライアントからオブジェクトを受け取る
                    assert socket != null;
                    ois = new ObjectInputStream(socket.getInputStream());
                    textTweet = (TextTweet) ois.readObject();
                    mode = textTweet.getMode();

                    //オブジェクトに入って送られてきた動作モードに応じて処理を分岐
                    switch (mode) {
                        case 0: //check TL mode
                            timelineString = GetTimeline.get(Integer.parseInt(textTweet.getMessage())); //タイムラインを取得して
                            textTweet.setMessage(timelineString);   //オブジェクトに書き込む
                            break;
                        case 1: //tweet mode
                            textTweet.run();    //オブジェクトに入っている内容でツイートを実行
                            textTweet.setMessage("Your Tweet was sent");    //処理が終わったメッセージを書き込む
                            break;
                        default:
                            System.out.println("error, try again");
                            break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    if (textTweet != null)
                        textTweet.setMessage("Something went wrong. Please try again.");
                }

                //もしオブジェクトがnullだった場合エラーメッセージを書き込む
                if (textTweet == null) {
                    textTweet = new TextTweet("Something went wrong. Please try again.", 0);
                }

                //処理が終わったオブジェクトをクライアントに送り返す
                oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(textTweet);
                oos.flush();

            }

            ois.close();
            oos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}