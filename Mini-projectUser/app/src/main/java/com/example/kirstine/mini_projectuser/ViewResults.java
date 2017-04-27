package com.example.kirstine.mini_projectuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Kirstine on 27-04-2017.
 */

public class ViewResults extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_results);
    }
    //Called when the user touches button "back"
    public void back2 (View view) {
        //Goes back to the Menu
        Intent i = new Intent(ViewResults.this, MainMenu.class);
        startActivity(i);
    }
}
