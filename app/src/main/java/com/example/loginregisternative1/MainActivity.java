package com.example.loginregisternative1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.net.NetworkInfo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;

import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class  MainActivity extends AppCompatActivity {

    EditText emailLogin,PasswordLogin;
    Button Login;
    TextView SignUpText;
    FirebaseAuth mFirebaseAuth;
    public static int quantitychecker=0;
    public static ArrayList<String> Names= new ArrayList<String>();
    public static ArrayList<Integer> Price = new ArrayList<Integer>();
    public static ArrayList<Integer> Quantity= new ArrayList<Integer>();
    public static ArrayList<Integer> TotalPrice= new ArrayList<Integer>();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public  static  int displaysum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailLogin=findViewById(R.id.emailLogin);
        PasswordLogin=findViewById(R.id.passwordLogin);
        Login=findViewById(R.id.SignIn);
        SignUpText=findViewById(R.id.TextViewLogin);

        mAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

                if(mFirebaseUser!=null)
                {     LoginActivity.pemail= mFirebaseUser.getEmail();
                    Toast.makeText(MainActivity.this,"You are Logged in",Toast.LENGTH_SHORT).show();
                    Names.clear();
                    Price.clear();
                    Quantity.clear();
                    startActivity(new Intent(MainActivity.this,ScanActivity.class));
                }
            }
        };


        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String emailid,pwd;
                emailid=emailLogin.getText().toString();
                pwd=PasswordLogin.getText().toString();
                if(emailid.isEmpty())
                {
                    emailLogin.setError("Please Provide values");
                    emailLogin.requestFocus();
                }
                else if(pwd.isEmpty())
                {
                    PasswordLogin.setError("Please Provide values");
                    PasswordLogin.requestFocus();
                }
                else if(emailid.isEmpty()&& pwd.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please provide values",Toast.LENGTH_LONG).show();
                }
                else if(!(emailid.isEmpty()&& pwd.isEmpty()))
                {
                    if (!checkConnection())
                    {
                        Toast.makeText(MainActivity.this, "Check Internet Connection ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mFirebaseAuth.signInWithEmailAndPassword(emailid,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this,"LoginError",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    startActivity(new Intent(MainActivity.this, ScanActivity.class));
                                }
                            }

                        });
                    }
                } else
                {
                    Toast.makeText(MainActivity.this,"LoginError",Toast.LENGTH_SHORT).show();
                }

            }
        });


        SignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }

    boolean checkConnection()
    {
        ConnectivityManager con= (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenet = con.getActiveNetworkInfo();
        if(null!=activenet)
        {
            if(activenet.getType()== ConnectivityManager.TYPE_WIFI)
            {
                return true;
            }
            else if(activenet.getType()== ConnectivityManager.TYPE_MOBILE)

            {
                return true;
            }


        }
        return  false;
    }
    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);


    }


    /*  Login code to copy*/


}
