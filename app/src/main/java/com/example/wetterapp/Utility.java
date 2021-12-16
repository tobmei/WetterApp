package com.example.wetterapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Utility {

    private static final String TAG = Utility.class.getSimpleName();

    //Aufzurufende URL mit Platzhaltern für die Parameter
    private static final String URL = "https://api.openweathermap.org/data/2.5/weather?q={0}&units={1}&lang={2}&mode={3}&appid=XXX";
    private static final String ICON = "https://openweathermap.org/img/w/{0}.png";

    private static String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};

    public static String getDirection(int deg) {
        return directions[(int)Math.floor((deg % 360) / 45)];
    }

    public static String getShortUnit(String unit){
        String deg = "";
        switch (unit){
            case "metric":
                deg = " C°";
                break;
            case "imperial":
                deg = " F°";
                break;
            case "kelvin":
                deg = " K°";
                break;
        }
        return deg;
    }

    public static String requestWeatherfromServer(String city, String unit, String lang, String mode){
        String anfrageURL = MessageFormat.format(URL, city, unit, lang, mode);
        Log.i(TAG,anfrageURL);

        String wetterString = "";
        HttpsURLConnection httpsURLConnection = null;

        try{
            //Aufbau der Verbindung zum Server
            java.net.URL url = new URL(anfrageURL);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setConnectTimeout(9000); //9 Sekunden
            httpsURLConnection.setReadTimeout(9000);

            //Prüfen ob ResponseCode OK
            if(httpsURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                //Anfordern der Daten und Umwandeln in String
                InputStream is = new BufferedInputStream(httpsURLConnection.getInputStream());
                wetterString = convertStreamToString(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wetterString;
    }

    public static Bitmap requestIcon(String id){
        String anfrageURL = MessageFormat.format(ICON, id);
        Log.i(TAG,anfrageURL);

        HttpsURLConnection httpsURLConnection = null;
        Bitmap bitmap = null;

        try{
            //Aufbau der Verbindung zum Server
            java.net.URL url = new URL(anfrageURL);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setConnectTimeout(9000); //9 Sekunden
            httpsURLConnection.setReadTimeout(9000);

            //Prüfen ob ResponseCode OK
            if(httpsURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                //Anfordern der Daten und Umwandeln in String
                InputStream is = new BufferedInputStream(httpsURLConnection.getInputStream());
                bitmap = BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static String convertStreamToString(InputStream is) {
        //Stringbuilder baut unseren String zusammen
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(is))){
            while((line = bf.readLine()) != null)
                stringBuilder.append(line).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();

    }

    public static Wetter jsonToWetter(String jsonString){
        Wetter wetter = null;
        try{
            JSONObject rootObject = new JSONObject(jsonString);
            JSONObject desc = rootObject.getJSONArray("weather").getJSONObject(0);
            JSONObject main = rootObject.getJSONObject("main");
            JSONObject wind = rootObject.getJSONObject("wind");

            String icon = desc.getString("icon");
            String description = desc.getString("main");
            String descriptionSub = desc.getString("description");
            String temp = main.getString("temp");
            String minTemp = main.getString("temp_min");
            String maxTemp = main.getString("temp_max");
            String humidity = main.getString("humidity");
            String pressure = main.getString("pressure");
            String windSpeed = wind.getString("speed");
            String windDeg = wind.getString("deg");

            String city = rootObject.getString("name");
            Bitmap bitmap = requestIcon(icon);

            wetter = new Wetter(temp,minTemp,maxTemp,humidity,pressure,windSpeed,windDeg,description,descriptionSub,city,bitmap,"");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wetter;
    }

    public static Wetter xmlToWetter(String xmlString){
        Wetter wetter = null;

        Document document;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xmlString));

            document = documentBuilder.parse(inputSource);
            Node node = document.getElementsByTagName("current").item(0);

            NodeList list = node.getChildNodes();
            Node tempNode = list.item(1);
            String temp = tempNode.getAttributes().item(0).getNodeValue();
            String minTemp = tempNode.getAttributes().item(1).getNodeValue();
            String maxTemp = tempNode.getAttributes().item(2).getNodeValue();

            Node humNode = list.item(3);
            String hum = humNode.getAttributes().item(0).getNodeValue();

            Node presNode = list.item(4);
            String pres = presNode.getAttributes().item(0).getNodeValue();

            Node windNode = list.item(5);
            NodeList windChildren  = windNode.getChildNodes();
            Node speedNode = windChildren.item(0);
            String windSpeed = speedNode.getAttributes().item(0).getNodeValue();
            String windDesc = speedNode.getAttributes().item(2).getNodeValue();
            Node dirNode = windChildren.item(2);
            String windDir = dirNode.getAttributes().item(0).getNodeValue();

            Node descNode = list.item(9);
            String desc = descNode.getAttributes().item(1).getNodeValue();
            String icon = descNode.getAttributes().item(2).getNodeValue();

            Node cityNode = list.item(0);
            String city = cityNode.getAttributes().item(1).getNodeValue();

            Bitmap bitmap = requestIcon(icon);

            wetter = new Wetter(temp,minTemp,maxTemp,hum,pres,windSpeed,windDir,"", desc,city,bitmap, windDesc);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wetter;
    }

}
