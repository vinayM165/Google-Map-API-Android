package com.example.mmap_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever_);
        supportMapFragment =(SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Reciever_Activity.this);
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
                getmyLocation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getmyLocation() {
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLatitude(), location.getLongitude()))
                        .title("Receiver's Location!")
                        .snippet("and snippet")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                float zoomLevel = 16.0f; //This goes up to 21
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoomLevel));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Reciever_Activity.this, "Internal Failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected Marker createMarker(double latitude, double longitude) {

        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title("Donor's_Location"));
    }
}

