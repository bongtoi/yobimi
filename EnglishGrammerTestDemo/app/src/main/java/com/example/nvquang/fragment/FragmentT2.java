package com.example.nvquang.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nvquang.adapter.MyFragmentPagerAdapter;
import com.example.nvquang.adapter.NonSwipeableViewPager;
import com.example.nvquang.englishgrammertestdemo.R;
import com.example.nvquang.model.Question;
import com.example.nvquang.network.TaskQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by NVQuang on 07/07/2017.
 */

public class FragmentT2 extends FragmentBase {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.vpQuestion)
    NonSwipeableViewPager vpQuestion;
    @InjectView(R.id.btn_previous)
    Button btnPrevious;
    @InjectView(R.id.btn_next)
    Button btnNext;
    @InjectView(R.id.btn_question_title)
    Button btnQuestionTitle;

    MyFragmentPagerAdapter myFragmentPagerAdapter;
    List<Question> questionsList = new ArrayList<>();
    List<Fragment> fragmentsList = new ArrayList<>();
    int [] answers;
    boolean flagFinishTest = true;
    int indexNow;
    int indexTest;
    ProgressDialog dialog;

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_t2;
    }

    @Override
    protected void initDataDefault() {
        super.initDataDefault();
        Bundle bundle = getArguments();
        indexTest = bundle.getInt("INDEX");

        // set default question title is 1
        btnQuestionTitle.setText("QUESTION 1");

        getQuestionsList();

        toolbar.setTitle("Test " + (indexTest + 1));
        toolbar.setNavigationIcon(R.drawable.ic_action_back);

        // set action click the back button on Toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onBackPressed();
            }
        });

    }

    public void onSelect(int index) {

        int indexNow = vpQuestion.getCurrentItem();

        if (indexNow >= questionsList.size());
        else {
            answers[indexNow] = index;
            if (indexNow < fragmentsList.size() - 1) {
                vpQuestion.setCurrentItem(indexNow + 1);
                Fragment f = fragmentsList.get(indexNow + 1);
                if (f instanceof FragmentQuestion) {
                    ((FragmentQuestion) f).viewResult();
                }
                btnQuestionTitle.setText("QUESTION " + (indexNow + 2));
            }
            else if (indexNow == (questionsList.size() - 1)) {
                flagFinishTest = false;
                fragmentsList.add(FragmentResultTest.newInstance(getnCorrect(answers, questionsList), getnMistake(answers, questionsList), getnSkipped(answers)));
                myFragmentPagerAdapter.notifyDataSetChanged();
                vpQuestion.setCurrentItem(indexNow + 1);
                btnQuestionTitle.setText("RESULT");
            }
        }

    }

    public static FragmentT2 newInstance(int index){
        FragmentT2 fragmentT2 = new FragmentT2();
        Bundle bundle = new Bundle();
        bundle.putInt("INDEX", index);

        fragmentT2.setArguments(bundle);
        return fragmentT2;
    }

    public void getQuestionsList() {
        TaskQuestion task = new TaskQuestion(getContext());

            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            task.request(new Response.Listener<ArrayList<Question>>() {
                @Override
                public void onResponse(ArrayList<Question> response) {
                    dialog.dismiss();

                    questionsList = response;
                    setQuestionList();
                    initFlagQuestion();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();

                    reLoad();

                }
            });

    }

    public void reLoad() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setMessage("Error network! Are you want to reload?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getQuestionsList();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mainActivity.onBackPressed();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void setQuestionList() {
        fragmentsList = getFragmentsList();
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList);
        vpQuestion.setAdapter(myFragmentPagerAdapter);
        // store state fragment
        vpQuestion.setOffscreenPageLimit(questionsList.size());

    }

    public List<Fragment> getFragmentsList() {
        for(int i = 0; i < questionsList.size(); i++) {
            fragmentsList.add(FragmentQuestion.newInstance(questionsList.get(i)));
        }
        return fragmentsList;
    }

    public void initFlagQuestion() {
        answers = new int[questionsList.size()];
        for (int i = 0; i < questionsList.size(); i++) {
            answers[i] = -1;
        }
    }

    @OnClick({R.id.btn_next, R.id.btn_previous})
    public void OnClick(View v) {
        indexNow = vpQuestion.getCurrentItem();
        switch (v.getId()) {
            case R.id.btn_next:
            {
                if (indexNow >= questionsList.size());
                else {
                    if (indexNow < fragmentsList.size() - 1) {
                        vpQuestion.setCurrentItem(indexNow + 1);
                        btnQuestionTitle.setText("QUESTION " + (indexNow + 2));
                        if (answers[indexNow] == -1 && flagFinishTest == true) {
                            Toast.makeText(getContext(), "Skipped!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (indexNow == (questionsList.size() - 1)) {
//                    Log.d("indexnow = ", indexNow + "");
                        flagFinishTest = false;
                        fragmentsList.add(FragmentResultTest.newInstance(getnCorrect(answers, questionsList), getnMistake(answers, questionsList), getnSkipped(answers)));
                        myFragmentPagerAdapter.notifyDataSetChanged();
                        vpQuestion.setCurrentItem(indexNow + 1);
                        btnQuestionTitle.setText("RESULT");

                    }
                }

                break;
            }
            case R.id.btn_previous:
            {
//                Log.d("indexnow = ", indexNow + "");
                if (fragmentsList.size() > questionsList.size()) {
                    fragmentsList.remove(fragmentsList.size() - 1);
                    myFragmentPagerAdapter.notifyDataSetChanged();
                }
                if (indexNow > 0) {
                    vpQuestion.setCurrentItem(indexNow - 1);
                    btnQuestionTitle.setText("QUESTION " + (indexNow));

                    Fragment f = fragmentsList.get(indexNow - 1);
                    // kiem tra type cu fragment f co phai la FragmentQuestion
                    if (f instanceof FragmentQuestion) {
                        ((FragmentQuestion) f).viewResult();
                    }
                }

                break;
            }

        }
    }

    public int getnCorrect (int [] answers, List<Question> questionsList) {
        int d = 0;
        for (int i = 0; i < answers.length; i++) {
            if (answers[i] != -1 && answers[i] == questionsList.get(i).getRightAnswer()) {
                d++;
            }
        }
        return d;
    }

    public int getnMistake (int [] answers, List<Question> questionsList) {
        int d = 0;
        for (int i = 0; i < answers.length; i++) {
            if (answers[i] != -1 && answers[i] != questionsList.get(i).getRightAnswer()) {
                d++;
            }
        }
        return d;
    }

    public int getnSkipped(int [] answers) {
        int d = 0;
        for (int i = 0; i < answers.length; i++) {
            if (answers[i] == -1) {
                d++;
            }
        }
        return d;
    }

}
