package com.fstg.deafinterepter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fstg.deafinterepter.R;
import com.fstg.deafinterepter.dao.TutorDao;
import com.fstg.deafinterepter.entities.Tutor;
import com.fstg.deafinterepter.utils.ValidationData;

public class RegisterTutorActivity extends AppCompatActivity {
    TextView text_hand_translate_up;
    EditText first_name;
    EditText last_name;
    EditText password;
    Spinner exptv;
    EditText email_tut;
    EditText mobile_tut;
    RadioButton rdFemal;
    RadioButton rdMale;
    Spinner nation_spinner;
    Button btn_signup_tutor;
    RadioGroup groupSexe;
    RadioButton radioSelcted;
    String sexeSelected;
    ProgressBar progressBarCreateTutor;
    Tutor t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutor);
        setupView();
        progressBarCreateTutor.setVisibility(View.INVISIBLE);
        text_hand_translate_up.setText("Deaf Interpreter🖐");
    }

    private void setupView() {
        text_hand_translate_up = findViewById(R.id.text_hand_translate_in);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        password = findViewById(R.id.password);
        nation_spinner = findViewById(R.id.nation_spinner);
        exptv = findViewById(R.id.exptv);
        btn_signup_tutor = findViewById(R.id.btn_signup_tutor);
        email_tut = findViewById(R.id.email_tut);
        mobile_tut = findViewById(R.id.mobile_tut);
        rdFemal = findViewById(R.id.rdFemal);
        rdMale = findViewById(R.id.rdMale);
        groupSexe = findViewById(R.id.groupSexe);
        progressBarCreateTutor = findViewById(R.id.progressBarCreateTutor);
    }

    public boolean vaidationDonner() {
        if (TextUtils.isEmpty(first_name.getText().toString())) {
            first_name.setError("First Name Required");
            first_name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(last_name.getText().toString())) {
            last_name.setError("Last Name Required");
            last_name.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_tut.getText().toString()).matches()) {
            email_tut.setError("Invalid Email");
            email_tut.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Password  Required");
            password.requestFocus();
            return false;
        }
        if (password.getText().toString().length() < 6) {
            password.setError("Length small ");
            password.requestFocus();
            return false;
        }

        if (!ValidationData.phoneValidation(mobile_tut.getText().toString())) {
            mobile_tut.setError("Invalid Format");
            mobile_tut.requestFocus();
            return false;
        }

        if (!ValidationData.validationSpinnerExpp(exptv.getSelectedItem().toString())) {
            Toast.makeText(this, "Select your experence", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!ValidationData.validationSpinnerNatio(nation_spinner.getSelectedItem().toString())) {
            Toast.makeText(this, "Select your country", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void registerTutor(View view) {
        int selectedId = groupSexe.getCheckedRadioButtonId();
        radioSelcted = findViewById(selectedId);
        sexeSelected = (String) radioSelcted.getText();

        if (vaidationDonner()) {
            Thread thread_getTutor = new Thread(() -> {
                TutorDao.getTutorByEmail(email_tut.getText().toString(), tutor -> {
                    t = tutor;
                });
            });
            try {
                Thread.sleep(500);
                thread_getTutor.start();
                thread_getTutor.join();
                if (t == null) {
                    progressBarCreateTutor.setVisibility(View.VISIBLE);
                    Tutor tutor = new Tutor(first_name.getText().toString(), last_name.getText().toString(), exptv.getSelectedItem().toString(),
                            password.getText().toString(), sexeSelected, nation_spinner.getSelectedItem().toString(), mobile_tut.getText().toString(), email_tut.getText().toString());
                    try {
                        TutorDao.insertUser(tutor);
                        progressBarCreateTutor.setVisibility(View.GONE);
                        Toast.makeText(this, "Tutor Save Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterTutorActivity.this, EditTutorActivity.class);
                        intent.putExtra("tutor", tutor);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        Log.i("TAG", " " + e.getLocalizedMessage());
                    }
                } else {
                    Toast.makeText(this, "Tutor Already Exist Please Log in", Toast.LENGTH_SHORT).show();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    public void prevuis_Aactivity(View view) {
        finish();
    }
}