package com.fstg.deafinterepter.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fstg.deafinterepter.R;
import com.fstg.deafinterepter.db.ConnexionDb;
import com.fstg.deafinterepter.entities.Tutor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginTutorActivity extends AppCompatActivity {

    ProgressBar progressBar;
    EditText password;
    EditText email_tutor_f;
    TextView text_hand_translate_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tutor);
        setupView();
        text_hand_translate_in.setText("Deaf interepter🖐");
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void setupView() {
        progressBar = findViewById(R.id.progressBar);
        password = findViewById(R.id.password);
        email_tutor_f = findViewById(R.id.email_tutor_f);
        text_hand_translate_in = findViewById(R.id.text_hand_translate_in);
    }

    public void loginTutor(View view) {
        String email = email_tutor_f.getText().toString();
        String password_tutor = password.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_tutor_f.setError("Invalid Email");
            email_tutor_f.requestFocus();
            return;
        }
        if (password_tutor.isEmpty()) {
            password.setError("Password required");
            password.requestFocus();
            return;
        }
        if (password_tutor.length() < 6) {
            password.setError("Invalid Password");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        ConnexionDb.db().collection("tuttors").whereEqualTo("email", email)
                .whereEqualTo("password", password_tutor)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Tutor tutor = new Tutor();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                tutor = document.toObject(Tutor.class);
                                tutor.setTutor_id(document.getId());
                                Log.i("TAG METHODE", tutor.toString());
                                break;
                            }
                        }
                        if (tutor.getEmail() != null) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(LoginTutorActivity.this, EditTutorActivity.class);
                            intent.putExtra("tutor", tutor);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginTutorActivity.this, "Failed Login try Again or create Account", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginTutorActivity.this, "Failed Login try Again", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void createAccountTutor(View view) {
        Intent intent = new Intent(LoginTutorActivity.this, RegisterTutorActivity.class);
        startActivity(intent);
    }

    public void prevuis_Aactivity(View view) {
        finish();
    }
}