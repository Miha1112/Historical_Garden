package com.dev.galagan.historicalgarden;

import static com.dev.galagan.historicalgarden.MainActivity.questionsArray;
import static com.dev.galagan.historicalgarden.MainActivity.questionsTheme;
import static com.dev.galagan.historicalgarden.MainActivity.score;
import static com.dev.galagan.historicalgarden.MainActivity.student_name;

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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_play);
    }

    private void init(){
        TextView player_name = findViewById(R.id.player_name);
        player_name.setText(student_name);
        TextView scoreTxt = findViewById(R.id.score_text);
        scoreTxt.setText(Integer.toString(score));
        parseQuestions(questionsTheme);
    }

    private void parseQuestions(Integer default_file_name){
        InputStream inputStream = getResources().openRawResource(default_file_name);
        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            String jsonString = new String(buffer, "UTF-8");
            JsonElement jsonElement = JsonParser.parseString(jsonString);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject.has("questions") && jsonObject.get("questions").isJsonArray()) {
                    JsonArray jsonArray = jsonObject.getAsJsonArray("questions");
                    Gson gson = new Gson();
                    questionsArray = gson.fromJson(jsonArray, Questions[].class);
                    System.out.println("loaded data: "+questionsArray);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}