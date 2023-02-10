package com.example.databasecontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText PassKey;
    ImageButton key;
    DatabaseReference passkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PassKey=(EditText)findViewById(R.id.passKey);
        key=(ImageButton)findViewById(R.id.Key);


        key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 passkey = FirebaseDatabase.getInstance().getReference("Password");
                passkey.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                           Long key= dataSnapshot.child("PassKey").getValue(Long.class);
                           String pass=PassKey.getText().toString().trim();
                           int pass1=Integer.parseInt(pass);
                            if (pass1== key) {
                                PassKey.setText("");
                                startActivity(new Intent(MainActivity.this, Products_Details.class));
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_LONG).show();
                            }

                        }

                        else{
                            Toast.makeText(MainActivity.this,"Snapshot doesn't exists",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


    }
}
