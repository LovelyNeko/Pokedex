package com.example.pokedex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    public boolean permcheck;
    static final int REQUEST_CODE_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] mPermission = {Manifest.permission.INTERNET};

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            permcheck = true;
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, mPermission, REQUEST_CODE_PERMISSION);
        }

    }

    public void Regional(View view){
        Intent intent  = new Intent(this, RegionalList.class);
        startActivity(intent);
    }

    public void National(View view){
        Intent intent  = new Intent(this, NationalDex.class);
        startActivity(intent);
    }

    public void Mega(View view){
        Intent intent  = new Intent(this, MegaDex.class);
        startActivity(intent);
    }


}