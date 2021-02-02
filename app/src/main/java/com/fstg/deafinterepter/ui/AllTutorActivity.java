package com.fstg.deafinterepter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fstg.deafinterepter.R;
import com.fstg.deafinterepter.adapter.MyAdapter;
import com.fstg.deafinterepter.dao.TutorDao;
import com.fstg.deafinterepter.entities.Tutor;

import java.util.ArrayList;
import java.util.List;

public class AllTutorActivity extends AppCompatActivity {

    ProgressBar progressBarAllTutor;
    TextView text_hand_translate_in;
    ListView list_view_tutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tutor);
        setupView();
        progressBarAllTutor.setVisibility(View.INVISIBLE);
        setupView();
        text_hand_translate_in.setText("Deaf interpreter🖐");
        progressBarAllTutor.setVisibility(View.VISIBLE);
        TutorDao.findAllTutor(tutorList -> {
            Log.i("TAG ALL TUTOR", tutorList.toString());
            list_view_tutor.setAdapter(new MyAdapter(tutorList, getApplicationContext()));
            progressBarAllTutor.setVisibility(View.GONE);
        });

    }

    private void setupView() {
        progressBarAllTutor = findViewById(R.id.progressBarAllTutor);
        text_hand_translate_in = findViewById(R.id.text_hand_translate_in);
        list_view_tutor = findViewById(R.id.list_view_tutor);
    }

    public void prevuis_Aactivity(View view) {
        finish();
    }

    public void gotoHome(View view) {

    }
}