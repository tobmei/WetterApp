package com.example.wetterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    EditText etCity;
    Button btnSubmit;
    RadioButton rbCelsius, rbKelvin, rbFahrenheit, rbGer, rbEng;
    RadioGroup rbG;
    CheckBox cbXML;
    ImageView ivGer, ivBrit;
    MainActivityListener mainActivityListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityListener = new MainActivityListener(this);

        btnSubmit = findViewById(R.id.button);
        etCity = findViewById(R.id.etCity);
        rbCelsius = findViewById(R.id.rbCelsius);
        rbKelvin = findViewById(R.id.rbKelvin);
        rbFahrenheit = findViewById(R.id.rbbFahrenheit);
        rbG = findViewById(R.id.rbgUnits);
        ivBrit = findViewById(R.id.ivBrit);
        rbGer = findViewById(R.id.rbGer);
        rbEng = findViewById(R.id.rbEng);

        cbXML = findViewById(R.id.cbXML);

        btnSubmit.setOnClickListener(mainActivityListener);

    }
}