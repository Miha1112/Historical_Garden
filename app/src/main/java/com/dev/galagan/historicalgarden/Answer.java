package com.dev.galagan.historicalgarden;

public class Answer {
    private String answer_text = "";
    private boolean is_true = false;

    public Answer(String answer_text, boolean is_true_answer) {
        this.answer_text = answer_text;
        this.is_true = is_true_answer;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public boolean isIs_true() {
        return is_true;
    }

    public void setIs_true(boolean is_true_answer) {
        this.is_true = is_true_answer;
    }
}
