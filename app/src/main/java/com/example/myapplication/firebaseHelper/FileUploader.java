package com.example.myapplication.firebaseHelper;


import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class FileUploader {
    private StorageReference mStorageRef;
    private StorageReference  fileReference;
    private StorageReference  httpsReference;
    private Context context ;
    public static String url;

    public Uri getUri() {
        return uri;
    }

    private Uri uri;


    public FileUploader(String  httpsRef) {
        httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(httpsRef);
    }

    public void downloadUri(){
        httpsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                FileUploader.this.uri = uri;
            }
        });
    }


    public FileUploader(Context context, String location ) {
        mStorageRef = FirebaseStorage.getInstance().getReference(location);
        this.context=context;
    }


    private String getFileExtension(Uri uri ) {

        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadFile( Uri uri) {

        if (uri != null) {
            fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(uri));

            fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                          fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                              @Override
                                public void onSuccess(Uri uri) {
                                  Toast.makeText(context,  uri.toString(), Toast.LENGTH_SHORT).show();
                                  FileUploader.url = uri.toString();

                                //  Toast.makeText(context, " this is url " + url, Toast.LENGTH_SHORT).show();

                                }
                            });

                          // url=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                          // Toast.makeText(context, "File Uploaded "+" "+ url, Toast.LENGTH_LONG).show();

                        }

                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override

                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context , "failed to upload", Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }

    public StorageReference getHttpsReference() {
        return httpsReference;
    }


}



