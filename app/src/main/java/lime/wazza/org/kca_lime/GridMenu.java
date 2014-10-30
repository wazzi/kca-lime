package lime.wazza.org.kca_lime;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lime.wazza.org.kca_lime.auxillary.ContentParser;
import lime.wazza.org.kca_lime.auxillary.MoodleWS_Engine;
import lime.wazza.org.kca_lime.auxillary.Unit;


public class GridMenu extends ActionBarActivity {

    GridView gv;
    private static final String EXP_TAG = "MENU_CTL_EXCEPTION";
    private static final String ERR_TAG = "MENU_CTL_ERROR";
    private static final String INFO_TAG = "MENU_CTL_INFO";
    //    private static String results = "<foo><single><key name=\"fullname\"><value>DTEEH</value></key><key name=\"idnumber\"><value>12313</value></key></single></foo>";
    private String results = "";
    private ArrayList<Unit> units;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_menu);
        gv = (GridView) findViewById(R.id.gridView);
        AppAdapter adapter = new AppAdapter(this);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(adapter);
    }

    /**
     * Create a custom adapter class for the grid
     */
    class AppAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

        Context context;
        //create an arrayList of SingleGridItem objects
        ArrayList<SingleGridItem> gridItemsList;

        public AppAdapter(Context context) {

            this.context = context;
            gridItemsList = new ArrayList<SingleGridItem>();
            Resources resources = context.getResources();
            String[] itemsArr = resources.getStringArray(R.array.items);//get the menu item names
            int[] itemImgs = {R.drawable.assignments1, R.drawable.messages1, R.drawable.notify1,
                    R.drawable.profile1, R.drawable.units1};//get images to be used with names

            //use loop to populate the listArray
            for (int i = 0; i < 5; i++) {
                SingleGridItem singleGridItem = new SingleGridItem(itemsArr[i], itemImgs[i]);
                gridItemsList.add(singleGridItem);
            }
        }

        @Override
        public int getCount() {
            return gridItemsList.size();
        }

        @Override
        public Object getItem(int i) {
            return gridItemsList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String output = getUserOption(i);
            Intent intent = new Intent(getApplicationContext(), UnitsViewer.class);
            startActivity(intent);

        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View row = view;
            ViewHolder holder;
            //if creating view for the first time...
            if (row == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.single_grid_element, viewGroup, false);
                holder = new ViewHolder(row);
                row.setTag(holder);//store the view for use later...

            } else {
                holder = (ViewHolder) row.getTag();
            }
            SingleGridItem current = gridItemsList.get(i);
            holder.imgView.setImageResource(current.imgID);
            holder.textView.setText(current.itemName);
            return row;
        }

        //class to hold all images and avoid recurrent fetching
        class ViewHolder {
            ImageView imgView;
            TextView textView;

            public ViewHolder(View v) {

                imgView = (ImageView) v.findViewById(R.id.imageView);
                textView = (TextView) v.findViewById(R.id.textView);

            }
        }
    }

    class SingleGridItem {
        private String itemName;
        private int imgID;

        public SingleGridItem(String itemName, int imgID) {
            this.itemName = itemName;
            this.imgID = imgID;
        }
    }

    public String getUserOption(int id) {

        //get the stored token
        SharedPreferences p = this.getApplicationContext().getSharedPreferences("MOODLE_TOKEN_OBJ", Context.MODE_PRIVATE);
        String token = p.getString("user_token", "No token found");
        Log.i("MENU_CRTL", "Token in preferences file: " + token);
        switch (id) {

            case 2:
                if (MoodleWS_Engine.isConnected(this.getApplicationContext())) {
//                    String url1 = MoodleWS_Engine.createUrl(token, "", "", "core_course_get_courses", new HashMap<String, String>());
                    String url1 = "http://10.0.2.2/moodle/webservice/rest/server.php?wstoken=113a3f906837164cd3ea5d94454751e3&wsfunction=core_course_get_courses";

                    //start new background task to fetch xml document from Moodle webservice and process courses available
                    MenuAsynTask bgTask = new MenuAsynTask();
                    bgTask.createDialog(GridMenu.this);
                    bgTask.execute(url1);
                }
                break;

            default:
                results = "This is the default case";
                break;
        }
        return results;
    }

    /**
     * Class will carry out background tasks:
     * 1. Connect to Moodle web service
     * 2. Execute the get_courses function
     * 3. Parse the XML document returned,create a list of courses available and pass list for
     * display on the UI.
     */
    public class MenuAsynTask extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;

        public void createDialog(Activity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Fetching content...");
            dialog.show();
            Log.i(INFO_TAG, "Dialog showing...");
        }

        @Override
        protected String doInBackground(String... strings) {

            String content = MoodleWS_Engine.getStreamContent(MoodleWS_Engine.getWSMethodConnection(strings[0]));
            Log.i(INFO_TAG, content);
            return content;
        }

        @Override
        public void onPostExecute(String s) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (s.length() < 1) {
                Log.e(ERR_TAG, "The returned string is null");
            } else {
                Log.i(INFO_TAG, "The returned string OK...parsing to list");
                //process return XML data for presentation
                ContentParser cp = new ContentParser();
                units = cp.parseDocument(s);
                Log.i(INFO_TAG, String.valueOf(s.length()));
                Log.i(INFO_TAG, String.valueOf(units.size()));
            }

        }


    }
}
