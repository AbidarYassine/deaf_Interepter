package com.fstg.deafinterepter.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.fstg.deafinterepter.R;
import com.fstg.deafinterepter.db.ConnexionDb;
import com.fstg.deafinterepter.entities.Tutor;
import com.fstg.deafinterepter.utils.ValidationData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class EditTutorActivity extends AppCompatActivity {

    EditText first_name;
    EditText last_name;
    Spinner exptv;
    EditText password;
    EditText mobile_tut;
    RadioButton rdFemal;
    EditText email_tutor;
    RadioButton rdMale;
    Spinner nation_spinner;
    Button btn_signup_tutor;
    RadioGroup groupSexe;
    RadioButton radioSelcted;
    Tutor tutor;
    String sexeSelected;
    ProgressBar progressBarEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tutor);
        setupView();
        progressBarEdit.setVisibility(View.GONE);
        tutor = (Tutor) getIntent().getSerializableExtra("tutor");
        first_name.setText(tutor.getFirstName());
        last_name.setText(tutor.getLastName());
        password.setText(tutor.getPassword());
        email_tutor.setText(tutor.getEmail());
        if (tutor.getPhone().isEmpty()) {
            mobile_tut.setText("");
        } else {
            mobile_tut.setText(tutor.getPhone());
        }
        if (tutor.getSexe().equalsIgnoreCase("Female")) {
            rdFemal.setChecked(true);
        } else if (tutor.getSexe().equalsIgnoreCase("Male")) {
            rdMale.setChecked(true);
        }
        nation_spinner.setSelection(getIndex(nation_spinner, tutor.getNationality()));
        exptv.setSelection(getIndex(exptv, tutor.getExp()));

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
        if (!Patterns.EMAIL_ADDRESS.matcher(email_tutor.getText().toString()).matches()) {
            email_tutor.setError("Invalid Email");
            email_tutor.requestFocus();
            return false;
        }
        if (!ValidationData.fieldValidation(password.getText().toString())) {
            password.setError("password Invalid");
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

    private void setupView() {
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        nation_spinner = findViewById(R.id.nation_spinner);
        exptv = findViewById(R.id.exptv);
        btn_signup_tutor = findViewById(R.id.btn_signup_tutor);
        password = findViewById(R.id.password);
        mobile_tut = findViewById(R.id.mobile_tut);
        rdFemal = findViewById(R.id.rdFemal);
        rdMale = findViewById(R.id.rdMale);
        email_tutor = findViewById(R.id.email_tutor);
        progressBarEdit = findViewById(R.id.progressBarEdit);
        groupSexe = findViewById(R.id.groupSexe);
    }

    public void prevuis_Aactivity(View view) {
        finish();
    }

    public void editTutor(View view) {
        int selectedId = groupSexe.getCheckedRadioButtonId();
        radioSelcted = findViewById(selectedId);
        sexeSelected = (String) radioSelcted.getText();
        Log.i("Tag sex", sexeSelected);
        if (vaidationDonner()) {
            progressBarEdit.setVisibility(View.VISIBLE);
            CollectionReference complaintsRef = ConnexionDb.db().collection("tuttors");
            complaintsRef.whereEqualTo("email", tutor.getEmail())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<Object, String> t = new HashMap<>();
                                    t.put("exp", exptv.getSelectedItem().toString());
                                    t.put("firstName", first_name.getText().toString());
                                    t.put("sexe", sexeSelected);
                                    t.put("lastName", last_name.getText().toString());
                                    t.put("nationality", nation_spinner.getSelectedItem().toString());
                                    t.put("phone", mobile_tut.getText().toString());
                                    t.put("password", password.getText().toString());
                                    t.put("email", email_tutor.getText().toString());
                                    complaintsRef.document(document.getId()).set(t, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressBarEdit.setVisibility(View.GONE);
                                                Toast.makeText(getApplicationContext(), "Tutor Updated successfully", Toast.LENGTH_SHORT).show();
                                            } else {
                                                progressBarEdit.setVisibility(View.GONE);
                                                Toast.makeText(getApplicationContext(), "Update Failed ,Try Again !", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    break;
                                }
                            }
                        }
                    });
        }
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }
}