package com.example.myapplication.model;

public class Student extends User  {

   private String studentId ;
   protected String level ;

   public Student() {
    }

    public Student(String firstName, String lastName, String userName, String level, String profilePicUrl){
       this.firstName = firstName;
       this.lastName = lastName;
       this.userName = userName;
       this.profilePicUrl = profilePicUrl;
       this.level = level;
    }
   public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }



    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public void partagerInformaion() {

    }


    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", level='" + level + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", profilePicUrl='" + profilePicUrl + '\'' +
                '}';
    }
}

