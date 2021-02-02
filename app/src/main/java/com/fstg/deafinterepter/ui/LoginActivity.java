package com.fstg.deafinterepter.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fstg.deafinterepter.R;
import com.fstg.deafinterepter.utils.ValidationData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {


    EditText email_user_f;
    EditText password;
    TextView text_hand_translate_in;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupView();
        text_hand_translate_in.setText("Deaf interepter🖐");
        progressBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();

    }

    private void setupView() {
        email_user_f = findViewById(R.id.email_user_f);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        text_hand_translate_in = findViewById(R.id.text_hand_translate_in);
    }

    public void loginUser(View view) {
        String email = email_user_f.getText().toString();
        String password_user = password.getText().toString();
        if (!ValidationData.validationEmail(email_user_f.getText().toString())) {
            email_user_f.setError("Email Invalid");
            email_user_f.requestFocus();
            return;
        }
        if (!ValidationData.fieldValidation(password.getText().toString())) {
            password.setError("password Invalid");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password_user).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Failed Sign in Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void forgotPassword(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
        startActivity(intent);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void prevuis_Aactivity(View view) {
        finish();
    }
}