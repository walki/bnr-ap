package com.blooot.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by rjw on 11/15/2014.
 */
public class CheatActivity extends Activity
{
    public static final String EXTRA_ANSWER_IS_TRUE = "com.blooot.android.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN = "com.blooot.android.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswer;


    private void setAnswerShownResult(boolean isAnswerShown)
    {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        //set the default answer of false;
        setAnswerShownResult(false);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);


        mAnswerTextView = (TextView)findViewById(R.id.answerTestView);

        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAnswerIsTrue)
                {
                    mAnswerTextView.setText(R.string.true_button);
                }
                else
                {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }
}
