package com.dev.galagan.historicalgarden;

public class Questions {
    private String questions_text = "";
    private Answer[] answer_var;

    public Questions(String questions_text, Answer[] answer_var) {
        this.questions_text = questions_text;
        this.answer_var = answer_var;
    }

    public String getQuestions_text() {
        return questions_text;
    }

    public void setQuestions_text(String questions_text) {
        this.questions_text = questions_text;
    }

    public Answer[] getQuestions_answer() {
        return answer_var;
    }

    public void setQuestions_answer(Answer[] questions_answer) {
        this.answer_var = questions_answer;
    }
}
