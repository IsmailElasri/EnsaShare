package com.example.myapplication.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DAO.StudentDao;
import com.example.myapplication.R;
import com.example.myapplication.firebaseHelper.FileUploader;
import com.example.myapplication.model.Student;

public class signupstudent2 extends AppCompatActivity {
    private ImageView imagebacksignup1;
    private Intent intent;
    private EditText username ;
    private EditText password ;
    private EditText passwordConfimed;
    private Button submit ;
    private StudentDao studentdao;
    FileUploader fileUploader ;
    private Student  student ;



    public void init(){
        username =(EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        passwordConfimed = (EditText) findViewById(R.id.passwordconfirmed);
        submit = (Button) findViewById(R.id.submit);
        studentdao  = new StudentDao() ;
        imagebacksignup1 = (ImageView) findViewById(R.id.backsignup1Image);
        fileUploader = new FileUploader(getApplicationContext(),
                "students-profile-images");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupstudent2);
        init();

       Bundle extras = getIntent().getExtras();
        student = (Student) extras.getSerializable("student");
        final Uri imagePath = (Uri) extras.get("profileImage");

       //Toast.makeText(signupstudent2.this,imagePath.toString(), Toast.LENGTH_LONG).show();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(passwordConfimed.getText().toString())){

                  student.setUserName(username.getText().toString());
                  student.setPassword(password.getText().toString());

                    fileUploader.uploadFile(imagePath);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                                student.setProfilePicUrl(FileUploader.url);
                                Toast.makeText(getApplicationContext(), "3333" + FileUploader.url, Toast.LENGTH_SHORT).show();
                                studentdao.registerStudent(student);

                        }
                    }, 5000);



                //  Toast.makeText(signupstudent2.this,student.getProfilePicUrl(), Toast.LENGTH_LONG).show();


                }else{

                    Toast.makeText(signupstudent2.this,"passwords don t match ", Toast.LENGTH_LONG).show();
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

