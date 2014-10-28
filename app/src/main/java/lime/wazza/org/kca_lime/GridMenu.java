package lime.wazza.org.kca_lime;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import lime.wazza.org.kca_lime.auxillary.MenuControl;
import lime.wazza.org.kca_lime.auxillary.Unit;


public class GridMenu extends ActionBarActivity {

    GridView gv;
    private static ArrayList<Unit> units;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_menu);
        gv = (GridView) findViewById(R.id.gridView);
        gv.setAdapter(new AppAdapter(this));
        gv.setOnItemClickListener(new AppAdapter(this));
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

            //detect the menu item chosen and create appropriate UI
            String output = MenuControl.getSelectionById(getApplicationContext(), i);

            //parse the xml document received
//            ContentParser cp = new ContentParser();
//            units = cp.parseDocument(output);

            //inflate the view for results..
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View resultsView = inflater.inflate(R.layout.single_unit_element, adapterView, false);
            TextView view1 = (TextView) resultsView.findViewById(R.id.singleUnitView);
            view1.setText(units.size());
//            setContentView(R.layout.single_unit_element);
//            Toast.makeText(view.getContext(),"The clicked ID id: "+i,Toast.LENGTH_LONG).show();
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


}
