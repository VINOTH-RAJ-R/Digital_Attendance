package com.example.digitalattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import java.util.List;

public class login extends AppCompatActivity {
    EditText emailId,Password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;



    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView (R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance ();
        emailId = findViewById (R.id.EmailId_singin);
        Password = findViewById (R.id.Password_singin);
        btnSignIn = findViewById (R.id.signin);
        tvSignUp = findViewById (R.id.signintosignup);
        runtimePermission ();
        mAuthStateListener = new FirebaseAuth.AuthStateListener () {

            @Override
            public void onAuthStateChanged (@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser ();
                if (mFirebaseUser != null) {
                    Toast.makeText (login.this, "epdi vadhu chi!!..", Toast.LENGTH_LONG).show ();
                    Intent i = new Intent (login.this, MainActivity.class);
                    startActivity (i);
                } else {
                    Toast.makeText (login.this, "marupadiyum panra body soda", Toast.LENGTH_LONG).show ();
                }

            }
        };
        btnSignIn.setOnClickListener (new View.OnClickListener () {
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
                    Toast.makeText (login.this,"email password ha enter painra body soda",Toast.LENGTH_LONG).show ();
                }
                else if (!(email.isEmpty () && pass.isEmpty ())){
                    mFirebaseAuth.signInWithEmailAndPassword (email,pass).addOnCompleteListener (login.this, new OnCompleteListener<AuthResult> () {
                        @Override
                        public void onComplete (@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful ()){
                                Toast.makeText (login.this,"Login error, login again",Toast.LENGTH_LONG).show ();
                            }
                            else{
                                Intent intToHome = new Intent (login.this,MainActivity.class);
                                startActivity (intToHome);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText (login.this,"Error!!",Toast.LENGTH_LONG).show ();
                }
            }
        });
        tvSignUp.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Intent intSignUp  = new Intent (login.this,registration.class);
                startActivity (intSignUp);
            }
        });
    }
    public void runtimePermission()
    {
        Dexter.withActivity (this)

                .withPermissions (Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA)


                .withListener (new MultiplePermissionsListener () {
                    @Override
                    public void onPermissionsChecked (MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted ()) {
                            // do you work now
                            //Toast.makeText (login.this, "All the permissions are granted..", Toast.LENGTH_SHORT).show ();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown (List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest ();
                    }
                }).withErrorListener(new PermissionRequestErrorListener () {
                    // this method is use to handle error
                    // in runtime permissions
                    @Override
                    public void onError(DexterError error) {
                        // we are displaying a toast message for error message.
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                }).onSameThread().check();


    }


}