package com.example.mmap_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Reciever_Activity extends AppCompatActivity implements OnMapReadyCallback {
SupportMapFragment supportMapFragment;
GoogleMap googleMap;
DatabaseReference databaseReference;
List<LatLag> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever_);
        supportMapFragment =(SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
        list = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("LatLag");
        Toast.makeText(this, "This is what a receiver will see!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap =googleMap;
        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot LatSnap: dataSnapshot.getChildren()){
                   LatLag latLag = LatSnap.getValue(LatLag.class);
                   list.add(latLag);
                   assert latLag != null;
                   Toast.makeText(Reciever_Activity.this, latLag.getLatitude()+" "+latLag.getLongitude(), Toast.LENGTH_SHORT).show();
               }
                for(int i = 0 ; i < list.size() ; i++) {

                    createMarker(list.get(i).getLatitude(), list.get(i).getLongitude());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    protected Marker createMarker(double latitude, double longitude) {

        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title("title"));
    }
}

