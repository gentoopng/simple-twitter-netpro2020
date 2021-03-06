import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.util.List;

public class GetTimeline {

    public static String get(int count) {
        StringBuilder result = new StringBuilder();
        Twitter twitter = new TwitterFactory().getInstance();

        try {
            Paging page = new Paging();
            page.setCount(count);
            List<Status> statusList = twitter.getHomeTimeline(page);
            for (Status status: statusList) {
                result.append(status.getUser().getName() + " @" + status.getUser().getScreenName() + "\n");
                result.append(status.getText() + "\n---------\n");
            }
        } catch (TwitterException e) {
            e.printStackTrace();
            result.append(e.getErrorMessage());
        }

        return result.toString();
    }
}
