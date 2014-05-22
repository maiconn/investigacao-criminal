import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Place;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.org.json.JSONException;


public class testFace {
	
	public static void main(String[] args) throws FacebookException, JSONException{		
		
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthAppId("670772572989250")
		.setOAuthAppSecret("5656c9ece88d88110d79796bb82b174a")
		.setOAuthAccessToken("CAAJiEGjwJ0IBADLqUJpf2oYCghyTZCl61vpHbXgvmnk17f04U9pwRvIfvYllVR0fiPx1uKWO6UZAoVstYp3lCHzr6ZAUFnudZCjEoDgF0wg8ZCZCawWFN90WjmD7ZBKoSA71IOfdZBDhf3dtGMPbishnB8827eZAzWnRN2HM7keacphvnoCY79cZBBu6Bo8sG6Hi0ZD")							  	
		.setOAuthPermissions("email,publish_stream,...");
		FacebookFactory ff = new FacebookFactory(cb.build());
		Facebook facebook = ff.getInstance();	
		
		
//		ResponseList<Group> results = facebook.searchGroups("FURB");
//		for(Group p: results){
//			System.out.println(p.getName());
//		}
		
		ResponseList<User> result1 = facebook.searchUsers("Maicon Machado Gerardi");
		for(User p: result1){
			System.out.println("============================");
			System.out.println("ID: "+ p.getId());
			System.out.println("Name: "+ p.getName());
			System.out.println("Bio: "+ p.getBio());
			System.out.println("UserName: "+ p.getUsername());
			System.out.println("TOString: "+ p.toString());
			
			
			for(String us: p.getInterestedIn()){
				System.out.println(us.toString());
			}
				
			
		}		
//		
		
		ResponseList<Event> result2 = facebook.searchEvents("WordCamp Belo Horizonte");		
		for(Event p: result2){
			System.out.println(p.toString());
			System.out.println(p.getLocation());
		}		
		
//		
////		ResponseList<Post> result3 = facebook.searchPosts("Copa do Mundo");
////		for(Post p: result3){
////			System.out.println(p.getName());
////		}
//		
		ResponseList<Place> result4 = facebook.searchPlaces("Jaraguá do Sul");		
		for(Place p: result4){
			System.out.println(p.getName());
		}
//		
		System.out.println("fim");
	}
	
	
}
