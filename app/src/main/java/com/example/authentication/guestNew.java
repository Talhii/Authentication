package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class guestNew extends AppCompatActivity {


    TextView textView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference codes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_new);

        textView = findViewById(R.id.edit);



        SharedPreferences sharedPreferences = getSharedPreferences("keys",MODE_PRIVATE);
        final String name = sharedPreferences.getString("name","");
        final String id = sharedPreferences.getString("id","");


        codes = database.getReference().child("Code");


        codes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    if(name.equals(ds.child("user").getValue().toString()) && id.equals(ds.child("host").getValue().toString())){

                        textView.setText((ds.child("code").getValue().toString()));
                        break;

                    }
                    else
                    {
                        textView.setText("Requested");
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }

    public void btnlocation(View view) {

          Intent intent = new Intent(guestNew.this,MapsActivity.class);
          startActivity(intent);
    }
}






