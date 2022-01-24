package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Random;


public class Records extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef,newref,codes,loc;

    Random r = new Random();
    private VerificattionData verificattionData;


    String name;
    TextView gName,gCName,gQuater;
    Button allow;
    int a,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);


        verificattionData = new VerificattionData();

        SharedPreferences sharedPreferences = getSharedPreferences("key", MODE_PRIVATE);
        final String user = sharedPreferences.getString("user", "");




        myRef = database.getReference().child("Guest");
        newref = database.getReference().child("GuestInfo");
        codes = database.getReference().child("Code");
        loc = database.getReference().child("Current Location");


        gName = findViewById(R.id.gname);
        gCName = findViewById(R.id.gcname);
        gQuater = findViewById(R.id.gquater);
        allow = findViewById(R.id.allow);


        newref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                a=0;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (user.equals(ds.child("hostID").getValue().toString())) {

                        name = ds.child("name").getValue().toString();
                        gName.setText(name);

                        SharedPreferences sp = getSharedPreferences("key1", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("name1", name);
                        editor.apply();

                        gCName.setText(ds.child("city").getValue().toString());
                        gQuater.setText(ds.child("quater_no").getValue().toString());
                        a=1;
                    }

                }
                if(a==0) {

                    gName.setText("No guest");
                    gCName.setText("No guest");
                    gQuater.setText("No guest");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


            allow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(name != null) {
                        verificattionData.setUser(name);
                        verificattionData.setCode(r.nextInt(9999 - 1111) + 1111);
                        verificattionData.setHost(user);
                        codes.child(name).setValue(verificattionData, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                Toast.makeText(Records.this, "Code Generted", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(Records.this, "Guest not recognized", Toast.LENGTH_SHORT).show();
                    }

                }

            });
        }



    public void check(View view) {
        loc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                b=0;

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    if(name!= null && name.equals(ds.child("names").getValue().toString() ))
                    {
                        startActivity(new Intent(Records.this,MapsActivity2.class));
                        b=1;
                    }

                }
                if(b==0)
                {
                    Toast.makeText(Records.this, "Guest has not shared their location", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Records.this,login.class));
        finish();
    }
}

