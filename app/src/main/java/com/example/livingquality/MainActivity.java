package com.example.livingquality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView humidity;
    private TextView light;
    private TextView air;
    private TextView sound;
    private Button backButton;
    private ListView listHumidity;
    private ListView listLight;
    private ListView listAir;
    private ListView listSound;
    private ListView listNo;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listHumidity = findViewById(R.id.humidityList);
        listLight = findViewById(R.id.lightList);
        listAir = findViewById(R.id.airList);
        listSound = findViewById(R.id.soundList);
        listNo = findViewById(R.id.noList);

        ArrayList<String> humidity = new ArrayList<>();
        ArrayList<String> light = new ArrayList<>();
        ArrayList<String> air = new ArrayList<>();
        ArrayList<String> sound = new ArrayList<>();
        ArrayList<String> no = new ArrayList<>();

        ArrayAdapter<String> adapterHumidity = new ArrayAdapter<String>(this, R.layout.list_item, humidity);
        ArrayAdapter<String> adapterLight = new ArrayAdapter<String>(this, R.layout.list_item, light);
        ArrayAdapter<String> adapterAir = new ArrayAdapter<String>(this, R.layout.list_item, air);
        ArrayAdapter<String> adapterSound = new ArrayAdapter<String>(this, R.layout.list_item, sound);
        ArrayAdapter<String> adapterNo = new ArrayAdapter<String>(this, R.layout.list_item, no);

        listHumidity.setAdapter(adapterHumidity);
        listLight.setAdapter(adapterLight);
        listAir.setAdapter(adapterAir);
        listSound.setAdapter(adapterSound);
        listNo.setAdapter(adapterNo);

        reference = FirebaseDatabase.getInstance().getReference().child("Information");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                humidity.clear();
                int i=1;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Information info = snapshot.getValue(Information.class);
                    humidity.add(info.getHumidity()+"");
                    light.add(info.getLight()+"");
                    air.add(info.getAir()+"");
                    sound.add(info.getSound()+"");
                    no.add(i+"");
                    i++;
                }
                adapterHumidity.notifyDataSetInvalidated();
                adapterLight.notifyDataSetInvalidated();
                adapterAir.notifyDataSetInvalidated();
                adapterSound.notifyDataSetInvalidated();
                adapterNo.notifyDataSetInvalidated();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHomeActivity();
            }
        });
    }

    public void goToHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}