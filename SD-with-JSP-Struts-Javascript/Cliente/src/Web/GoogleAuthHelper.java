package Web;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
@SuppressWarnings("serial")
@WebServlet("/Google")
public class GoogleAuthHelper extends HttpServlet{
	
	private static final String NETWORK_NAME = "Google";
	private static final String AUTHORIZE_URL = "https://accounts.google.com/o/oauth2/auth?oauth_token=";
	private static final String PROTECTED_RESOURCE_URL = "https://docs.google.com/feeds/default/private/full/";
	private static final String SCOPE = "https://www.googleapis.com/auth/calendar"; 
	private static final Token EMPTY_TOKEN = null;
	private static final String CALLBACK_URL = "https://miguel.com:8080/Hey/callback";
	
	public GoogleAuthHelper() {
        super();
        
    }

	public void doGet(HttpServletRequest requestHttp, HttpServletResponse responseHttp) throws ServletException, IOException {
		try {
			OAuthService service = new ServiceBuilder()
            .provider(Google2APi.class)
            .apiKey("939126361806-k2si9sn45aen106mb5r8firv190j7ks8.apps.googleusercontent.com")
            .apiSecret("rIVdu632RoM3lqoO7qVoMXJY")
            .callback(CALLBACK_URL)
            .scope(SCOPE)
            .build();



			Scanner in = new Scanner(System.in);
			
			
			System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
			System.out.println();
			
			System.out.println("Fetching the Authorization URL...");
			String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
			System.out.println("Got the Authorization URL!");
			System.out.println("Now go and authorize Scribe here:");
			System.out.println(authorizationUrl+"&approval_prompt=force");
			System.out.println("And paste the authorization code here");
			System.out.print(">>");
			Verifier verifier = new Verifier(in.nextLine());
			System.out.println();
			
			// Trade the Request Token and Verfier for the Access Token
			System.out.println("Trading the Request Token for an Access Token...");
			Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
			System.out.println("Got the Access Token!");
			System.out.println("(if your curious it looks like this: " + accessToken + " )");
			System.out.println();
			
			// Now let's go and ask for a protected resource!
			System.out.println("Now we're going to access a protected resource...");
			OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			service.signRequest(accessToken, request);
			Response response = request.send();
			System.out.println("Got it! Lets see what we found...");
			System.out.println();
			System.out.println(response.getCode());
			System.out.println(response.getBody());
			
			
			System.out.println();
			System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
			
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		  

	}
	
}