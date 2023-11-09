package com.dev.galagan.historicalgarden;

import static com.dev.galagan.historicalgarden.MainActivity.categoryName;
import static com.dev.galagan.historicalgarden.MainActivity.questionsArray;
import static com.dev.galagan.historicalgarden.MainActivity.questionsTheme;
import static com.dev.galagan.historicalgarden.MainActivity.score;
import static com.dev.galagan.historicalgarden.MainActivity.student_name;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
    private int position_in_questions_array = 0;
    Integer[] btnArray = {R.id.quest_answer,R.id.quest_answer1,R.id.quest_answer2,R.id.quest_answer3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_play);
        init();
    }

    private void init(){
        TextView player_name = findViewById(R.id.player_name);
        player_name.setText(student_name);
        TextView scoreTxt = findViewById(R.id.score_text);
        scoreTxt.setText(Integer.toString(score));
        parseQuestions(questionsTheme);
        setQuestions(position_in_questions_array);

        TextView category_theme = findViewById(R.id.theme_name);
        category_theme.setText(categoryName);

        ImageView imgMenu = findViewById(R.id.menu_btn);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    private void setQuestions(int position){
        //initialised questions variables
        TextView scoreTxt = findViewById(R.id.score_text);
        TextView questions_text = findViewById(R.id.questions_text);
        String quest_text = questionsArray[position].getQuestions_text();
        Answer[] answers_array = questionsArray[position].getQuestions_answer();
        Button answer_btn1 = findViewById(btnArray[0]);
        Button answer_btn2 = findViewById(btnArray[1]);
        Button answer_btn3 = findViewById(btnArray[2]);
        Button answer_btn4 = findViewById(btnArray[3]);

        scoreTxt.setText(Integer.toString(score));
        questions_text.setText(quest_text);
        answer_btn1.setText(answers_array[0].getAnswer_text());
        answer_btn2.setText(answers_array[1].getAnswer_text());
        answer_btn3.setText(answers_array[2].getAnswer_text());
        answer_btn4.setText(answers_array[3].getAnswer_text());

        answer_btn1.setOnClickListener(clickListenerAnswers);
        answer_btn2.setOnClickListener(clickListenerAnswers);
        answer_btn3.setOnClickListener(clickListenerAnswers);
        answer_btn4.setOnClickListener(clickListenerAnswers);

        answer_btn1.setBackgroundColor(Color.GRAY);
        answer_btn2.setBackgroundColor(Color.GRAY);
        answer_btn3.setBackgroundColor(Color.GRAY);
        answer_btn4.setBackgroundColor(Color.GRAY);

        answer_btn1.setTextColor(Color.WHITE);
        answer_btn2.setTextColor(Color.WHITE);
        answer_btn3.setTextColor(Color.WHITE);
        answer_btn4.setTextColor(Color.WHITE);

    }

    private final View.OnClickListener clickListenerAnswers = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Answer[] answers = questionsArray[position_in_questions_array].getQuestions_answer();
            for (int i = 0;i <4;i++){
                if (v.getId()==btnArray[i]){
                    if (answers[i].isIs_true()){
                        score++;
                        findViewById(btnArray[i]).setBackgroundColor(Color.GREEN);
                   }else{
                        findViewById(btnArray[i]).setBackgroundColor(Color.RED);
                   }
                    toNextQuestions();
                }
            }
        }
    };

    private void toNextQuestions(){
        position_in_questions_array++;
        if (position_in_questions_array<questionsArray.length){
            CountDownTimer timer = new CountDownTimer(900,400) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    setQuestions(position_in_questions_array);
                }
            };
            timer.start();
        }else{
            //out to main screen
            toMenu();
        }
    }

    private void toMenu(){
        finish();
    }
}