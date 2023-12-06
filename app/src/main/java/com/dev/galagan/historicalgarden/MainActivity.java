package com.dev.galagan.historicalgarden;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class MainActivity extends Activity {
    public static int score = 0;
    private static Integer[] categoryImg = {};

    public static Player[] players;
    private int[] imgArray = {R.id.q_btn,R.id.q_btn2,R.id.q_btn3,R.id.q_btn4};
    private int[] resArray = {R.raw.date,R.raw.date,R.raw.date,R.raw.date};
    private String[] catNameArray = {"Дати","Персоналії","Категорія 3","Категорія 4"};
    public static Questions[] questionsArray;
    public static ImgQuestions[] imgQuestionsArray;
    public static Integer questionsTheme = R.raw.date;
    public static String categoryName = "";
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
        init();
    }

    private void init(){
        jsonParsePlayer();
        TextView player_name = findViewById(R.id.player_name);
        ImageButton menu = findViewById(R.id.menu_btn);
        player_name.setText(student_name);
        TextView scoreTxt = findViewById(R.id.score_text);
        scoreTxt.setText(Integer.toString(score));
        setClickListener();
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog menu = new Dialog(MainActivity.this);
                menu.requestWindowFeature(Window.FEATURE_NO_TITLE);
                menu.setCancelable(true);
                menu.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                menu.setContentView(R.layout.menu_layout);

                menu.findViewById(R.id.returnBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menu.hide();
                    }
                });
                menu.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO make settings screen
                    }
                });
                Button exit = menu.findViewById(R.id.exit);
                exit.setText("Вихід");
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                menu.show();
            }
        });

    }
    private void setClickListener(){
        ImageView qBtn1 = findViewById(R.id.q_btn);
        ImageView qBtn2 = findViewById(R.id.q_btn2);
        ImageView qBtn3 = findViewById(R.id.q_btn3);
        ImageView qBtn4 = findViewById(R.id.q_btn4);

        qBtn1.setOnClickListener(clickListenerQuestions);
        qBtn2.setOnClickListener(clickListenerQuestions);
        qBtn3.setOnClickListener(clickListenerQuestions);
        qBtn4.setOnClickListener(clickListenerQuestions);
    }

    private final View.OnClickListener clickListenerQuestions = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i <imgArray.length;i++){
                if (v.getId()==imgArray[i]){
                    questionsTheme = resArray[i];
                    categoryName = catNameArray[i];
                }
            }
            Intent intent = new Intent(v.getContext(), PlayActivity.class);
            startActivity(intent);
        }
    };

    private void jsonParsePlayer() {
        String fileName = "player.json";
        File file = new File(getFilesDir(),fileName);
        if (!file.exists()){
            System.out.println("players from default");
            String file_name = "";
            InputStream inputStream = getResources().openRawResource(R.raw.player_score);
            try {
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                String jsonString = new String(buffer, "UTF-8");
                JsonElement jsonElement = JsonParser.parseString(jsonString);
                if (jsonElement.isJsonObject()) {
                    System.out.println("try load data");
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has("players") && jsonObject.get("players").isJsonArray()) {
                        JsonArray jsonArray = jsonObject.getAsJsonArray("players");
                        Gson gson = new Gson();
                        System.out.println("load data continue");
                        players = gson.fromJson(jsonArray, Player[].class);
                        System.out.println("loaded data: " + players);
                    }
                }
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else{
            try {
                System.out.println("load data from local files");
                FileReader reader = new FileReader(file);
                Gson gson = new Gson();
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(reader);
                players = gson.fromJson(jsonElement, Player[].class);
                System.out.println("loaded data: " + players);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}