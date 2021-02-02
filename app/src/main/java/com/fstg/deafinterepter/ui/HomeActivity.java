package com.fstg.deafinterepter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.fstg.deafinterepter.R;
import com.fstg.deafinterepter.entities.User;

public class HomeActivity extends AppCompatActivity {

    Button btn_learning;
    Button btn_translate;

    ///24/01/2021 ===> 06/02/2021
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupView();
//        User student = (User) getIntent().getSerializableExtra("user");
        btn_learning.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AllTutorActivity.class);
//            intent.putExtra("user", student);
            startActivity(intent);
        });
        btn_translate.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TranslateActivity.class);
            startActivity(intent);
        });


    }

    private void setupView() {
        btn_learning = findViewById(R.id.btn_learning);
        btn_translate = findViewById(R.id.btn_translate);
    }
}