package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private  LatLng location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);


            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.maps);
            mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Current Location");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                SharedPreferences sharedPreferences = getSharedPreferences("key1", MODE_PRIVATE);
                final String users = sharedPreferences.getString("name1", "");

                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    if(users.equals(ds.child("names").getValue().toString()))
                    {
                        Double latitude = ds.child("latitude").getValue(Double.class);
                        Double longitude = ds.child("longitude").getValue(Double.class);

                         location = new LatLng(latitude, longitude);
                    }
                }

                if(marker == null) {
                    marker = mMap.addMarker(new MarkerOptions().position(location).title("Marker"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14F));
                }

                if(marker != null)
                {
                    marker.remove();
                    marker = mMap.addMarker(new MarkerOptions().position(location).title("Marker"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14F));
                    Toast.makeText(MapsActivity2.this, "Updated Location", Toast.LENGTH_SHORT).show();

                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MapsActivity2.this,Records.class));
        finish();
    }
}
