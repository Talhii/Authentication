package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Guest extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference,myRef;

    private guestInfo guest;
    EditText name,city,quater,User_id;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);


        guest = new guestInfo();
        reference = database.getReference().child("GuestInfo");
        myRef = database.getReference().child("Host");


        name = findViewById(R.id.name);
        city = findViewById(R.id.cname);
        quater = findViewById(R.id.quater);
        User_id = findViewById(R.id.user_id);
    }

    public void Request(View view) {


        guest.setName(name.getText().toString());
        guest.setCity(city.getText().toString());
        guest.setQuater_no(quater.getText().toString());
        guest.setHostID(User_id.getText().toString());



        SharedPreferences sharedPreferences = getSharedPreferences("keys", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name.getText().toString().trim());
        editor.putString("id", User_id.getText().toString().trim());
        editor.apply();

        if (name.getText().toString().trim().length() <8) {
            Toast.makeText(Guest.this, "User Name's Character should be more than 7", Toast.LENGTH_SHORT).show();
        } else {
            first();
        }

    }
    public void first () {


        a = 0;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if ((User_id.getText().toString()).equals(ds.child("service_id").getValue().toString())  ) {
                        reference.push().setValue(guest);
                        Toast.makeText(Guest.this, "Redirecting", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Guest.this, guestNew.class);
                        startActivity(intent);
                        a=1;
                    }

                }
                if (a==0) {
                    Toast.makeText(Guest.this, "Wrong Service_Id", Toast.LENGTH_SHORT).show();
                }

            }

            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
