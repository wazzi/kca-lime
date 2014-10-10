package lime.wazza.org.kca_lime.auxillary;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by kelli on 10/9/14.
 */
public class LoginManager {


    /**
     * Create login url from user credentials
     */


    public static String createLoginURL(String userName, String password, String site) {
        return "http://10.0.2.2/moodle/login/token.php?username=sylvia_ws&password=sly*SLY12&service=nina";
    }


    public static String authenticate(String url) {
        InputStream inputStream = null;
        String line = "";
        String resultString = "";

        try {
            //create a HttpClient
            HttpClient client = new DefaultHttpClient();

            //make get request to the supplied url
            HttpResponse response = client.execute(new HttpGet(url));

            //receive response as input stream
            inputStream = response.getEntity().getContent();

            //convert input stream into string...
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                while ((line = reader.readLine()) != null) {
                    resultString += line;
                }
            } else {
                resultString = "No Result Found";
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

}
