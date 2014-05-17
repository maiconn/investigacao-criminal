import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Group;
import facebook4j.Place;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.conf.ConfigurationBuilder;


public class testFace {
	
	public static void main(String[] args) throws FacebookException{		
		
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthAppId("670772572989250")
		.setOAuthAppSecret("5656c9ece88d88110d79796bb82b174a")
		.setOAuthAccessToken("CAAJiEGjwJ0IBAAklOJ46kCIVcbu3yFrgNMIKwR4rtiHeSnOXXw87g17CoCxFsQaYXZBWKK4Ad5HZCTJbPVIKF3iZBooNZAjNJT6wVfWPlm54y8Ll6Wvij3H6yuZAnnCrDcEeIJkrl693ZAqUvMPFRZBLizZCzNCP7RxEM8bJaw2BaYUZCZBesYpmHRVFekI0qKCyEZD")							  	
		.setOAuthPermissions("email,publish_stream,...");
		FacebookFactory ff = new FacebookFactory(cb.build());
		Facebook facebook = ff.getInstance();	
		
		
//		ResponseList<Group> results = facebook.searchGroups("FURB");
//		for(Group p: results){
//			System.out.println(p.getName());
//		}
		
		ResponseList<User> result1 = facebook.searchUsers("Maicon Machado");
		for(User p: result1){
			System.out.println(p.getName());
			
			
			for(String us: p.getInterestedIn()){
				System.out.println(us.toString());
			}
				
			
		}		
//		
//		ResponseList<Event> result2 = facebook.searchEvents("Karaokê");		
//		for(Event p: result2){
//			System.out.println(p.getName());
//			System.out.println(p.getLocation());
//		}
//		
////		ResponseList<Post> result3 = facebook.searchPosts("Copa do Mundo");
////		for(Post p: result3){
////			System.out.println(p.getName());
////		}
//		
//		ResponseList<Place> result4 = facebook.searchPlaces("Jaraguá do Sul");		
//		for(Place p: result4){
//			System.out.println(p.getName());
//		}
//		
		System.out.println("fim");
	}
	
	
}
