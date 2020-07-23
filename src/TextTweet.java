import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.Status;
import twitter4j.StatusUpdate;

public class TextTweet implements Runnable {
    private String message;  //受け渡しする内容

    public TextTweet(String status){
        this.message = status;
    }

    public String getTweetString() {
        return message;
    }

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
