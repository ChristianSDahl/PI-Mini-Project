package com.example.christian.pollvotingsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Christian on 27-04-2017.
 */

public class Create_Poll extends AppCompatActivity {

    public Button backtooverview;
    public Button savepoll;
    public EditText description;
    public EditText option1;
    public EditText option2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_create_poll);
        backtooverview = (Button) findViewById(R.id.backtooverview);
        savepoll = (Button) findViewById(R.id.savepoll);

        description = (EditText) findViewById(R.id.editDesc);
        option1 = (EditText) findViewById(R.id.editButton1);
        option2 = (EditText) findViewById(R.id.editButton2);
    }

    public void savePoll(View view){
        String finalDescription = description.getText().toString();
        String finalOption1 = option1.getText().toString();
        String finalOption2 = option2.getText().toString();
        String finalPoll = "0," + "0," + finalDescription + "," + finalOption1 + "," + finalOption2;

        Log.d("done", finalPoll);
    }




public void backToOverview(View view){
    Intent i = new Intent(Create_Poll.this, Poll_Overview.class);
    startActivity(i);
}

}