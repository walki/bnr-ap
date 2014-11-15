package com.blooot.android.geoquiz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends ActionBarActivity {

    private enum Direction{
        NONE,
        PREV,
        NEXT
    }

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mPrevButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

    private TrueFalse[] mQuestionBank = new TrueFalse[]
            {
                new TrueFalse(R.string.question_oceans, true),
                new TrueFalse(R.string.question_mideast, false),
                new TrueFalse(R.string.question_africa, false),
                new TrueFalse(R.string.question_americas, true),
                new TrueFalse(R.string.question_asia, true)
            };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        // Set the first question
        updateQuestion(Direction.NONE);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clicking the text view now moves to next question
                updateQuestion(Direction.NEXT);
            }
        });


        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);

            }
        });

        mPrevButton = (Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion(Direction.PREV);
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion(Direction.NEXT);
            }
        });




    }


    private void updateQuestion(Direction direction) {
        switch (direction)
        {
            case PREV:
                mCurrentIndex = (mCurrentIndex-1)% mQuestionBank.length;
                // As Java Mod returns the sign of the dividend, lets ensure that we
                // have a positive value here!
                if (mCurrentIndex < 0)
                {
                    mCurrentIndex += mQuestionBank.length;
                }
                break;
            case NEXT:
                mCurrentIndex = (mCurrentIndex+1)% mQuestionBank.length;
                break;
            case NONE:
            default:
                mCurrentIndex = 0;
                break;
        }

        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }


    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int resultId = 0;
        if (userPressedTrue == answerIsTrue)
        {
            resultId = R.string.correct_toast;
        }
        else
        {
            resultId = R.string.incorrect_toast;
        }
        Toast.makeText(this, resultId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
