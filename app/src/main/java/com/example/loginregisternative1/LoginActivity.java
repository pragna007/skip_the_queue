package com.example.loginregisternative1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class LoginActivity extends AppCompatActivity {
    EditText email,pass;
    Button   SignUp1;
    TextView SignIntext;
    FirebaseAuth mFirebaseAuth;
   public static String pemail;


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.EmailSignUp);
        pass=findViewById(R.id.PasswordSignUp);
        SignUp1=findViewById(R.id.SignUp);
        SignIntext=findViewById(R.id.Logintext);


        SignUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final   String emailid,pwd;
                emailid=email.getText().toString();
                pwd=pass.getText().toString();

                if(emailid.isEmpty())
                {
                    email.setError("Please Provide values");
                    email.requestFocus();
                }
                else if(pwd.isEmpty())
                {
                    pass.setError("Please Provide values");
                    pass.requestFocus();
                }
                else if(emailid.isEmpty()&& pwd.isEmpty())
                {
                    Toast.makeText(LoginActivity.this,"Please provide values",Toast.LENGTH_LONG).show();
                }
                else if(!(emailid.isEmpty()&& pwd.isEmpty()))
                {
                    if (!checkConnection())
                    {
                        Toast.makeText(LoginActivity.this, "Check Internet Connection ", Toast.LENGTH_SHORT).show();
                    } else {

                        mFirebaseAuth.signInWithEmailAndPassword(emailid,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(!task.isSuccessful())
                                {
                                    mFirebaseAuth.createUserWithEmailAndPassword(emailid, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(LoginActivity.this, "SignUp unsuccesful !! Please try Again", Toast.LENGTH_LONG).show();
                                            }
                                            else
                                            {
                                                 pemail=emailid;
                                                Toast.makeText(LoginActivity.this, "SignUp succesful", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(LoginActivity.this,ScanActivity.class));

                                            }


                                        }
                                    });
                                } else
                                {
                                    Toast.makeText(LoginActivity.this, "You are already an User", Toast.LENGTH_LONG).show();
                                }

                            }
                        });



                    }

                }

            }


        });

        SignIntext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
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

}
