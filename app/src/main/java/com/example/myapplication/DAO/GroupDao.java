package com.example.myapplication.DAO;
import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.model.Admin;
import com.example.myapplication.model.Group;
import com.example.myapplication.model.Student;
import com.example.myapplication.model.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public  class GroupDao {
    private DatabaseReference ref;
    private Group groupdao;
    private Context context;

    public Admin getAdmindao() {
        return admindao;
    }

    public void setAdmindao(Admin admindao) {
        this.admindao = admindao;
    }

    private Admin admindao;
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

    public GroupDao(final Context context, String groupNameClicked) {
        ref = FirebaseDatabase.getInstance().getReference().child("groups").child("group:" + groupNameClicked);
        this.context = context;
        groupdao = new Group();
    }

    public synchronized Group getGroupDao() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Admin admin;
                String name = dataSnapshot.child("name").getValue().toString();
                String urlImage = dataSnapshot.child("urlImage").getValue().toString();
                String groupId = dataSnapshot.child("groupId").getValue().toString();
                Object data = (HashMap<String, Object>) dataSnapshot.child("Admin").getValue();
                HashMap<String, Object> dataMap = (HashMap<String, Object>) data;
                admin = new Admin((String) dataMap.get("firstName"), (String) dataMap.get("lastName"), (String) dataMap.get("userName"),
                       (String) dataMap.get("level"), (String) dataMap.get("profilePicUr"));
                GroupDao.this.groupdao.setName(name);
                GroupDao.this.groupdao.setUrlImage(urlImage);
                GroupDao.this.groupdao.setGroupId(groupId);
                GroupDao.this.groupdao.setAdmin(admin);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return this.groupdao;
    }

    public Group getListsGroupDao() {
        List<Student> studentList = new ArrayList<Student>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Student> studentList = new ArrayList<Student>();
                List<Teacher> teacherList = new ArrayList<Teacher>();
                //Toast.makeText(context, "Name : " + "okey", Toast.LENGTH_LONG).show();
                HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.child("students").getValue();
                for (String key : dataMap.keySet()) {
                    Object data = dataMap.get(key);
                    try {
                        HashMap<String, Object> studentData = (HashMap<String, Object>) data;
                        Student student = new Student((String) studentData.get("firstName"), (String) studentData.get("lastName"), (String) studentData.get("userName"), (String) studentData.get("level"), (String) studentData.get("profilePicUrl"));
                   // Toast.makeText(context, "picture : " + student.getProfilePicUrl(), Toast.LENGTH_LONG).show();
                        studentList.add(student);
                    } catch (Exception e) {  }
                }
                dataMap = (HashMap<String, Object>) dataSnapshot.child("teachers").getValue();
                for (String key : dataMap.keySet()) {
                    Object data = dataMap.get(key);
                    try {
                        HashMap<String, Object> teacherData = (HashMap<String, Object>) data;
                        Object levels = (Object) dataSnapshot.child("teachers").child(key).child("levels").getValue();
                        ArrayList<String> levelList = (ArrayList) levels;
                        Teacher teacher = new Teacher((String) teacherData.get("firstName"), (String) teacherData.get("lastName"), (String) teacherData.get("userName"),
                                levelList , (String) teacherData.get("profilePicUrl"));
                    } catch (Exception e) {  Toast.makeText(context, "exception" , Toast.LENGTH_LONG).show(); }
                }

                GroupDao.this.groupdao.setListStudents(studentList);
                Admin admin;
                Object data = (HashMap<String, Object>) dataSnapshot.child("Admin").getValue();
                dataMap = (HashMap<String, Object>) data;
                admin = new Admin((String) dataMap.get("firstName"), (String) dataMap.get("lastName"), (String) dataMap.get("userName"),
                        (String) dataMap.get("level"), (String) dataMap.get("profilePicUrl"));
                GroupDao.this.groupdao.setAdmin(admin);
                //Toast.makeText(context, admin.getProfilePicUrl() , Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return this.groupdao;

    }









    }
