package com.fstg.deafinterepter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fstg.deafinterepter.R;
import com.fstg.deafinterepter.entities.Tutor;

public class InfoTutorActivity extends AppCompatActivity {
    TextView text_hand_translate_in;
    TextView tutor_name, email_tutor, exp_tv, phone_tutor, gender_tutor, nationality_tutor, libe_email, libe_exp, libe_gender, libe_phone, libe_natio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_tutor);
        setupView();
        text_hand_translate_in.setText("Deaf interepter🖐");
        Tutor tutor = (Tutor) getIntent().getSerializableExtra("tutor");
        libe_email.setText("Email :");
        libe_exp.setText("Experience :");
        libe_gender.setText("Gender :");
        libe_phone.setText("Phone :");
        libe_natio.setText("Nationality :");
        tutor_name.setText(tutor.getFirstName() + " " + tutor.getLastName());
        exp_tv.setText(tutor.getExp());
        email_tutor.setText(tutor.getEmail());
        if (tutor.getPhone().isEmpty()) {
            phone_tutor.setText("Not Specified");
        } else {
            phone_tutor.setText(tutor.getPhone());
        }

        gender_tutor.setText(tutor.getSexe());
        nationality_tutor.setText(tutor.getNationality());

    }

    private void setupView() {
        text_hand_translate_in = findViewById(R.id.text_hand_translate_in);
        tutor_name = findViewById(R.id.tutor_name);
        email_tutor = findViewById(R.id.email_tutor);
        exp_tv = findViewById(R.id.exp_tv);
        phone_tutor = findViewById(R.id.phone_tutor);
        gender_tutor = findViewById(R.id.gender_tutor);
        nationality_tutor = findViewById(R.id.nationality_tutor);
        libe_email = findViewById(R.id.libe_email);
        libe_exp = findViewById(R.id.libe_exp);
        libe_gender = findViewById(R.id.libe_gender);
        libe_natio = findViewById(R.id.libe_natio);
        libe_phone = findViewById(R.id.libe_phone);
    }

    public void prevuis_Aactivity(View view) {
        finish();
    }

    public void gotoHome(View view) {
        Intent intent = new Intent(InfoTutorActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}