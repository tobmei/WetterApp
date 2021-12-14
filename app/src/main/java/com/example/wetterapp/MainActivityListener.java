package com.example.wetterapp;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

public class MainActivityListener implements View.OnClickListener {

    RequestWetter requestWetter;
    MainActivity mainActivity;
    String unit = "";

    public MainActivityListener(MainActivity mainActivity) {
       this.mainActivity = mainActivity;
    }

    public void showWetter(Wetter wetter){
        Intent intent = new Intent(mainActivity, WeatherActivity.class);
        intent.putExtra("wetter", (Parcelable) wetter);
        intent.putExtra("unit", unit);
        mainActivity.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        String lang = "";
        String mode = "";

        if(mainActivity.rbCelsius.isChecked())
            unit = "metric";
        else if(mainActivity.rbFahrenheit.isChecked())
            unit = "imperial";
        else if(mainActivity.rbKelvin.isChecked())
            unit = "kelvin";

        if(mainActivity.rbGer.isChecked())
            lang = "de";
        else if(mainActivity.rbEng.isChecked())
            lang = "en";

        if(mainActivity.cbXML.isChecked())
            mode = "xml";

        if(view.getId() == R.id.button){
            requestWetter = new RequestWetter(this);
            requestWetter.execute(mainActivity.etCity.getText().toString(), unit, lang, mode);
        }
    }
}
