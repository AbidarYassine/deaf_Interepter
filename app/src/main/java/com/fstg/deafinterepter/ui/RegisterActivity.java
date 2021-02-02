package com.fstg.deafinterepter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fstg.deafinterepter.R;
import com.fstg.deafinterepter.entities.User;
import com.fstg.deafinterepter.utils.ValidationData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    EditText user_name;
    EditText email_user;
    EditText password;
    Button sign_ub_btn;
    TextView text_hand_translate_in;
    ProgressBar prBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        setupView();
        text_hand_translate_in.setText("Deaf interepter🖐");
        prBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        sign_ub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    prBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email_user.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    User user = new User(user_name.getText().toString(), email_user.getText().toString(), password.getText().toString());
                                    db.collection("users")
                                            .add(user)
                                            .addOnCompleteListener(documentReferenceTask -> {
                                                if (documentReferenceTask.isSuccessful()) {
                                                    prBar.setVisibility(View.INVISIBLE);
                                                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                    Toast.makeText(RegisterActivity.this, "User Login successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    prBar.setVisibility(View.GONE);
                                                    Log.i("TAG ", "Failed to register try again");
                                                }

                                            });
                                } else {
                                    prBar.setVisibility(View.GONE);
                                    Log.i("TAG ", "Failed to register try again");
                                }
                            });
                }
            }
        });
    }

    private boolean validateData() {
        if (!ValidationData.fieldValidation(user_name.getText().toString())) {
            user_name.setError("UserName Invalid");
            user_name.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_user.getText().toString()).matches()) {
            email_user.setError("Email Invalid");
            email_user.requestFocus();
            return false;
        }
        if (!ValidationData.fieldValidation(password.getText().toString())) {
            password.setError("password Invalid");
            password.requestFocus();
            return false;
        }
        return true;
    }

    public void prevuis_Aactivity(View view) {
        finish();
    }

    private void setupView() {
        user_name = findViewById(R.id.user_name);
        email_user = findViewById(R.id.email_user);
        password = findViewById(R.id.password);
        prBar = findViewById(R.id.prBar);
        sign_ub_btn = findViewById(R.id.sign_ub_btn);
        text_hand_translate_in = findViewById(R.id.text_hand_translate_in);

    }
}