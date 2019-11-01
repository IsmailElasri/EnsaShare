package com.example.myapplication.DAO;
import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.myapplication.model.Group;
import com.example.myapplication.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public  class GroupDao {
    private DatabaseReference ref;
    private Group groupdao;
    private Context context;
    private boolean received;

    public DatabaseReference getRef() {
        return ref;
    }

    public void setRef(DatabaseReference ref) {
        this.ref = ref;
    }

    public Group getGroupdao() {
        return groupdao;
    }

    public void setGroupdao(Group groupdao) {
        this.groupdao = groupdao;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public GroupDao(final Context context, String groupNameClicked){
          received = false;
          ref = FirebaseDatabase.getInstance().getReference().child("groups").child("group:"+groupNameClicked);
          this.context = context;
          groupdao = new Group();
    }

    public synchronized Group getGroupDao(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.child("students").getValue();
                String name = dataSnapshot.child("name").getValue().toString();
                String urlImage = dataSnapshot.child("urlImage").getValue().toString();
                String groupId =  dataSnapshot.child("urlImage").getValue().toString();
                GroupDao.this.groupdao.setName(name);
                GroupDao.this.groupdao.setUrlImage(urlImage);
                GroupDao.this.groupdao.setGroupId(groupId);




                Toast.makeText(context, "Name : " +  groupdao.getName() , Toast.LENGTH_LONG).show();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return this.groupdao;
    }





    }
