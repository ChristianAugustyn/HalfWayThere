package com.example.christian_augustyn_aandroid_developer_halfwaythere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {

    EditText name, email, pass, passv;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.newName);
        email = findViewById(R.id.newEmail);
        pass = findViewById(R.id.password);
        passv = findViewById(R.id.passwordV);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void createAccount(View view) {
        String new_email = email.getText().toString();
        String new_pass = pass.getText().toString();
        String new_passv = passv.getText().toString();

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
                        startActivity(new Intent(Signup.this, MainActivity.class));
                    } else {
                        System.out.println("Account was not created, an error occured");
                    }
                }
            });
        }
    }

}
