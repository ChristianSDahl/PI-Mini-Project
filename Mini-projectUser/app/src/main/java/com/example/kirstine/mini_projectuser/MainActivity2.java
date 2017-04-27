package com.example.kirstine.mini_projectuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.x;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Kirstine on 27-04-2017.
 */


//Array of options, which will run through an ArrayAdapter, that will show in the ListView
//Array of options --> ArrayAdapter --> ListView

//List view: {views: da_items.xml}


public class MainActivity2 extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_viewpolls);

        init();

    }

    //Called when the user touches button "View polls"
    public void message4(View view) {
        //Does something in response to the button click
        Intent i = new Intent(MainActivity2.this, MainMenu.class);
        startActivity(i);
    }

    public void init() {
        String[] pollsFromServer = loadFromServer();

        TableLayout ll = (TableLayout) findViewById(R.id.displayLinear);
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                TableRow desc = new TableRow(this);
                TableRow.LayoutParams pl = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                desc.setLayoutParams(pl);
                TextView description = new TextView(this);
                description.setText("NEWTESTING");
                desc.addView(description);
                ll.addView(desc, i);
            } else {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                Button button = new Button(this);
                Button button2 = new Button(this);
                row.addView(button);
                row.addView(button2);
                ll.addView(row, i);
            }
        }
    }

    public String[] loadFromServer() {
        return null;
    }
}
