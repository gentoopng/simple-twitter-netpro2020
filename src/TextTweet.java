import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.Status;
import twitter4j.StatusUpdate;

public class TextTweet implements Runnable {
    private String tweetString;  //ツイート内容

    public TextTweet(String status){
        this.tweetString = status;
    }

    public String getTweetString() {
        return tweetString;
    }

    @Override
    public void run() {

    }
}
