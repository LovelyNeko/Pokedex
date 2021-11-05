package com.example.pokedex;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.databinding.ActivityNationalDexBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NationalDex extends AppCompatActivity {

    private ActivityNationalDexBinding binding;
    LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new APIConnection().execute();

        binding = ActivityNationalDexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        ll = (LinearLayout) findViewById(R.id.ll_content);

    }
    class APIConnection extends AsyncTask<Void, Void, JSONObject> {

        //Manipulate the limit to change query size, use the offset as base nr,
        // so if you use offset 151 you start at 2nd gen, the following gen numbers are based on the national pokedex
        //151 = kanto region, the first gen pokemons
        //251 = 2nd gen
        //386 = 3rd gen
        //493 = 4th gen
        //649 = 5th gen
        //721 = 6th gen
        //809 = 7th gen
        //898 = 8th gen
        public String gen = "?limit=898";
        public String JSON_URL = "https://pokeapi.co/api/v2/pokemon" + gen;
        String charset = "UTF-8";
        HttpURLConnection conn;
        StringBuilder result;
        URL urlObj;

//        Bulbapedia url reference, this is a base url, you can put the name of the pokemon
//        (the name object that you get from result) directly behind the link and it will
//        automatically refer you to the right page
        public String BULBA_URL = "https://bulbapedia.bulbagarden.net/wiki/";

        @Override
        protected JSONObject doInBackground(Void... args) {
            JSONObject retObj = null;

            //internet
            try{
                urlObj = new URL(JSON_URL);
                conn = (HttpURLConnection) urlObj.openConnection();
                conn.setDoOutput(false);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept-Charset", charset);
                conn.setConnectTimeout(15000);
                conn.connect();

                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                result = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    result.append(line);
                }

                retObj = new JSONObject(result.toString());

            } catch (IOException e){
                e.printStackTrace();
            }  catch(JSONException e){
                e.printStackTrace();
            }
            return retObj;
        }

        @Override
        protected void onPostExecute(JSONObject json){
            if(json != null){
                try{

                    Log.i("URL", JSON_URL);
                    Log.i("json", json.toString());


//                    Returned JSONArray
                    JSONArray result = json.getJSONArray("results");
                    Log.i("JSONArray", result.getString(0));


                    String[] text = new String[result.length()];

                    String[] url = new String[result.length()];

                    for (int i = 0; i < result.length(); i++) {
                        Log.i("Loop", String.valueOf(i));
                        Log.i("Loop", result.getString(i));

                        JSONObject obj = result.getJSONObject(i);
                        text[i] = obj.getString("name");

                        Log.i("JSONObject", text[i]);

                        final Button pokeButton = new Button(NationalDex.this);
                        ll.addView(pokeButton);
                        pokeButton.setText(1 + i + "   " + text[i]);

                        url[i] = obj.getString("url");
                        Log.i("URL", url[i]);


                        String finalurl = url[i];
                        pokeButton.setId(i);
                        int finalI = i;
                        pokeButton.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View arg0){
                                Intent i = new Intent(NationalDex.this, PokemonActivity.class);
                                i.putExtra("url", finalurl);
                                startActivity(i);
                            }
                        });
                    }


                    ll.invalidate();

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }

    }
}