package br.org.furb.sic.controller;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterController {
	
	private final String TWITTER_CONSUMER_KEY = "9wCm1u34L3pLVYnvOiFIKPHSi";
	private final String TWITTER_SECRET_KEY = "SoqKvwPy2pi19twgc82qBENGOeuJhCkLQOXpebTbBYXkCdVNLk";
	private final String TWITTER_ACCESS_TOKEN = "2460706994-2ztrCLNxNYe574qQJBJVQdVjlFm2bOI3yxA8cdO";
	private final String TWITTER_ACCESS_TOKEN_SECRET = "qXt2Mm78vRTOahtWqyfqgVAWdpjH5augH6LztptZh5zQf";
	private final int TWEETS_TIME_LINE = 4;
	private Twitter twitter;
	
	public TwitterController (){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		    .setOAuthConsumerKey(TWITTER_CONSUMER_KEY)
		    .setOAuthConsumerSecret(TWITTER_SECRET_KEY)
		    .setOAuthAccessToken(TWITTER_ACCESS_TOKEN)
		    .setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}
	
	public void buscaPalavraChave(String palavra){		
		 
		try {
		    Query query = new Query(palavra);
		    QueryResult result;
		    boolean v = true;
		    do {
		    	result = twitter.search(query);
		        List<Status> tweets = result.getTweets();
		        for (Status tweet : tweets) {	
		        	// Inserir na lista
		            System.out.println("@" + tweet.getUser().getScreenName() + " |Description " + tweet.getUser().getDescription() + " - " + tweet.getText());
		            if(v)
		            	buscaUsuarioTimeLine(tweet.getUser().getId());
		            v = false;
		            
		        }
		    } while ((query = result.nextQuery()) != null);
		    System.exit(0);
		} catch (TwitterException te) {
		    te.printStackTrace();
		    System.out.println("Failed to search tweets: " + te.getMessage());
		    System.exit(-1);
		}		
		
	}
	
	public void buscaUsuarioTimeLine(long id){
		
		try{
	    	List<Status> statusUser = twitter.getUserTimeline(id);
	    	if (!statusUser.isEmpty()){
	    		int count = 0;
	    		Status status = statusUser.get(count);	    	
	    		while((status != null) && count <= TWEETS_TIME_LINE){
	    			System.out.println(status.getUser().getName() + ":" +
		                    status.getText());
	    			count++;
	    			status = statusUser.get(count);
	    		}
	    	}	    			
		} catch (TwitterException te) {
		    te.printStackTrace();
		    System.out.println("Failed to search user timeline: " + te.getMessage());		    
		}
        
	}
	
	public boolean isValidTweet(String texto, String palavra){
		return false;
	}

}
