package com.example.nvquang.model;

import java.util.ArrayList;

/**
 * Created by NVQuang on 06/07/2017.
 */

public class Question {
    private int qid;
    private String imgQuestion;
    private String txtQuestion;
    private String audioQuestion;
    private ArrayList<String> answers;
    private int cat;
    private int rightAnswer;

    public Question(int qid) {
        this.qid = qid;
    }

    public Question(int qid, String imgQuestion, String txtQuestion, String audioQuestion, ArrayList<String> answers, int cat, int rightAnswer) {
        this.qid = qid;
        this.imgQuestion = imgQuestion;
        this.txtQuestion = txtQuestion;
        this.audioQuestion = audioQuestion;
        this.answers = answers;
        this.cat = cat;
        this.rightAnswer = rightAnswer;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getImgQuestion() {
        return imgQuestion;
    }

    public void setImgQuestion(String imgQuestion) {
        this.imgQuestion = imgQuestion;
    }

    public String getTxtQuestion() {
        return txtQuestion;
    }

    public void setTxtQuestion(String txtQuestion) {
        this.txtQuestion = txtQuestion;
    }

    public String getAudioQuestion() {
        return audioQuestion;
    }

    public void setAudioQuestion(String audioQuestion) {
        this.audioQuestion = audioQuestion;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
