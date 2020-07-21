import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.Status;
import twitter4j.StatusUpdate;

public class TextTweet {
    private String tweetString;  //ツイート内容

    public TextTweet(String status){
        this.tweetString = status;
    }

    public String getTweetString() {
        return tweetString;
    }
}
