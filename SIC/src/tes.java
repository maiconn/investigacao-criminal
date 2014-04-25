import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class tes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final String TWITTER_CONSUMER_KEY = "9wCm1u34L3pLVYnvOiFIKPHSi";
		final String TWITTER_SECRET_KEY = "SoqKvwPy2pi19twgc82qBENGOeuJhCkLQOXpebTbBYXkCdVNLk";
		final String TWITTER_ACCESS_TOKEN = "2460706994-2ztrCLNxNYe574qQJBJVQdVjlFm2bOI3yxA8cdO";
		final String TWITTER_ACCESS_TOKEN_SECRET = "qXt2Mm78vRTOahtWqyfqgVAWdpjH5augH6LztptZh5zQf";

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		    .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
		    .setOAuthConsumerSecret(TWITTER_SECRET_KEY)
		    .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
		    .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		int contador = 0; 
		try {
		    Query query = new Query("FURB");
		    QueryResult result;
		    do {
		        result = twitter.search(query);
		        List<Status> tweets = result.getTweets();
		        for (Status tweet : tweets) {		        	
		            System.out.println("@" + tweet.getUser().getScreenName() + " |Description " + tweet.getUser().getDescription() + " - " + tweet.getText());
		            if (contador == 0){
		            	List<Status> statusUser = twitter.getUserTimeline(tweet.getUser().getId());
		            	for (Status status : statusUser){
		            		System.out.println(status.getUser().getName() + ":" +
		                            status.getText());
		            	}
		            	contador++;
		            }
		        }
		    } while ((query = result.nextQuery()) != null);
		    System.exit(0);
		} catch (TwitterException te) {
		    te.printStackTrace();
		    System.out.println("Failed to search tweets: " + te.getMessage());
		    System.exit(-1);
		}

	}

}
