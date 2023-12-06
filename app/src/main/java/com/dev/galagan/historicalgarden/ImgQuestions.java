package com.dev.galagan.historicalgarden;

public class ImgQuestions {
    private Answer[] answer_var;
    private String person_ind;
    private int ind_img;
    private Integer resource;

    public ImgQuestions(Answer[] answer_var, String person_ind) {
        this.answer_var = answer_var;
        this.person_ind = person_ind;
    }
    public Integer getResource() {
        getResourceInd();
        return resource;
    }

    public Answer[] getAnswer_var() {
        return answer_var;
    }

    public void setAnswer_var(Answer[] answer_var) {
        this.answer_var = answer_var;
    }

    public String getPerson_ind() {
        return person_ind;
    }

    public void setPerson_ind(String person_ind) {
        this.person_ind = person_ind;
    }

    public int getInd_img() {
        return ind_img;
    }

    public void setInd_img(int ind_img) {
        this.ind_img = ind_img;
    }

    private void getResourceInd(){
        ind_img = Integer.parseInt(person_ind);
        switch (ind_img){
            case 1:
                resource = R.drawable.ind_1;
                break;
            case 2:
                resource = R.drawable.ind_2;
                break;
            case 3:
                resource = R.drawable.ind_3;
                break;
            case 4:
                resource = R.drawable.ind_4;
                break;
            case 5:
                resource = R.drawable.ind_5;
                break;
            case 6:
                resource = R.drawable.ind_6;
                break;
            case 7:
                resource = R.drawable.ind_7;
                break;
            case 8:
                resource = R.drawable.ind_8;
                break;
            case 9:
                resource = R.drawable.ind_9;
                break;
            case 10:
                resource = R.drawable.ind_10;
                break;
            case 11:
                resource = R.drawable.ind_11;
                break;
            case 12:
                resource = R.drawable.ind_12;
                break;
            case 13:
                resource = R.drawable.ind_13;
                break;
            case 14:
                resource = R.drawable.ind_14;
                break;
            case 15:
                resource = R.drawable.ind_15;
                break;
            case 16:
                resource = R.drawable.ind_16;
                break;
            case 17:
                resource = R.drawable.ind_17;
                break;
            case 18:
                resource = R.drawable.ind_18;
                break;
            case 19:
                resource = R.drawable.ind_19;
                break;
            case 20:
                resource = R.drawable.ind_20;
                break;
            case 21:
                resource = R.drawable.ind_21;
                break;
            case 22:
                resource = R.drawable.ind_22;
                break;
            case 23:
                resource = R.drawable.ind_23;
                break;
            case 24:
                resource = R.drawable.ind_24;
                break;
            case 25:
                resource = R.drawable.ind_25;
                break;
            case 26:
                resource = R.drawable.ind_26;
                break;
        }
    }
}
