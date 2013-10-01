package com.msplit;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HomePage extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home_page, menu);
        return true;
    }
}
