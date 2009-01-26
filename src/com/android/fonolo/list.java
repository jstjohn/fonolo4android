package com.android.fonolo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

//edit class to reflect UI
public class list extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("Hello, Android");
        setContentView(tv);
    }
}