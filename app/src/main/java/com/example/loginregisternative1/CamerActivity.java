package com.example.loginregisternative1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


import static com.example.loginregisternative1.MainActivity.Names;
import static com.example.loginregisternative1.MainActivity.Price;
import static com.example.loginregisternative1.MainActivity.Quantity;
public class CamerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    DatabaseReference getproducts,purchases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result result) {

        ScanActivity.ScannedCode= result.getText();
        purchases=FirebaseDatabase.getInstance().getReference();

        getproducts= FirebaseDatabase.getInstance().getReference("Products");
       // Toast.makeText(ScanActivity.class,ScanActivity.ScannedCode,Toast.LENGTH_SHORT).show();
        Query getProduct =getproducts.child(ScanActivity.ScannedCode);


        getProduct.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                     String pdname= dataSnapshot.child("Name").getValue(String.class);
                     int pdprice= dataSnapshot.child("Price").getValue(Integer.class);
                     int pdquantity=dataSnapshot.child("Quantity").getValue(Integer.class);
                     Names.add(pdname);
                     Price.add(pdprice);
                     Quantity.add(pdquantity);

                     startActivity(new Intent(CamerActivity.this,ScanActivity.class));
                }
                else
                {
                  //  Toast.makeText(ScanActivity.this,"does not exists",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

             //   Toast.makeText(ScanActivity.this,"dr error",Toast.LENGTH_SHORT).show();
            }
        });


        // startActivity(new Intent(CamerActivity.this,ScanActivity.class));

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();

    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
