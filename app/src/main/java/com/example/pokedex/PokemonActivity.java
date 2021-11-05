package com.example.pokedex;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PokemonActivity extends AppCompatActivity {

    public Bundle extras;
    volatile public TextView metadata;
    volatile public ImageView spriteview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        spriteview = findViewById(R.id.spriteview);
        spriteview.setVisibility(View.VISIBLE);

        metadata = findViewById(R.id.metadata);
        metadata.setVisibility(View.VISIBLE);
        metadata.setTextColor(Color.WHITE);


//      Bundle that gets the "extra" you set with the onClick function on the pokemon buttons
        extras = getIntent().getExtras();
        if(extras != null ){
            String value = extras.getString("url");
        }
        Log.i("extra", String.valueOf(extras));

        new metadata().execute();
    }

//  Class that establishes api connection and gets the selected pokemon's data, then sets it to the textview
    class metadata extends AsyncTask<Void, Void, JSONObject>{


        volatile public JSONObject pmd;
        volatile public URL pmdurl, spriteurl;
        volatile public String pmdJSON = extras.getString("url");
        volatile public String charset = "UTF-8";
        volatile HttpURLConnection pmdconn;
        volatile public StringBuilder pmdresult, textcontent;
        volatile public Bitmap bmp;

        @Override
        public JSONObject doInBackground(Void... args) {

            pmd = null;

            Log.i("url", pmdJSON);

            try{
                pmdurl = new URL(pmdJSON);
                pmdconn = (HttpURLConnection) pmdurl.openConnection();
                pmdconn.setDoOutput(false);
                pmdconn.setRequestMethod("GET");
                pmdconn.setRequestProperty("Accept-Charset", charset);
                pmdconn.setConnectTimeout(15000);
                pmdconn.connect();

                InputStream in = new BufferedInputStream(pmdconn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                pmdresult = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    pmdresult.append(line);
                }

                pmd = new JSONObject(pmdresult.toString());



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("pmd", String.valueOf(pmd));

            return pmd;
        }

        @Override
        public void onPostExecute(JSONObject json){

            textcontent = new StringBuilder();
            try {
//              Getting the pokemon name
                String name = pmd.getString("name");
//              Checking what the name object holds
                Log.i("name", name);
//              Adding the name to the stringbuilder
                textcontent.append("Name: " + name + ". \n");

//              Getting the pokemon height
                Integer height = pmd.getInt("height");
                Double calcheight = height * 10.0;

//              Adding the height to the Stringbuilder
                textcontent.append("Height: " + calcheight + " cm. \n");

//              Getting the pokemon weight
                Integer weight = pmd.getInt("weight");

//              Formatting the weight
                Double calcweight = weight * 0.1;

//              Adding the weight to the stringbuilder
                textcontent.append("This pokémon's weight averages around: " + calcweight + " kg. \n");

                JSONArray types = pmd.getJSONArray("types");

                textcontent.append("This pokemon has the following types: \n");

                for(int i = 0; i < types.length(); i++){
//              gets the slots
                JSONObject indtype = types.getJSONObject(i);

//              gets the type objects in the slots
                JSONObject acttype = indtype.getJSONObject("type");

//              gets the actual name of the type
                String type = acttype.getString("name");
                Log.i("pokeOBJ.type", type);


                int counter = i + 1;
                if(types.length() > 1){
                textcontent.append("Type" + counter + " : ");
                textcontent.append(type + ". \n");
                } else {
                       textcontent.append("Type" + counter + " : ");
                       textcontent.append(type + ". "); }
                }

                JSONObject spriteobject = pmd.getJSONObject("sprites");
                Log.i("sprites", String.valueOf(spriteobject));

                String sprite = spriteobject.getString("front_default");

                Log.i("sprite", String.valueOf(sprite));


                spriteurl = new URL(sprite);


            } catch (JSONException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run(){
                    try {
                        bmp = BitmapFactory.decodeStream(spriteurl.openConnection().getInputStream());
                        spriteview.setImageBitmap(bmp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Log.i("textcontent", String.valueOf(textcontent));
            metadata.setText(textcontent);
        }
    }
}