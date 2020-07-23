import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.Status;
import twitter4j.StatusUpdate;

import java.io.Serializable;

public class TextTweet implements Runnable, Serializable {
    private String message;  //受け渡しする内容
    private int mode;   //0 = check TL, 1 = tweet
    private boolean done = false;

    public static int VIEW_MODE = 0;
    public static int TWEET_MODE = 1;

    public TextTweet(String status, int mode){
        this.message = status;
        this.mode = mode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public int getMode() {
        return mode;
    }

    public boolean isDone() {
        return done;
    }
    public void setDone(boolean status) {
        this.done = status;
    }

    //このオブジェクトの message の内容でツイートを実行する
    @Override
    public void run() {
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            StatusUpdate update = new StatusUpdate(message);
            Status status = twitter.updateStatus(update);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
