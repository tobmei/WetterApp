package com.example.wetterapp;

import android.os.AsyncTask;


public class RequestWetter extends AsyncTask<String, String, Wetter> {

    MainActivityListener mainActivityListener;

    public RequestWetter(MainActivityListener mainActivityListener){
        this.mainActivityListener = mainActivityListener;
    }

    @Override
    protected Wetter doInBackground(String... strings) {
        Wetter wetter;
        String city = strings[0];
        String unit = strings[1];
        String lang = strings[2];
        String mode = strings[3];

        String wetterString = Utility.requestWeatherfromServer(city, unit, lang, mode);

        if(mode == "xml")
            wetter = Utility.xmlToWetter(wetterString);
        else
            wetter = Utility.jsonToWetter(wetterString);

        return wetter;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Wetter wetter) {
        mainActivityListener.showWetter(wetter);
    }
}
