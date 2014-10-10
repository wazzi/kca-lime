package lime.wazza.org.kca_lime;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class ImgListView extends ActionBarActivity {

    String[] titles;
    ListView lv;
    int[] images = {R.drawable.assignments1,
            R.drawable.htc_email,
            R.drawable.notifications,
            R.drawable.profile};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister);

        //get reference to array resources in the strings.xml file
        Resources resc = getResources();
        titles = resc.getStringArray(R.array.operations);
        lv = (ListView) findViewById(R.id.listView);

        //create a custom adapter instance
        CustomAdapter adapter = new CustomAdapter(this, titles, images);
        lv.setAdapter(adapter);

    }


    //create a custom adapter

    class CustomAdapter extends ArrayAdapter<String> {

        Context context;
        String[] titlesArray;
        int[] images;

        public CustomAdapter(Context c, String[] titles, int[] imgs) {
            /**
             * override the constructor in ArrayAdapter
             */
            super(c, R.layout.list_row_element, R.id.txtElementView, titles);
            this.context = c;
            this.images = imgs;
            this.titlesArray = titles;
        }


        //this method is called for creating and populating each row in the listview
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //use inflater to get xml layout available in java code
            View row = inflater.inflate(R.layout.list_row_element, parent, false);

            ImageView imgView = (ImageView) row.findViewById(R.id.imageView);
            TextView txtView = (TextView) row.findViewById(R.id.txtElementView);

            imgView.setImageResource(images[position]);
            txtView.setText(titlesArray[position]);
            return row;


        }
    }


}
