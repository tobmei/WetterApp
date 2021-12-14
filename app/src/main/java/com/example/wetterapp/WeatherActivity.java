package com.example.wetterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {

    WeatherActivityListener weatherActivityListener;
    TextView txtvTemp, txtvMinTemp, txtvMaxTemp, txtvDesc, txtvDescSub, txtvWindSpeed, txtvWindDeg, txtvPressure, txtvHumidity, txtvHeader, txtvWindDesc;
    ImageView ivIcon, ivWind, ivTemp, ivHumid, ivPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        txtvTemp = findViewById(R.id.txtvTemp);
        txtvMinTemp = findViewById(R.id.txtvMinTemp);
        txtvMaxTemp = findViewById(R.id.txtvMaxTemp);
        //txtvDesc = findViewById(R.id.txtvDesc);
        txtvDescSub = findViewById(R.id.txtvDescSub);
        txtvWindSpeed = findViewById(R.id.txtvWindSpeed);
        txtvWindDeg = findViewById(R.id.txtvWindDeg);
        txtvPressure = findViewById(R.id.txtvPressure);
        txtvHumidity = findViewById(R.id.txtvHumidity);
        txtvHeader = findViewById(R.id.txtvHeader);
        txtvWindDesc = findViewById(R.id.txtvWindDesc);

        ivIcon = findViewById(R.id.ivIcon);
        ivTemp = findViewById(R.id.ivTemp);
        ivHumid = findViewById(R.id.ivHumid);
        ivPressure = findViewById(R.id.ivPressure);
        ivWind = findViewById(R.id.ivWind);

        weatherActivityListener = new WeatherActivityListener(this);

    }

    public void showWetter(Wetter wetter, String unit) {
        String deg = Utility.getShortUnit(unit);

        txtvHeader.setText(wetter.getCity());
        txtvTemp.setText(wetter.getTemp() + deg);
        txtvMinTemp.setText("Min: " + wetter.getMinTemp() + deg);
        txtvMaxTemp.setText("Max: " + wetter.getTemp() + deg);
        //txtvDesc.setText(wetter.getDesc());
        txtvDescSub.setText(wetter.getDescSub());
        txtvWindSpeed.setText(wetter.getWindSpeed() + " m/s");
        txtvWindDeg.setText(Utility.getDirection(Integer.parseInt(wetter.getWindDir())));
        txtvWindDesc.setText(wetter.getwindDesc());
        txtvPressure.setText(wetter.getPressure() + " hPa");
        txtvHumidity.setText(wetter.getHumidity() + " %");
        ivIcon.setImageBitmap(wetter.getBitmap());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return weatherActivityListener.onOptionsItemSelected(item);
    }
}