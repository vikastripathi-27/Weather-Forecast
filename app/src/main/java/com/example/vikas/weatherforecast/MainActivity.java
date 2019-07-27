package com.example.vikas.weatherforecast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public String url = "https://samples.openweathermap.org/data/2.5/weather?id=6619347&appid=3b65df6b49198678b14166569d430196";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadUrlData();
    }

    public void loadUrlData() {
        final ProgressDialog pd =new ProgressDialog(this);
        pd.setMessage("Fetching Data");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                //Toast.makeText(MainActivity.this, "welcome to weather app", Toast.LENGTH_SHORT).show();
                TextView small_description2,decscription2,temp,hum,maxi_temp,mini_temp,win_speed,dt;
                //small_description2 = findViewById(R.id.small_desc);
                decscription2 = findViewById(R.id.desc);
                temp = findViewById(R.id.temprature);
                hum = findViewById(R.id.humidity);
                maxi_temp = findViewById(R.id.maximum_temp);
                mini_temp = findViewById(R.id.minimum_temp);
                win_speed = findViewById(R.id.windspeed);
                String date_time = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                dt= findViewById(R.id.date);
                dt.setText("Date : "+date_time);
                try {
                    //if array is present
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("weather");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            String small_description = jsonArray.getJSONObject(i).getString("main");
                            String description = jsonArray.getJSONObject(i).getString("description");
                            decscription2.setText("Conditions : "+description);
                           // small_description2.setText("Partial description = "+small_description);
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "erro1", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //if array is not present
                    JSONObject jsonObject = new JSONObject(response).getJSONObject("main");
                        try {
                            String temprature = jsonObject.getString("temp");
                            String humidity = jsonObject.getString("humidity");
                            String max_temp = jsonObject.getString("temp_max");
                            String min_temp = jsonObject.getString("temp_min");
                            Double max = Double.parseDouble(max_temp);
                            Double min = Double.parseDouble(min_temp);
                            Double tempr = Double.parseDouble(temprature);
                            tempr = tempr - 273.15;
                            max = max - 272.15;
                            min = min - 276.15;
                            max_temp = Double.toString(max);
                            min_temp = Double.toString(min);
                            temprature = Double.toString(tempr);
                            temp.setText("Current Temprature \n"+temprature+" °C");
                            hum.setText("Humidity : "+humidity+"%");
                            maxi_temp.setText("Maximum "+max_temp+" °C");
                            mini_temp.setText("Minimum "+min_temp+" °C");
                            //Toast.makeText(MainActivity.this, "got the value", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "erro2", Toast.LENGTH_SHORT).show();
                        }
                    JSONObject jsonObject1 = new JSONObject(response).getJSONObject("wind");
                    try {
                        String speed = jsonObject1.getString("speed");
                        win_speed.setText("Wind speed : "+speed+" kmph");
                        //Toast.makeText(MainActivity.this, "got the last value", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e) {
                        Toast.makeText(MainActivity.this, "erro3", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "erro4", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Cannot connect to server",Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
