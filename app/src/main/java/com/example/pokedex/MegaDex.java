package com.example.pokedex;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.databinding.ActivityMegaDexBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class MegaDex extends AppCompatActivity {

    private ActivityMegaDexBinding binding;
    LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new APIConnection().execute();

        binding = ActivityMegaDexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        ll = (LinearLayout) findViewById(R.id.ll_content);
        TextView textView = new TextView(this);

        ll.addView(textView);
        textView.setText("Hey there friends! Sadly this pokédex is currently unavailable," +
                " as of right now we do not have a proper database regarding mega and mega related evolutions specifically." +
                " If someone has suggestions on how to implement this part of the application you can e-mail us or send me a message on Github!" +
                "Though please keep it serious inquiries only, thank you for your time!");
    }

//    class APIConnection extends AsyncTask<Void, Void, JSONObject> {
//
//        //Manipulate the limit to change query size, use the offset as base nr,
//        // so if you use offset 151 you start at 2nd gen, the following gen numbers are based on the national pokedex
//        //151 = kanto region, the first gen pokemons
//        //251 = 2nd gen
//        //386 = 3rd gen
//        //493 = 4th gen
//        //649 = 5th gen
//        //721 = 6th gen
//        //809 = 7th gen
//        //898 = 8th gen
//        public String gen = "";
//        public String JSON_URL = "https://pokeapi.co/api/v2/pokemon" + gen;
//        String charset = "UTF-8";
//        HttpURLConnection conn;
//        StringBuilder result;
//        URL urlObj;
//
//        @Override
//        protected JSONObject doInBackground(Void... args) {
//            JSONObject retObj = null;
//
//            //internet
//            try{
//                urlObj = new URL(JSON_URL);
//                conn = (HttpURLConnection) urlObj.openConnection();
//                conn.setDoOutput(false);
//                conn.setRequestMethod("GET");
//                conn.setRequestProperty("Accept-Charset", charset);
//                conn.setConnectTimeout(15000);
//                conn.connect();
//
//                InputStream in = new BufferedInputStream(conn.getInputStream());
//                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                result = new StringBuilder();
//                String line;
//                while((line = reader.readLine()) != null){
//                    result.append(line);
//                }
//
//                retObj = new JSONObject(result.toString());
//
//            } catch (IOException e){
//                e.printStackTrace();
//            }  catch(JSONException e){
//                e.printStackTrace();
//            }
//            return retObj;
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject json){
//            if(json != null){
//                try{
//
//                    Log.i("URL", JSON_URL);
//                    Log.i("json", json.toString());
//
//
////                    Returned JSONArray
//                    JSONArray result = json.getJSONArray("results");
//                    Log.i("JSONArray", result.getString(0));
//
//
//                    String[] text = new String[result.length()];
//
//
//                    for (int i = 0; i < result.length(); i++) {
//                        Log.i("Loop", String.valueOf(i));
//                        Log.i("Loop", result.getString(i));
//
//                        JSONObject obj = result.getJSONObject(i);
//                        text[i] = obj.getString("name");
//
//                        Log.i("JSONObject", text[i]);
//
//                        final Button pokeButton = new Button(MegaDex.this);
//                        ll.addView(pokeButton);
//                        pokeButton.setText(1 + i + "   " + text[i]);
//                    }
//
//                    ll.invalidate();
//
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
}