package com.dev.galagan.historicalgarden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    public static int score = 0;

    public static Player[] players;
    public static String student_name = "Учень";
    public static int[] last_score = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
    }

    private void init(){
        jsonParse();

        TextView player_name = findViewById(R.id.player_name);
        player_name.setText(student_name);
        TextView scoreTxt = findViewById(R.id.score_text);
        scoreTxt.setText(Integer.toString(score));
    }




    private void jsonParse() {
        String fileName = "player.json";
        File file = new File(getFilesDir(),fileName);
        if (!file.exists()){
            //System.out.println("cards bg from default");
            String file_name = "";
            InputStream inputStream = getResources().openRawResource(R.raw.player_score);
            try {
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                String jsonString = new String(buffer, "UTF-8");
                JsonElement jsonElement = JsonParser.parseString(jsonString);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has("cards_bg") && jsonObject.get("cards_bg").isJsonArray()) {
                        JsonArray jsonArray = jsonObject.getAsJsonArray("cards_bg");
                        Gson gson = new Gson();
                        players = gson.fromJson(jsonArray, Player[].class);
                    }
                }
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else{
            try {
                //System.out.println("load cards bg from local files");
                FileReader reader = new FileReader(file);
                Gson gson = new Gson();
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(reader);
                System.out.println("is obj json : "+ jsonElement.isJsonArray());
                players = gson.fromJson(jsonElement, Player[].class);
                //System.out.println("load successful");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        }
    }
}