package com.example.resumeproject1;
//Second screen

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    long MIN_TIME = 5000;
    float MIN_DIST = 1000;
    int REQUEST_CODE = 123;
    private String TAG = getClass().getName();
    private MediaPlayer mediaPlayer;
    Button getLocationbutton;
    Button getWeatherButton;
    String LOCATION_PROVIDER;
    LocationManager locationManager;
    LocationListener locationListener;
    String latitude;
    String longitude;
    String APPID="559340dd86fcb83340111729e6240a0e";
    String WEATHER_URL="https://api.openweathermap.org/data/2.5/weather?";
    TextView textView;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate() method called now:");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent receivedIntent = getIntent();
        Context context = getApplicationContext();
        CharSequence text = receivedIntent.getExtras().getString("username");
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        mediaPlayer = MediaPlayer.create(this, R.raw.start);
        //Code for location
         getLocationbutton= (Button) findViewById(R.id.getLocation);
        getLocationbutton.setOnClickListener(v -> {
            Log.d(TAG, "Getlocation button pressed");
            getLocation();
        });
        //Code for Weather
        getWeatherButton=(Button)findViewById(R.id.getWeather);
        getWeatherButton.setOnClickListener(v->{
            Log.d(TAG,"Getweather button pressed -> ");
            getWeather();
        });
        //Textview Initialization
        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
    }

    public void getWeather(){
        Log.d(TAG,"Get weather method is being called");
        RequestParams params=new RequestParams();
        params.put("lat",latitude);
        params.put("lon",longitude);
        params.put("appid",APPID);
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode,Header[] headers,JSONObject response){
                Log.d(TAG,"HTTP REQUEST SUCCEEDED");
                if(WeatherModel.decode(response)){
                    textView.setText( String.valueOf(WeatherModel.temperature-273.15));
                    textView2.setText(WeatherModel.cityName);
                }else{
                    Log.d(TAG,"Unable to decode json object");
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d(TAG,"HTTP REQUEST FAILED");
            }
        });
    }
    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            Log.d(TAG,"Requesting permissions:");
            return;
        }
        Log.d(TAG,"Location Permissions granted");
        locationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DIST, locationListener);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getLocation();
            }else{
                Log.d(TAG,"User denied location permission");
            }
        }
    }


    public void Onclick(View view) {          //Play song button

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start(); // no need to call prepare(); create() does that for you
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"SavedInstancestate() method called now:");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitude=String.valueOf(location.getLatitude());
                longitude=String.valueOf(location.getLongitude());
                Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude);
                Log.d(TAG,"Current latitude :"+latitude);
                Log.d(TAG,"Current longitude :"+longitude);
            Intent intent=new Intent(Intent.ACTION_VIEW,gmmIntentUri);
            startActivity(intent);
            }
            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Log.d(TAG, "Location provider disabled.");
            }
        };
        Log.e(TAG,"OnResume() method called now:");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"OnStart() method called now:");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG,"OnRestart() method called now:");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"OnPause() method called now:");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"OnStop() method called now:");
    }
}