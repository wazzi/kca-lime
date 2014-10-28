package lime.wazza.org.kca_lime.auxillary;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by kelli on 10/20/14.
 * <p/>
 * Read XML reposnses from Moodle webs service and format to UI
 * on appropriate activity.
 */
public class ContentParser {

    private ArrayList<Unit> units;
    private Unit unit;
    private String keyFlag;
    private XmlPullParser xpp;

    /**
     * Create XML parser
     */
    public ContentParser() {
        units = new ArrayList<Unit>();
        unit = new Unit();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            xpp = factory.newPullParser();
            System.out.println("Created parser...");
        } catch (XmlPullParserException e) {
            Log.d("PARSER_LOG", "error in creating parser");
            e.printStackTrace();
        }
    }

    /**
     * Get course details from the moodle web service function: get_course_content
     */
    public ArrayList<Unit> parseDocument(String document) {
        if (document == null) {
            System.out.println("This  parameter is null");
            System.exit(1);
        }
        try {
            xpp.setInput(new StringReader(document));
            int eventType = xpp.getEventType();
            while (eventType != xpp.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        //get the name of the tag
                        String startTagName = xpp.getName();

                        //if tag name is 'single'...
                        if (startTagName.equals("single")) {
                            System.out.println("Create new unit instance...");
                        }
                        //if tag name is 'key'...
                        else if (startTagName.equals("key")) {

                            //get the value tags for keys with attibs; id and fullname...
                            String attribName = xpp.getAttributeValue(null, "name");
                            if (attribName.equals("fullname")) {

                                //set flag value
                                keyFlag = "fullname";
                            } else if (attribName.equals("idnumber")) {

                                keyFlag = "idnumber";
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (xpp.getName().equals("single")) {
                            System.out.println("Adding unit with code: " + unit.getCode() + " and Name " + unit.getFullName());
                            units.add(unit);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if (keyFlag.equals("fullname")) {
                            System.out.println("fullname: " + xpp.getText());
                            unit.setFullName(xpp.getText());
                        } else if (keyFlag.equals("idnumber")) {
                            System.out.println("idnumber: " + xpp.getText());
                            unit.setCode(xpp.getText());
                        }
                        break;
                    default:
                        break;
                }
                eventType = xpp.next();
            }

            int count = units.size();
            System.out.println("number of units: " + count);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return units;
    }

    /**
     * Test utility
     * Get the number of units retrived from Moodle
     *
     * @param units
     */
    public void getUnitCount(ArrayList<Unit> units) {
        if (units != null)
            for (Unit u : units) {
                System.out.println(u.getFullName());
                System.out.println(u.getCode());
//            System.out.println(u.getId());
            }
        else {
            System.out.println("Units is null");
        }
    }
}
