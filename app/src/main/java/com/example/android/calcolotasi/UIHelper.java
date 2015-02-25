package com.example.android.calcolotasi;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by innocenzo on 09/02/15.
 */
public class UIHelper {

    public static void displayText(Activity activity, int id, String text) {
        TextView tv = (TextView) activity.findViewById(id);
        tv.setText(text);
    }

    public static String getText(Activity activity, int id) {
        EditText et = (EditText) activity.findViewById(id);
        return et.getText().toString();
    }

    public static boolean getCBChecked(Activity activity, int id) {
        CheckBox cb = (CheckBox) activity.findViewById(id);
        return cb.isChecked();
    }
}
