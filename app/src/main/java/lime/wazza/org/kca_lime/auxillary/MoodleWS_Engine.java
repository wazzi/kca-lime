package lime.wazza.org.kca_lime.auxillary;

/**
 * Created by kelli on 10/15/14.
 */

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class will handle all web service call form this android application.
 */
public class MoodleWS_Engine {

    private final static String EXCEPTION = "MWS_EXCEPTION";
    private static final String BASE_URL = "http://10.0.2.2/moodle/webservice/rest/server.php";
    private static String delimiter = "?";
    private final String LOGIN_URL = "http://localhost/moodle/login/token.php";

    public static boolean startEngine(String baseUrl, Context context) {
        boolean isStarted = false;
        if (isConnected(context)) {

        } else {

        }
        return isStarted;
    }

    //check if internet connection is available
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else
            return false;

    }

    //    http://127.0.0.1/moodle/webservice/rest/simpleserver.php?wsusername=sylvia_ws&wspassword=sly*SLY12&ws
    // function=moodle_user_get_users_by_id&userids[0]=7

    /**
     * Create url for any web service method in the application
     *
     * @param token
     * @param user
     * @param password
     * @param functionName
     * @param map
     * @return the created url
     */
    public static String createUrl(String token, String user, String password, String functionName, Map<String, String> map) {


        String url;
        //token based request
        if (user == null && password == null) {
            if (map.size() < 1) {
                //no parameters in the request
                url = BASE_URL + delimiter + "wstoken=" + token + "wsfunction=" + functionName;
            } else {

                Set keys = map.keySet();
                Collection<String> values = map.values();
                //get the parameter name and values and attach to request...
                url = BASE_URL + delimiter + "wstoken=" + token + "wsfunction=" + functionName;
                //add the parameters to the url

                Iterator<String> i = keys.iterator();

                while (i.hasNext()) {
                    url += "&" + i.next() + "[" + 0 + "]" + values.iterator().next();
                }
                Log.d("MOODLE_WSENG", url);
            }

        } else //url has user name and password...handle params later...
        {
            url = BASE_URL + delimiter + "wsusername=sylvia" + functionName;

        }
        Log.d("MOOBLE_WSENG", url);
        return url;
    }

    /**
     * Create a reusable inputstream for all web service connections
     *
     * @param functionUrl
     * @return inputsream
     */
    public static InputStream getWSMethodConnection(String functionUrl) {

        InputStream inputStream = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(functionUrl);
        try {
            HttpResponse httpResponse = client.execute(get);
            inputStream = httpResponse.getEntity().getContent();
        } catch (ClientProtocolException cpre) {
            Log.d(MoodleWS_Engine.EXCEPTION, cpre.getMessage());
            cpre.printStackTrace();
        } catch (IOException io) {
            Log.d(MoodleWS_Engine.EXCEPTION, io.getMessage());
            io.printStackTrace();
        }
//        Log.d("MOODLE_WS","Inputstream: "+inputStream)
        return inputStream;
    }

    public static String getStreamContent(InputStream in) {

        String results = "";
        String liner;
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new InputStreamReader(in));

            while ((liner = reader.readLine()) != null) {
                results += liner;

            }
        } catch (IOException ioe) {
            Log.d(MoodleWS_Engine.EXCEPTION, ioe.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.d(MoodleWS_Engine.EXCEPTION, "Error closing stream");
                }
            }
        }
//        Log.d("MOODLE_WSENG",results);
        return results;
    }
}
