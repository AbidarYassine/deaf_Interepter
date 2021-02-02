package com.fstg.deafinterepter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fstg.deafinterepter.R;

public class MainActivity extends AppCompatActivity {
    TextView text_hand_translate;
    Button sign_in_as_user;
    Button sign_is_as_tutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        text_hand_translate.setText("Deaf interepter🖐");
        sign_in_as_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        sign_is_as_tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginTutorActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupView() {
        text_hand_translate = findViewById(R.id.text_hand_translate_in);
        sign_in_as_user = findViewById(R.id.sign_in_as_user);
        sign_is_as_tutor = findViewById(R.id.sign_is_as_tutor);
    }
}