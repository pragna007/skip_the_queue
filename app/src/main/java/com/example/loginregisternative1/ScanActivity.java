package com.example.loginregisternative1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.loginregisternative1.MainActivity.Price;
import static com.example.loginregisternative1.MainActivity.Names;
import static com.example.loginregisternative1.MainActivity.Quantity;

public  class  ScanActivity extends AppCompatActivity  {

    public static  String  ScannedCode="8901088000451";
    public static TextView ttotal;
    public  static int firsttime=0;
    public static TextView Value;
    Button Scan;
    RecyclerView recyclerView;
    ImageButton incr,decr;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter nameadapter;




    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ttotal=findViewById(R.id.tv_total);

            recyclerView = (RecyclerView) findViewById(R.id.recycler_cart);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            nameadapter = new nameAdapter(Names);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(nameadapter);
        BottomNavigationView  bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedfragmaent= null;
                switch (item.getItemId())
                {
                    case R.id.nav_scan:
                      startActivity(new Intent(ScanActivity.this,CamerActivity.class));
                      overridePendingTransition(0,0);
                    break;

                }

                return true;
            }
        });





    }


    public    void display_message(int val)
    {
        if(val==1)
        {
            new TTFancyGifDialog.Builder(ScanActivity.this)
                    .setTitle("Quantity Error")
                    .setMessage("Cannot Reduce Quantity below 1 Slide to Delete ")
                    .setPositiveBtnText("Ok")
                    .setPositiveBtnBackground("#c1272d")
                    .setGifResource(R.drawable.quantity)
                    .OnPositiveClicked(new TTFancyGifDialogListener() {
                        @Override
                        public void OnClick() {

                        }
                    })
                    .build();



        }
    }


}
