package com.example.wetterapp;

import android.content.Intent;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

public class WeatherActivityListener {


    WeatherActivity weatherActivity;
    ActionBar actionBar;
    Wetter wetter;

    public WeatherActivityListener(WeatherActivity weatherActivity) {
        this.weatherActivity = weatherActivity;

        actionBar = weatherActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        wetter = weatherActivity.getIntent().getParcelableExtra("wetter");
        String unit = weatherActivity.getIntent().getExtras().getString("unit");
        if(wetter != null && unit != null){
            weatherActivity.showWetter(wetter, unit);
        }
        else{
            Toast toast = Toast.makeText(weatherActivity,"Diese Stadt exisitert nicht!",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            weatherActivity.finish();
        }

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            weatherActivity.finish();
        return true;
    }
}
