package lime.wazza.org.kca_lime.auxillary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import lime.wazza.org.kca_lime.R;

/**
 * Created by kelli on 10/13/14.
 */
public class MenuControl {
    public static View getSelectionById(Context context, int id) {
        View v;
        switch (id) {
            case 1:
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.single_unit_element, null, false);

                TextView view = (TextView) v.findViewById(R.id.singleUnitView);
                view.setText("This is the Units view");
                break;
            default:
                LayoutInflater inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater2.inflate(R.layout.single_unit_element, null, false);

                view = (TextView) v.findViewById(R.id.singleUnitView);
                view.setText("This is some other");
        }
        return v;
    }
}

