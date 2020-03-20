package com.example.christian_augustyn_aandroid_developer_halfwaythere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText user, email, pass, passv, fname, lname;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        user = findViewById(R.id.userName);
        email = findViewById(R.id.newEmail);
        pass = findViewById(R.id.password);
        passv = findViewById(R.id.passwordV);
        fname = findViewById(R.id.fName);
        lname = findViewById(R.id.lName);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        mRef = db.getReference();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void createAccount(View view) {
        final String new_email = email.getText().toString();
        final String new_pass = pass.getText().toString();
        String new_passv = passv.getText().toString();
        final String new_user = user.getText().toString();
        final String firstname = fname.getText().toString();
        final String lastname = lname.getText().toString();

        if (TextUtils.isEmpty(new_email)) {
            email.setError("This field must contain an email");
            return;
        }
        if (TextUtils.isEmpty(new_pass)) {
            pass.setError("This field must contain a Password");
            return;
        }
        if (!new_pass.equals(new_passv)) {
            passv.setError("The  passwords do not match");
            return;
        }
        if (new_pass.length() < 8) {
            pass.setError("Password must be greater than 7 characters");
            return;
        } else {
            mAuth.createUserWithEmailAndPassword(new_email, new_pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        System.out.println("Account created successfully");
                        ProfileInfo profile = new ProfileInfo(new_user, new_email, firstname, lastname);
                        FirebaseUser user = mAuth.getCurrentUser();
                        mRef.child(user.getUid()).setValue(profile);
                        startActivity(new Intent(Signup.this, MainActivity.class));
                    } else {
                        System.out.println("Account was not created, an error occured");
                    }
                }
            });
        }
    }

}
