package com.blooot.android.geoquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class QuizActivity extends ActionBarActivity {


    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEAT_RESULTS = "cheatResults";


    private enum Direction{
        NONE,
        PREV,
        NEXT
    }

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPrevButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;
    private Button mCheatButton;

    private TrueFalse[] mQuestionBank = new TrueFalse[]
            {
                new TrueFalse(R.string.question_oceans, true),
                new TrueFalse(R.string.question_mideast, false),
                new TrueFalse(R.string.question_africa, false),
                new TrueFalse(R.string.question_americas, true),
                new TrueFalse(R.string.question_asia, true)
            };

    private int mCurrentIndex = 0;

    private boolean [] mCheatedQuestions = new boolean[mQuestionBank.length];

    @TargetApi(11)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_quiz);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            ActionBar actionBar = getActionBar();
            actionBar.setSubtitle("Bodies of Water");
        }


        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);

        // Set the first question, or the saved state question.
        loadFromSavedState(savedInstanceState);

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

        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion(Direction.PREV);
            }
        });

        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion(Direction.NEXT);
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start CheatActivity
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i, 0);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null)
            return;

        if (mCheatedQuestions[mCurrentIndex] == false) {
            // Only update non-cheated questions
            mCheatedQuestions[mCurrentIndex] = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBooleanArray(KEY_CHEAT_RESULTS, mCheatedQuestions);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause()
    {
        super.onStart();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume()
    {
        super.onStart();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop()
    {
        super.onStart();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy()
    {
        super.onStart();
        Log.d(TAG, "onDestroy() called");
    }

    private void loadFromSavedState(Bundle savedState)
    {
        mCheatedQuestions = savedState != null ? savedState.getBooleanArray(KEY_CHEAT_RESULTS) : new boolean[mQuestionBank.length];
        mCurrentIndex = (savedState != null) ? savedState.getInt(KEY_INDEX, 0) : 0;
        updateQuestion(Direction.NONE);
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
                //mCurrentIndex = 0;
                break;
            default:
                mCurrentIndex = 0;
                break;
        }
        //Log.d(TAG, "Updating question text for question #"+mCurrentIndex, new Exception());

        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }


    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int toastMessageId;
        if (mCheatedQuestions[mCurrentIndex])
        {
            toastMessageId = R.string.judgment_toast;
        }else {
            if (userPressedTrue == answerIsTrue) {
                toastMessageId = R.string.correct_toast;
            } else {
                toastMessageId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT).show();
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
