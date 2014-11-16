package com.blooot.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
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
    private static final String KEY_ANSWERSHOWN = "answerShown";


    private boolean mAnswerIsTrue;
    private boolean mAnswerShown;

    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private TextView mAPILevelTextView;


    private void setAnswerShownResult(boolean isAnswerShown)
    {
        mAnswerShown = isAnswerShown;

        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerShown = false;
        if (savedInstanceState!=null)
        {
            mAnswerShown = savedInstanceState.getBoolean(KEY_ANSWERSHOWN, false);
        }
        setAnswerShownResult(mAnswerShown);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);


        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

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

        mAPILevelTextView = (TextView)findViewById(R.id.apiLevelTextView);
        mAPILevelTextView.setText(String.format("API Level %d", Build.VERSION.SDK_INT ));
        mAPILevelTextView.setPadding(25,25,25,25);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ANSWERSHOWN, mAnswerShown);
    }
}
