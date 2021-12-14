package com.example.wetterapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Wetter implements Parcelable {

    private String temp, minTemp, maxTemp, humidity, pressure, windSpeed, windDir, desc, descSub, city, windDesc;
    private Bitmap bitmap;


    public Wetter(String temp, String minTemp, String maxTemp, String humidity, String pressure, String windSpeed, String windDir, String desc, String descSub, String city,Bitmap bitmap, String windDesc) {
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDir = windDir;
        this.desc = desc;
        this.descSub = descSub;
        this.city = city;
        this.bitmap = bitmap;
        this.windDesc = windDesc;
    }

    protected Wetter(Parcel in) {
        temp = in.readString();
        minTemp = in.readString();
        maxTemp = in.readString();
        humidity = in.readString();
        pressure = in.readString();
        windSpeed = in.readString();
        windDir = in.readString();
        desc = in.readString();
        descSub = in.readString();
        city = in.readString();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        windDesc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(temp);
        parcel.writeString(minTemp);
        parcel.writeString(maxTemp);
        parcel.writeString(humidity);
        parcel.writeString(pressure);
        parcel.writeString(windSpeed);
        parcel.writeString(windDir);
        parcel.writeString(desc);
        parcel.writeString(descSub);
        parcel.writeString(city);
        parcel.writeParcelable(bitmap, 0);
        parcel.writeString(windDesc);
    }

    public static final Creator<Wetter> CREATOR = new Creator<Wetter>() {
        @Override
        public Wetter createFromParcel(Parcel in) {
            return new Wetter(in);
        }

        @Override
        public Wetter[] newArray(int size) {
            return new Wetter[size];
        }
    };

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getCity() {
        return city;
    }

    public String getTemp() {
        return temp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDir() {
        return windDir;
    }

    public String getDesc() {
        return desc;
    }

    public String getDescSub() {
        return descSub;
    }

    public String getwindDesc(){
        return windDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
