package lime.wazza.org.kca_lime;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

//import static lime.wazza.org.kca_lime.R.id.txtListElement;


public class Home extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        ListView view;//get a reference to the list view layout resource
//        view = (ListView) findViewById(R.id.listView);
//        adapter = new AppHomeAdapter();
//        view.setAdapter(adapter);
    }


//    /**
//     * Create an  adapter to use with the list
//     */
//    public class AppHomeAdapter extends BaseAdapter {
//
//        //collection of strings to be used as the list items
//        ArrayList<String> items;
//
//        public AppHomeAdapter() {
//
//            items = new ArrayList<String>();
//            items.add("My Units");
//            items.add("Groups");
//            items.add("Profile");
//            items.add("Assignments");
//            items.add("Messages");
//        }
//
//        public int getCount() {
//            return items.size();
//        }
//
//        public Object getItem(int itemIndex) {
//
//            return getItem(itemIndex);
//        }
//
//        public long getItemId(int index) {
//
//            return index;
//        }
//
//        public View getView(int index, View view, ViewGroup parent) {
//
//
//            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
////            view = inflater.inflate(R.layout.simple_list_item, parent, false);
//
//            String current = items.get(index);
//
//            TextView tv = (TextView) view.findViewById(txtListElement);
//            tv.setText(current);
//            return view;
//
//        }
//
//    }
}
