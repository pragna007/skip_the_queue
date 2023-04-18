package com.example.databasecontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Products_Details extends AppCompatActivity {
    EditText Name, Price, Barcode;
    EditText Prevpass,Newpass;
    int prevpass2,newpass2;

    DatabaseReference reference,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products__details);
        Name = (EditText) findViewById(R.id.name);
        Price = (EditText) findViewById(R.id.price);
        Barcode = (EditText) findViewById(R.id.barcode);

        reference = FirebaseDatabase.getInstance().getReference("Products");

        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add:
                        final String barcode = Barcode.getText().toString().trim();
                        Query adder = reference.child(barcode);
                        adder.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    new AlertDialog.Builder(Products_Details.this)
                                            .setTitle("UPDATE")
                                            .setMessage("Product already exists do you want to update")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String demo = Name.getText().toString().trim();
                                                    String price = Price.getText().toString().trim();
                                                    int price1 = Integer.parseInt(price);
                                                    String p = "1";
                                                    int p1 = Integer.parseInt(p);
                                                    reference.child(barcode).child("Name").setValue(demo);
                                                    reference.child(barcode).child("Price").setValue(price1);
                                                    reference.child(barcode).child("Quantity").setValue(p1);
                                                    clear();

                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            }).create().show();

                                } else {
                                    String demo = Name.getText().toString().trim();
                                    String price = Price.getText().toString().trim();
                                    int price1 = Integer.parseInt(price);
                                    String p = "1";
                                    int p1 = Integer.parseInt(p);
                                    reference.child(barcode).child("Name").setValue(demo);
                                    reference.child(barcode).child("Price").setValue(price1);
                                    reference.child(barcode).child("Quantity").setValue(p1);
                                    clear();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        return true;
                    case R.id.delete:
                        final String barcode1 = Barcode.getText().toString().trim();
                        final Query check = reference.child(barcode1);
                        check.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    reference.child(barcode1).removeValue();
                                    Barcode.setText("");
                                    Toast.makeText(Products_Details.this,"Product Deleted",Toast.LENGTH_LONG).show();
                                } else {
                                    new AlertDialog.Builder(Products_Details.this)
                                            .setTitle("ERROR")
                                            .setMessage("Product does not exists please check the bar code once again")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            }).create().show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        return  true;
                    case R.id.pass:
                        password = FirebaseDatabase.getInstance().getReference("Password");
                        Query passkey = FirebaseDatabase.getInstance().getReference("Password").child("PassKey");
                        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.alert_layout, null);
                        final EditText Prevpass = view.findViewById(R.id.prevpass);
                        final EditText Newpass = view.findViewById(R.id.newpass);


                        new AlertDialog.Builder(Products_Details.this)
                                .setTitle("CHANGE PASSWORD")
                                .setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String Newpass1 = Newpass.getText().toString().trim();
                                String prevpass1 = Prevpass.getText().toString().trim();

                                if(Newpass1.length()<4)
                                {
                                    Toast.makeText(Products_Details.this, "Password should be a number and 4 digits minimum", Toast.LENGTH_LONG).show();
                                }

                                if(!Newpass1.isEmpty()&&!prevpass1.isEmpty()) {
                                    prevpass2 = Integer.parseInt(prevpass1);
                                    try {
                                        newpass2 = Integer.parseInt(Newpass1);
                                    } catch (Exception e) {
                                        Toast.makeText(Products_Details.this, "Password should be a number and 4 digits minimum", Toast.LENGTH_LONG).show();
                                    }
                                }
                                password.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists())
                                        {
                                            long key= dataSnapshot.child("PassKey").getValue(Long.class);

                                            if(prevpass2==key&&newpass2!=0)
                                            {
                                                password.child("PassKey").setValue(newpass2);
                                                Toast.makeText(Products_Details.this, "Password Changed", Toast.LENGTH_LONG).show();

                                            }
                                            else {
                                            new AlertDialog.Builder(Products_Details.this)
                                                    .setTitle("ERROR")
                                                    .setMessage("WRONG PREVIOUS PASSWORD")
                                                    .create().show();
                                        }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }).create().show();








                        return true;


                }

                return true;

            }

        });



    }

    private void clear() {
        Barcode.setText("");
        Name.setText("");
        Price.setText("");
    }






}

