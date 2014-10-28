package lime.wazza.org.kca_lime.auxillary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.ArrayList;

/**
 * Created by kelli on 10/13/14.
 */
public class MenuControl {

    private static final String LOG_TAG = "MENU_CTL";
    private static String results;
    private static ArrayList<Unit> units;

    private MoodleWS_Engine engine;

    public static String getSelectionById(Context context, int id) {

        //get the stored token
        SharedPreferences p = context.getSharedPreferences("MOODLE_TOKEN_OBJ", Context.MODE_PRIVATE);
        String token = p.getString("user_token", "No token found");
        Log.d("MENU_CRTL", "Token Found" + token);
        switch (id) {
            case 1:
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (MoodleWS_Engine.isConnected(context)) {
//                    String url1 = MoodleWS_Engine.createUrl(token, "", "", "core_course_get_courses", new HashMap<String, String>());
                    String url1 = "http://10.0.2.2/moodle/webservice/rest/server.php?wstoken=113a3f906837164cd3ea5d94454751e3&wsfunction=core_course_get_courses";
                    new MenuAsynTask().execute(url1);
                    break;
                }

            default:
                results = "This is the default case";
                break;
        }
        return results;
    }


    public static class MenuAsynTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String content = MoodleWS_Engine.getStreamContent(MoodleWS_Engine.getWSMethodConnection(strings[0]));

            Log.d(LOG_TAG, content.substring(0, 500));
            return content;
        }

        @Override
        public void onPostExecute(String content) {
            //process return XML data for presentation
            ContentParser cp = new ContentParser();
            units = cp.parseDocument(content);

        }
    }


}

