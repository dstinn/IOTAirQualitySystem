package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private String ledStatus = "OFF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ledStatusRef = database.getReference("ledStatus");

        final TextView statusTextView = findViewById(R.id.statusTextView);
        final Button toggleButton = findViewById(R.id.toggleButton);

        ledStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ledStatus = snapshot.getValue(String.class);
                statusTextView.setText("The LED is " + ledStatus);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("ON".equals(ledStatus)){
                    ledStatusRef.setValue("OFF");
                    Toast.makeText(MainActivity.this, "Turned the LED OFF", Toast.LENGTH_SHORT).show();
                }
                else{
                    ledStatusRef.setValue("ON");
                    Toast.makeText(MainActivity.this, "Turned the LED ON", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}