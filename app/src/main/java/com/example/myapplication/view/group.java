package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.DAO.GroupDao;
import com.example.myapplication.R;
import com.example.myapplication.firebaseHelper.FileUploader;
import com.example.myapplication.model.Group;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class group extends AppCompatActivity{
    private TextView groupname;
    private ImageView imageGroup;
    private Group group;
    private GroupDao groupDaoGI;
    private FileUploader  fileUploader;
    Fragment selectedFragment;
    private Bundle bundle;

    public void init()  {
        // **** VIEW *******
        imageGroup = (ImageView) findViewById(R.id.imageGroup);
        groupname = (TextView) findViewById(R.id.groupname);
        //
        // ***** DAO ********
        groupDaoGI = new GroupDao(getApplicationContext(), "gi3");
        group = groupDaoGI.getGroupDao();
        //
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        init();
        load(groupname, imageGroup);
        bundle = new Bundle();
        this.selectedFragment = new group_posts();

        BottomNavigationView topNav = findViewById(R.id.top_navigation);
        topNav.setOnNavigationItemSelectedListener(navListener);
        Fragment initialFragment = new group_posts();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                initialFragment).commit();

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.
            OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            group.this.selectedFragment = new group_posts();

            switch (item.getItemId()) {
                case R.id.nav_posts:
                    group.this.selectedFragment = new group_posts();
                    break;
                case R.id.nav_files:
                    group.this.selectedFragment = new group_files();


                    break;
                case R.id.nav_members:
                    group.this.selectedFragment = new group_members();

                    break;
            }
            bundle.putString( "level","gi3");
            group.this.selectedFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    group.this.selectedFragment).commit();
            return true;
        }
    };

    public synchronized void loadImages(String groupurl, final ImageView imageview) {
        fileUploader = new FileUploader(groupurl);
        fileUploader.getHttpsReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(imageview);
            }
        });
    }

    public synchronized void load(final TextView textView, final ImageView imageView){
        groupDaoGI.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textView.setText( "GROUP " + group.getName());
                loadImages(group.getUrlImage(), imageView);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
