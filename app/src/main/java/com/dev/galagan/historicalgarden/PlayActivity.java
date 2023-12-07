package com.dev.galagan.historicalgarden;

import static com.dev.galagan.historicalgarden.MainActivity.categoryName;
import static com.dev.galagan.historicalgarden.MainActivity.imgQuestionsArray;
import static com.dev.galagan.historicalgarden.MainActivity.questionsArray;
import static com.dev.galagan.historicalgarden.MainActivity.questionsTheme;
import static com.dev.galagan.historicalgarden.MainActivity.score;
import static com.dev.galagan.historicalgarden.MainActivity.student_name;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Random;

public class PlayActivity extends Activity {
    private int position_in_questions_array = 0;
    private int questCount = 20;
    private TextView scoreTxt;
    private int questMode = 1;//mode for use script to load questions: 1 - text questions, 2 questions with image
    Integer[] btnArray = {R.id.quest_answer,R.id.quest_answer1,R.id.quest_answer2,R.id.quest_answer3};
    private static Questions[] randomQuest = new Questions[20];
    private static ImgQuestions[] imgRandomQuest = new ImgQuestions[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_play);
        init();
    }

    private void init(){
        TextView player_name = findViewById(R.id.player_name);
        player_name.setText(student_name);
        scoreTxt = findViewById(R.id.score_text);
        ImageButton menu = findViewById(R.id.menu_btn);
        TextView scoreTxt = findViewById(R.id.score_text);
        scoreTxt.setText(Integer.toString(score));

        System.out.println(categoryName + " category name");
        switch (categoryName){
            case "Персоналії":
                TextView hideText = findViewById(R.id.questions_text);
                hideText.setVisibility(View.INVISIBLE);
                questMode = 2;
                questCount = 18;
                break;
            case "Дати":
                ImageView hideImg = findViewById(R.id.personalityImg);
                hideImg.setVisibility(View.INVISIBLE);
                questMode = 1;
                questCount = 15;
                break;
        }
        if (questMode == 1) {
            parseQuestions(questionsTheme);
            getRandomQuestArr();
            setQuestions(position_in_questions_array);
        }else if (questMode == 2){
            parseQuestionsImg(questionsTheme);
            ImgGetRandomQuestArr();
            setQuestions(position_in_questions_array);

        }
        System.out.println(questCount + " " + questMode + " quest param");

        TextView category_theme = findViewById(R.id.theme_name);
        category_theme.setText(categoryName);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog menu = new Dialog(PlayActivity.this);
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
                menu.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                menu.show();
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
    private void parseQuestionsImg(Integer default_file_name){
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
                    imgQuestionsArray = gson.fromJson((JsonElement) jsonArray, (Type) ImgQuestions[].class);
                    System.out.println("loaded data: "+imgQuestionsArray);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void getRandomQuestArr(){
        for (int i = 0; i<questCount;i++){
            Random random = new Random();
            randomQuest[i] = questionsArray[random.nextInt(questionsArray.length-1)];
        }
    }
    private void ImgGetRandomQuestArr(){
        for (int i = 0; i<questCount;i++){
            Random random = new Random();
            imgRandomQuest[i] = imgQuestionsArray[random.nextInt(imgQuestionsArray.length-1)];
        }
    }
    private void setQuestImg(){
        ImageView questImg = findViewById(R.id.personalityImg);
        System.out.println("person ind = " + imgRandomQuest[position_in_questions_array].getPerson_ind());
        Integer img = imgRandomQuest[position_in_questions_array].getResource();
        questImg.setImageDrawable(getResources().getDrawable(img));
    }

    private void setQuestions(int position){
        //initialised questions variables
        Answer[] answers_array = new Answer[4];
        TextView questions_text = findViewById(R.id.questions_text);

        scoreTxt.setText(Integer.toString(score));

        if (questMode == 1){
            String quest_text = randomQuest[position].getQuestions_text();
            answers_array = randomQuest[position].getQuestions_answer();
            questions_text.setText(quest_text);
        }else if (questMode == 2){
            answers_array = imgRandomQuest[position].getAnswer_var();
            setQuestImg();
        }

        Button answer_btn1 = findViewById(btnArray[0]);
        Button answer_btn2 = findViewById(btnArray[1]);
        Button answer_btn3 = findViewById(btnArray[2]);
        Button answer_btn4 = findViewById(btnArray[3]);
        Button[] ansBtn = {answer_btn1,answer_btn2,answer_btn3,answer_btn4};
        String[] setQ = new String[4];
        Boolean hasElement = false;
        Boolean endWhile = false;
        int i = 0;
        while (endWhile == false){
            System.out.println("infinity while");
            int randPos = (int) (Math.random()*(i-4)+4);
            String answer = answers_array[randPos].getAnswer_text();
            for (int j = 0;j<setQ.length;j++){
                if (setQ[j]==answer){
                    hasElement = true;
                }
            }
            if (hasElement==false){
                ansBtn[i].setText(answer);
                i++;
            }
            if (i>=ansBtn.length){
                endWhile = true;
            }
        }
        answer_btn1.setText(answers_array[(int) (Math.random()*(0-4)+4)].getAnswer_text());
        answer_btn2.setText(answers_array[(int) (Math.random()*(1-4)+4)].getAnswer_text());
        answer_btn3.setText(answers_array[2].getAnswer_text());
        answer_btn4.setText(answers_array[3].getAnswer_text());

        answer_btn1.setOnClickListener(clickListenerAnswers);
        answer_btn2.setOnClickListener(clickListenerAnswers);
        answer_btn3.setOnClickListener(clickListenerAnswers);
        answer_btn4.setOnClickListener(clickListenerAnswers);
        setActionBtn();

    }

    private final View.OnClickListener clickListenerAnswers = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Answer[] answers = randomQuest[position_in_questions_array].getQuestions_answer();
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
    private void setActionBtn(){
        Answer[] answers = new Answer[4];
        if (questMode == 1){
            answers = randomQuest[position_in_questions_array].getQuestions_answer();
        }else if(questMode == 2){
            answers = imgRandomQuest[position_in_questions_array].getAnswer_var();
        }
        for (int j =0; j<4;j++){
            if (answers[j].isIs_true()){
                findViewById(btnArray[j]).setBackground(getResources().getDrawable(R.drawable.btn_true));
            }else{
                findViewById(btnArray[j]).setBackground(getResources().getDrawable(R.drawable.btn_wrong));
            }
        }
    }

    private void toNextQuestions(){
        position_in_questions_array++;
        if (position_in_questions_array<randomQuest.length){
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

    private void close(){
        finish();
    }

    private void toMenu(){
        final Dialog final_dialog = new Dialog(PlayActivity.this);
        final_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final_dialog.setCancelable(false);
        final_dialog.setContentView(R.layout.dialog_after_play);
        final_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView timerText = final_dialog.findViewById(R.id.timerTextV);
        int time = 10;
        timerText.setText(Integer.toString(time));
        Button closeBtn = final_dialog.findViewById(R.id.closeBtn);



        CountDownTimer timer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(Long.toString(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                close();
            }
        };
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        final_dialog.show();
        timer.start();
    }
}