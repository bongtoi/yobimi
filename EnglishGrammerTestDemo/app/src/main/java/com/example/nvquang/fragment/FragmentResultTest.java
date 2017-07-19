package com.example.nvquang.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nvquang.englishgrammertestdemo.R;

import butterknife.InjectView;

/**
 * Created by NVQuang on 08/07/2017.
 */

public class FragmentResultTest extends FragmentBase {
    @InjectView(R.id.txt_correct)
    TextView txtCorrect;
    @InjectView(R.id.txt_mistakes)
    TextView txtMistake;
    @InjectView(R.id.txt_skipped)
    TextView txtSkipped;
    @InjectView(R.id.txt_highscore)
    TextView txtHighScore;
    @InjectView(R.id.progress_bar_result_test)
    ProgressBar progressBarResultTest;
    @InjectView(R.id.txt_percent_result)
    TextView txtPerCentResult;

    Handler mHandler = new Handler();
    int mProgressStatus = 0;

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_result_test;
    }

    @Override
    protected void initDataDefault() {
        super.initDataDefault();

        Bundle bundle = getArguments();
        int nCorrect = bundle.getInt("NCORRECT");
        int nMistake = bundle.getInt("NMISTAKE");
        int nSkipped = bundle.getInt("NSKIPPED");

        setCircleResult((100 * nCorrect) / (nCorrect + nMistake + nSkipped));
        setTestResult(nCorrect, nMistake, nSkipped);
        setHighScore(nCorrect);

    }

    public static FragmentResultTest newInstance(int a, int b, int c) {
        FragmentResultTest fragmentResultTest = new FragmentResultTest();

        Bundle bundle = new Bundle();
        bundle.putInt("NCORRECT", a);
        bundle.putInt("NMISTAKE", b);
        bundle.putInt("NSKIPPED", c);

        fragmentResultTest.setArguments(bundle);
        return fragmentResultTest;
    }

    public void setCircleResult(final int percent) {
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < percent) {
                    mProgressStatus++;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            progressBarResultTest.setProgress(mProgressStatus);
                            txtPerCentResult.setText(""+mProgressStatus+"%");
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void setTestResult(int nCorrect, int nMistake, int nSkipped) {
        txtCorrect.setText(nCorrect + "");
        txtMistake.setText(nMistake + "");
        txtSkipped.setText(nSkipped + "");
    }

    public void setHighScore(int nCorrect) {
        int indexTest = ((FragmentT2)getParentFragment()).indexTest;

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("HIGHSCORE", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("TEST" + indexTest, 0);
        if (nCorrect > highScore) {
            highScore = nCorrect;
            SharedPreferences.Editor editor = getContext().getSharedPreferences("HIGHSCORE", Context.MODE_PRIVATE).edit();
            editor.putInt("TEST" + indexTest, highScore);
            editor.apply();
        }
        txtHighScore.setText(highScore + "");
    }

}
