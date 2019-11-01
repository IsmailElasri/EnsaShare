package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;


public class MainActivity extends AppCompatActivity {
    // Buttons **********************

    private Button signup1;
    private Button signin;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //BUTTONS EVENTS *********************
        signin = (Button) findViewById(R.id.signIn);
        signup1 = (Button) findViewById(R.id.signUp);
        signup1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               pop_up_signup.showPopUpsignup(MainActivity.this);
            }


        });

        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(),group.class);
                startActivity(i);
            }
        });


    }
}