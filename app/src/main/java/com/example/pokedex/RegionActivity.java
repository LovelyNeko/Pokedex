package com.example.pokedex;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.databinding.ActivityRegionBinding;
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

public class RegionActivity extends AppCompatActivity {

    private ActivityRegionBinding binding;
    LinearLayout ll;
    public Bundle act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;


        ll = (LinearLayout) findViewById(R.id.ll_content);

        //      Bundle that gets the "extra" you set with the onClick function on the pokemon buttons
        act = getIntent().getExtras();
        if(act != null ){
            String value = act.getString("activity");
        }
        Log.i("extra", String.valueOf(act));

        String title = act.getString("activity");
        toolBarLayout.setTitle(title);

        new RegionActivity.APIConnection().execute();

    }
    class APIConnection extends AsyncTask<Void, Void, JSONObject> {

        //Manipulate the limit to change query size, use the offset as base nr,
        // so if you use offset 151 you start at 2nd gen, the following gen numbers are based on the national pokedex
        //151 = kanto region, the first gen pokemons
        //251 = 2nd gen ?offset=251&limit=136
        //386 = 3rd gen
        //493 = 4th gen
        //649 = 5th gen
        //721 = 6th gen
        //809 = 7th gen
        //898 = 8th gen


        public String generation = act.getString("activity");
        public String gen;
        public String JSON_URL = "https://pokeapi.co/api/v2/pokemon";
        String charset = "UTF-8";
        HttpURLConnection conn;
        public StringBuilder result;
        URL urlObj;


        @Override
        protected JSONObject doInBackground(Void... args) {
            JSONObject retObj = null;

            Log.i("generation", generation);


//          This switch case gets the selected region from String generation,
//          it checks what the value is and for each value it has a case.
//          Based on the value of generation, it sets the content of String gen.
//          String gen is the dynamic half of the api url, string gen sets what content the database connectiong gets
            switch (generation){
//              1st gen
                case  "Kanto":
                    gen = "?limit=152";
                    break;

//              2nd gen
                case "Johto":
                    gen = "?offset=152&limit=101";
                    break;

//              3rd gen
                case "Hoenn":
                    gen = "?offset=251&limit=136";
                    break;

//              4th gen
                case "Sinnoh":
                    gen = "?offset=386&limit=108";
                    break;

//              5th gen
                case "Unova":
                    gen = "?offset=493&limit=157";
                    break;

//              6th gen
                case "Kalos":
                    gen = "?offset=649&limit=73";
                    break;

//              7th gen
                case "Alola":
                    gen = "?offset=721&limit=89";
                    break;

//              8th gen
                case "Galar":
                    gen = "?offset=809&limit=309";
                    break;
            }

            Log.i("switch", gen);

            //Connection to the base API (Getting the list of pokemon)
            try{
                urlObj = new URL(JSON_URL + gen);
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

//                  Returned JSONArray
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

//                      Creating a button w context + adding it to the pre-defined linear layout
                        final Button pokeButton = new Button(RegionActivity.this);
                        ll.addView(pokeButton);

//                      setting the text for the buttons
                        pokeButton.setText(1 + i + "   " + text[i]);

                        url[i] = obj.getString("url");
                        Log.i("URL", url[i]);
                        pokeButton.setId(i);

                        String finalurl = url[i];
                        int finalI = i;
                        pokeButton.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(RegionActivity.this, PokemonActivity.class);
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