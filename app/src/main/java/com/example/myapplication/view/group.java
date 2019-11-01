package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.example.myapplication.DAO.GroupDao;
import com.example.myapplication.R;
import com.example.myapplication.firebaseHelper.FileUploader;
import com.example.myapplication.model.Group;
import com.example.myapplication.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.SynchronousQueue;

public class group extends AppCompatActivity{
    private TextView groupname;
    private ImageView imageGroup;
    private Group group;
    private GroupDao groupDaoGI3;
    private FileUploader  fileUploader;


    public void init()  {
        // **** VIEW *******
        groupname = (TextView) findViewById(R.id.groupname);

        //
        // ***** DAO ********
        groupDaoGI3 = new GroupDao(getApplicationContext(), "gi3");
        group = groupDaoGI3.getGroupDao();


        //
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        init();
        load();

        BottomNavigationView topNav = findViewById(R.id.top_navigation);
        topNav.setOnNavigationItemSelectedListener(navListener);
        Fragment initialFragment = new group_chat();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                initialFragment).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.
            OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_chat:
                    selectedFragment = new group_chat();

                    break;
                case R.id.nav_files:
                    selectedFragment = new group_files();

                    break;
                case R.id.nav_members:
                    selectedFragment = new group_members();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        }
    };

    public synchronized void loadImages(String groupurl) {
        Toast.makeText(getApplicationContext(), groupurl, Toast.LENGTH_SHORT).show();
        fileUploader = new FileUploader(groupurl);
        imageGroup = (ImageView) findViewById(R.id.imageGroup);
        Glide.with(getApplication()).load(fileUploader.getHttpsReference()).into(imageGroup);
        Toast.makeText(getApplicationContext(), "222okaaay", Toast.LENGTH_SHORT).show();
    }


    public synchronized void load(){
        groupDaoGI3.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupname.setText( "GROUP " + group.getName());
                loadImages(group.getUrlImage());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
