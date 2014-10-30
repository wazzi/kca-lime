//package lime.wazza.org.kca_lime.auxillary;
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.hardware.display.DisplayManager;
//import android.os.AsyncTask;
//import android.util.Log;
//import android.view.Display;
//import android.view.LayoutInflater;
//
//import java.util.ArrayList;
//
//import lime.wazza.org.kca_lime.UnitsViewer;
//
///**
// * Created by kelli on 10/13/14.
// */
//public class MenuControl {
//
//    private  static Context context;
//
//    protected static void setContext(Context c){
//        MenuControl.context=c;
//    }
//
//
//
//
//    /**
//     * Class will carry out background tasks:
//     *      1. Connect to Moodle web service
//     *      2. Execute the get_courses function
//     *      3. Parse the XML document returned,create a list of courses available and pass list for
//     *      display on the UI.
//     *
//     */
//    public static class MenuAsynTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            String content = MoodleWS_Engine.getStreamContent(MoodleWS_Engine.getWSMethodConnection(strings[0]));
//            Log.i(LOG_TAG, content.substring(0, 500));
//            return content;
//        }
//
//        @Override
//        public void onPostExecute(String s) {
//
//            if(s.equals(null)){
//                Log.e(LOG_TAG,"The returned string is null");
//            }else {
//                Log.i(LOG_TAG,"The returned string is OK");
//
//            }
//
//        }
//    }
//
//
//}
//
