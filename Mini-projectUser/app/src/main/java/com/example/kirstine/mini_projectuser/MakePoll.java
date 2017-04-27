package com.example.kirstine.mini_projectuser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Kirstine on 27-04-2017.
 */

public class MakePoll extends AppCompatActivity {
    public Button savebutton;
    public EditText questionbutton;
    public EditText optionOne;
    public EditText optionTwo;

    protected void onCreate(Bundle savedInstanceState) {
        savebutton = (Button) findViewById(R.id.save);
        questionbutton = (EditText) findViewById(R.id.question);
        optionOne = (EditText) findViewById(R.id.optionOne);
        optionTwo = (EditText) findViewById(R.id.optionTwo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_make_poll);
    }

    //Called when the user touches button "back"
    public void back1 (View view) {
        //Goes back to the Menu
        Intent i = new Intent(MakePoll.this, MainMenu.class);
        startActivity(i);
    }

    //Called when the user touches button "save"
    public void save (View view) {
        questionbutton = (EditText) findViewById(R.id.question);
        CharSequence question = questionbutton.getText();
        CharSequence one = optionOne.getText().toString();
        CharSequence two = optionTwo.getText().toString();
        String questionString = question.toString();
//        String toServer = "createpoll" + ",0" + ",0" + "," + question + "," + one + two;
        Log.d("savetest",questionString);
    }

}
