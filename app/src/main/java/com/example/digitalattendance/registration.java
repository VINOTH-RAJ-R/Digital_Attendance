package com.example.digitalattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registration extends AppCompatActivity {
    EditText emailId,Password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;




    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView (R.layout.activity_registration);

        mFirebaseAuth = FirebaseAuth.getInstance ();
        emailId = findViewById (R.id.EmailId_singup);
        Password = findViewById (R.id.Password_signup);
        btnSignUp = findViewById (R.id.signup);
        tvSignIn = findViewById (R.id.signuptosignin);
        btnSignUp.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                String email = emailId.getText ().toString ();
                String pass = Password.getText ().toString ();
                if (email.isEmpty ()){
                    emailId.setError ("email enter painra body soda");
                    emailId.requestFocus ();
                }
                else if (pass.isEmpty ()){
                    Password.setError ("password enter painra body soda");
                    Password.requestFocus ();
                }
                else if (email.isEmpty () && pass.isEmpty ()){
                    Toast.makeText (registration.this,"email password ha enter painra body soda",Toast.LENGTH_LONG).show ();
                }
                else if (!(email.isEmpty () && pass.isEmpty ())){
                    mFirebaseAuth.createUserWithEmailAndPassword (email,pass).addOnCompleteListener (registration.this, new OnCompleteListener<AuthResult> () {
                        @Override
                        public void onComplete (@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful ()){
                                Toast.makeText (registration.this,"SignUp unsuccessful, please try again",Toast.LENGTH_LONG).show ();
                            }
                            else{
                                startActivity (new Intent (registration.this,MainActivity.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText (registration.this,"Error!!",Toast.LENGTH_LONG).show ();
                }
            }
        });
        tvSignIn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent i =  new Intent (registration.this,login.class);
                startActivity (i);
            }
        });
    }
}