package com.example.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pokedex.databinding.ActivityRegionalListBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class RegionalList extends AppCompatActivity {

    private ArrayList<String>mregionarray;
    private ActivityRegionalListBinding binding;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegionalListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;

        String title = "Region list";
        toolBarLayout.setTitle(title);


        mregionarray = new ArrayList<>(Arrays.asList("Kanto, Johto, Hoenn, Sinnoh, Unova, Kalos, Alola, Galar".split(",")));
        String[] mRegionString = {
                "Kanto",
                "Johto",
                "Hoenn",
                "Sinnoh",
                "Unova",
                "Kalos",
                "Alola",
                "Galar"
        };



        ll = (LinearLayout) findViewById(R.id.ll_content);
        for (int i = 0; i < mRegionString.length; i++) {
            Log.i("Loop", String.valueOf(i));
            Log.i("Loop", mRegionString[i]);

            final Button btn = new Button(this);
            ll.addView(btn);
            btn.setText( 1 + i + " " + mRegionString[i]);
            int finalI = i;
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    String activity =mRegionString[finalI];
                    Log.i("Button", activity);
                    Intent intent = new Intent();
                    intent.setClassName("com.example.pokedex", "com.example.pokedex.RegionActivity");
                    intent.putExtra("activity", activity);
                    Log.i("Button", String.valueOf(intent));
                    startActivity(intent);
                }

            });
        }

        ll.invalidate();

    }
}