package lime.wazza.org.kca_lime.auxillary;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by kelli on 9/23/14.
 */

public class Authenticator {
    private String userName;
    private String password;
    private static final String ERR_TAG = "LIME_AUTH";

    BufferedReader bufferedReader = null;
    static boolean verified = false;


    public static boolean authenticateUser(String userName, String password) {

        HttpClient connection = new DefaultHttpClient();
        try {

            URL url = new URL("http", "localhost", 80, "/moodle/login/token.php");
            URLConnection connection1 = url.openConnection();
            connection1.setDoOutput(true);
            connection1.connect();

            StringBuilder sb = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection1.getInputStream()));


//            HttpHost host=new HttpHost();
            URI uri = new URI("http://localhost/moodle/login/token.php?user=" + userName +
                    "&password=" + password + "&service=lime");
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(uri);
            HttpResponse response = connection.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                verified = true;
            }
            //get the content of the response

        } catch (URISyntaxException err) {
            Log.d(ERR_TAG, "Syntax error on connection URI");
        } catch (ClientProtocolException cpe) {
            Log.d(ERR_TAG, "Client Protocol error");

        } catch (IOException cpe) {
            Log.d(ERR_TAG, "IO error");
            Log.d(ERR_TAG, cpe.getMessage());
        }
//        finally {
//            try{
//
//            }
//        }

        return verified;
    }

    //query a simple url and return the results...
    public String testSimpleGet() {
        StringBuilder stringBuilder = new StringBuilder();
        URLConnection connection;
        URL url;
        HttpResponse response;
        try {

            url = new URL("http", "localhost/devlib.ke", 80, "/purchase.html");
            connection = url.openConnection();
            connection.setDoOutput(true);
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
        return stringBuilder.toString();
    }
}
