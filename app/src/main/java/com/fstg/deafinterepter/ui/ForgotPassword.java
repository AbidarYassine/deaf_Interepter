package com.fstg.deafinterepter.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fstg.deafinterepter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    TextView text_hand_translate_in;
    EditText email_user_f;
    ProgressBar progressBarResset;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setupView();
        mAuth = FirebaseAuth.getInstance();
        progressBarResset.setVisibility(View.INVISIBLE);
        text_hand_translate_in.setText("Deaf interepter🖐");
    }

    private void setupView() {
        text_hand_translate_in = findViewById(R.id.text_hand_translate_in);
        email_user_f = findViewById(R.id.email_user_f);
        progressBarResset = findViewById(R.id.progressBarResset);
    }

    public void restPassword(View view) {
        String email = email_user_f.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email_user_f.getText().toString()).matches()) {
            email_user_f.setError("Email Invalid");
            email_user_f.requestFocus();
            return;
        }
        progressBarResset.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressBarResset.setVisibility(View.INVISIBLE);
                    Toast.makeText(ForgotPassword.this, "Please Check you email To Reset Your password", Toast.LENGTH_SHORT).show();
                } else {
                    progressBarResset.setVisibility(View.GONE);
                    Toast.makeText(ForgotPassword.this, "Try Again !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void prevuis_Aactivity(View view) {
        finish();
    }
}