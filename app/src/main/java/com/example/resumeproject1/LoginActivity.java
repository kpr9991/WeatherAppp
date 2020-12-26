package com.example.resumeproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void OnClick(View view){
        EditText editText=findViewById(R.id.username);
        EditText editText1=findViewById(R.id.password);
        String username=editText.getText().toString();
        String password=editText1.getText().toString();
        Intent newIntent=new Intent(this,MainActivity.class);
        newIntent.putExtra("username",username);
        newIntent.putExtra("password",password);
        startActivity(newIntent);
    }
}