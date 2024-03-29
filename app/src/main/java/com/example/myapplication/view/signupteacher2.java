package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DAO.TeacherDao;
import com.example.myapplication.R;
import com.example.myapplication.model.Teacher;

public class signupteacher2 extends AppCompatActivity {
    private ImageView imagebacksignup1;
    private Intent intent;
    private EditText username ;
    private EditText password ;
    private EditText passwordConfimed;
    private Button submit ;
    private TeacherDao teacherdao;

    private Teacher teacher ;



    public void init(){
        username =(EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        passwordConfimed = (EditText) findViewById(R.id.passwordconfirmed);
        submit = (Button) findViewById(R.id.submit);
        teacherdao = new TeacherDao() ;
        imagebacksignup1 = (ImageView) findViewById(R.id.backsignup1Image);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupteacher2);
        init();

        Intent i = getIntent();
       teacher = (Teacher) i.getSerializableExtra("teacher");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(passwordConfimed.getText().toString())){

                    teacher.setUserName(username.getText().toString());
                    teacher.setPassword(password.getText().toString());

                  teacherdao.registerTeacher(teacher);

                    Toast.makeText(getApplicationContext(),teacher.toString(), Toast.LENGTH_LONG).show();


                }else{

                    Toast.makeText(getApplicationContext(),"passwords don t match ", Toast.LENGTH_LONG).show();
                }

            }
        });



        imagebacksignup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), signup1.class);
                startActivity(intent);

            }
        });

            }
}
