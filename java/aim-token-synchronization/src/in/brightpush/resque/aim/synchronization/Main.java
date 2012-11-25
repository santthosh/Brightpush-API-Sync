package in.brightpush.resque.aim.synchronization;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class Main {

	static Logger log = Logger.getLogger(Main.class.getName());
	
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	  
	/**
	 * @param args
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 */
	public static void main(String[] args) throws GeneralSecurityException, IOException {
		log.info("Starting TaskQueue <tokens-create / tokens-update> processor");
		
		Properties properties = new Properties();
		properties.load(Main.class.getClassLoader().getResourceAsStream("googleapi.properties"));
		
        // service account credentials
        GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
            .setJsonFactory(JSON_FACTORY)
            .setServiceAccountScopes(properties.getProperty("serviceaccountscope"))
            .setServiceAccountPrivateKeyFromP12File(new File(properties.getProperty("privatekeyfile")))
            .setServiceAccountId(properties.getProperty("serviceaccountid"))
            .build();
        
		log.info("Finishing TaskQueue <tokens-create / tokens-update> processor");
	}
}
