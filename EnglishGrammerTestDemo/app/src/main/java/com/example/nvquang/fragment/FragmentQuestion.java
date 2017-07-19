package com.example.nvquang.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.nvquang.englishgrammertestdemo.R;
import com.example.nvquang.model.Question;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by NVQuang on 06/07/2017.
 */

public class FragmentQuestion extends FragmentBase {
    @InjectView(R.id.txtContent)
    TextView txtContent;
    @InjectView(R.id.txt_answer_a)
    TextView txtAnswerA;
    @InjectView(R.id.txt_answer_b)
    TextView txtAnswerB;
    @InjectView(R.id.txt_answer_c)
    TextView txtAnswerC;
    @InjectView(R.id.txt_answer_d)
    TextView txtAnswerD;

    @InjectView(R.id.txt_check_answer_a)
    TextView txtCheckAnswerA;
    @InjectView(R.id.txt_check_answer_b)
    TextView txtCheckAnswerB;
    @InjectView(R.id.txt_check_answer_c)
    TextView txtCheckAnswerC;
    @InjectView(R.id.txt_check_answer_d)
    TextView txtCheckAnswerD;

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_question_2;
    }

    @Override
    protected void initDataDefault() {
        super.initDataDefault();

        Bundle bundle = getArguments();
        int qId = bundle.getInt("QID");
        String txtQuestion = bundle.getString("TXTQUESTION");
        ArrayList<String> answer = bundle.getStringArrayList("ANSWERS");
        int rightAnswer = bundle.getInt("RIGHTANSWER");

        txtContent.setText(txtQuestion);
        txtAnswerA.setText(answer.get(0));
        txtAnswerB.setText(answer.get(1));
        txtAnswerC.setText(answer.get(2));
        txtAnswerD.setText(answer.get(3));
    }

    public void viewResult() {
        boolean flagFT = ((FragmentT2)getParentFragment()).flagFinishTest;
//        Log.d("indexnow on", flagFT + "");

        if (flagFT == false) {
            int id = ((FragmentT2)getParentFragment()).vpQuestion.getCurrentItem();
//            Log.d("indexnow", id + "");
            int sl = ((FragmentT2)getParentFragment()).answers[id];
            int ra = ((FragmentT2)getParentFragment()).questionsList.get(id).getRightAnswer();

            setResult(sl, ra);
        }

    }

    public static FragmentQuestion newInstance(Question question) {
        FragmentQuestion fragmentQuestion = new FragmentQuestion();

        Bundle bundle = new Bundle();
        bundle.putInt("QID", question.getQid());
        bundle.putString("TXTQUESTION", question.getTxtQuestion());
        bundle.putStringArrayList("ANSWERS", question.getAnswers());
        bundle.putInt("RIGHTANSWER", question.getRightAnswer());
        fragmentQuestion.setArguments(bundle);

        return fragmentQuestion;
    }

    @OnClick({R.id.txt_answer_a, R.id.txt_answer_b, R.id.txt_answer_c, R.id.txt_answer_d})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.txt_answer_a:
            {
                ((FragmentT2)getParentFragment()).onSelect(0);
                txtAnswerA.setBackgroundColor(getResources().getColor(R.color.bgQuestionContent));
                txtAnswerB.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerC.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerD.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                Log.d("answer", "0");
//                Toast.makeText(getContext(), "Answer A", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.txt_answer_b:
            {
                ((FragmentT2)getParentFragment()).onSelect(1);
                txtAnswerB.setBackgroundColor(getResources().getColor(R.color.bgQuestionContent));
                txtAnswerA.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerC.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerD.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                Log.d("answer", "1");
//                Toast.makeText(getContext(), "Answer B", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.txt_answer_c:
            {
                ((FragmentT2)getParentFragment()).onSelect(2);
                txtAnswerC.setBackgroundColor(getResources().getColor(R.color.bgQuestionContent));
                txtAnswerA.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerB.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerD.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                Log.d("answer", "2");
//                Toast.makeText(getContext(), "Answer C", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.txt_answer_d:
            {
                ((FragmentT2)getParentFragment()).onSelect(3);
                txtAnswerD.setBackgroundColor(getResources().getColor(R.color.bgQuestionContent));
                txtAnswerA.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerB.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerC.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                Log.d("answer", "3");
//                Toast.makeText(getContext(), "Answer D", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    public void setResult(int selected, int rightAnswer) {
        setRightAnswer(rightAnswer);
        setSeclected(selected, rightAnswer);
        setEnableAnswer();
    }

    private void setEnableAnswer() {
        txtAnswerA.setEnabled(false);
        txtAnswerB.setEnabled(false);
        txtAnswerC.setEnabled(false);
        txtAnswerD.setEnabled(false);
    }

    public void setSeclected(int seclected, int rightAnswer) {
        switch (seclected) {
            case -1: {
                switch (rightAnswer) {
                    case 0:
                    {
                        txtCheckAnswerA.setBackgroundResource(R.drawable.ic_answer_skip_correct);
                        break;
                    }
                    case 1:
                    {
                        txtCheckAnswerB.setBackgroundResource(R.drawable.ic_answer_skip_correct);
                        break;
                    }
                    case 2:
                    {
                        txtCheckAnswerC.setBackgroundResource(R.drawable.ic_answer_skip_correct);
                        break;
                    }
                    case 3:
                    {
                        txtCheckAnswerD.setBackgroundResource(R.drawable.ic_answer_skip_correct);
                        break;
                    }
                }
                break;
            }
            case 0:
            {
                if (seclected == rightAnswer);
                else {
                    txtCheckAnswerA.setBackground(getResources().getDrawable(R.drawable.ic_answer_mistake));
                }
                break;
            }
            case 1:
            {
                if (seclected == rightAnswer);
                else {
                    txtCheckAnswerB.setBackground(getResources().getDrawable(R.drawable.ic_answer_mistake));
                }
                break;
            }
            case 2:
            {
                if (seclected == rightAnswer);
                else {
                    txtCheckAnswerC.setBackground(getResources().getDrawable(R.drawable.ic_answer_mistake));
                }
                break;
            }
            case 3:
            {
                if (seclected == rightAnswer);
                else {
                    txtCheckAnswerD.setBackground(getResources().getDrawable(R.drawable.ic_answer_mistake));
                }
                break;
            }
        }
    }

    public void setRightAnswer(int rightAnswer) {
        switch (rightAnswer) {
            case 0:
            {
                txtAnswerA.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerB.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerC.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerD.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
//                txtAnswerA.setBackgroundResource(R.drawable.ic_answer_correct);
                txtCheckAnswerA.setBackground(getResources().getDrawable(R.drawable.ic_answer_correct));
                break;
            }
            case 1:
            {
                txtAnswerA.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerB.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerC.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerD.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
//                txtAnswerB.setBackgroundResource(R.drawable.ic_answer_correct);
                txtCheckAnswerB.setBackground(getResources().getDrawable(R.drawable.ic_answer_correct));
                break;
            }
            case 2:
            {
                txtAnswerA.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerB.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerC.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerD.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
//                txtAnswerC.setBackgroundResource(R.drawable.ic_answer_correct);
                txtCheckAnswerC.setBackground(getResources().getDrawable(R.drawable.ic_answer_correct));
                break;
            }
            case 3:
            {
                txtAnswerA.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerB.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerC.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
                txtAnswerD.setBackgroundColor(getResources().getColor(R.color.colorDefault1));
//                txtAnswerD.setBackgroundResource(R.drawable.ic_answer_correct);
                txtCheckAnswerD.setBackground(getResources().getDrawable(R.drawable.ic_answer_correct));
                break;
            }
        }
    }

}
