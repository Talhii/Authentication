package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    EditText username, password;
    int a ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        myRef = database.getReference().child("Host");

        username = findViewById(R.id.username);
        password = findViewById(R.id.pswd);

    }


    String pass,user;
    public void Login(View view) {

        user = username.getText().toString();
        pass = password.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("key", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user",user.trim());
        editor.apply();

        try {

            myRef.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   a=0;
                   for(DataSnapshot ds: dataSnapshot.getChildren()){

                       if(user.equals(ds.child("service_id").getValue().toString() ))
                       {
                           if (pass.equals(ds.child("password").getValue().toString())) {
                               Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(login.this, Records.class);
                                a=1;
                               startActivity(intent);
                           }
                       }

                   }
                   if(a==0)
                   {
                       Toast.makeText(login.this, "Wrong user name or password", Toast.LENGTH_SHORT).show();

                   }
               }



             @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });

       }
       catch (Exception e)
       {

           Toast.makeText(login.this, "logical error", Toast.LENGTH_SHORT).show();
           e.printStackTrace();
       }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
