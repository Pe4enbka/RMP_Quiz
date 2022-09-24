package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final Question[] mQuestionBank = new Question[]{
            new Question(R.string.question1, true),
            new Question(R.string.question2, true),
            new Question(R.string.question3, false),
            new Question(R.string.question4, false),
            new Question(R.string.question5, true),
            new Question(R.string.question6, false),
            new Question(R.string.question7, true),
            new Question(R.string.question8, true),
            new Question(R.string.question9, false),
            new Question(R.string.question10, true),
    };

    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private int mCurrentIndex = 0;
    private TextView mQuestionTextView;
    private boolean mIsCheater;

    public void nextQuestion(int number) {
        if (number != 1) {
            mCurrentIndex = mCurrentIndex + number;
        } else {
            if (mCurrentIndex != mQuestionBank.length - 1) {
                mCurrentIndex = mCurrentIndex + number;
            }
        }

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mIsCheater = false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        saveInstanceState.putBoolean(KEY_INDEX, mIsCheater);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShow(data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_INDEX, false);
        }

        mQuestionTextView = findViewById(
                R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mQuestionTextView.setOnClickListener(
                v -> nextQuestion(1)
        );

        ImageButton mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(
                v -> checkAnswer(true)
        );

        ImageButton mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(v -> checkAnswer(false));

        ImageButton mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(
                v -> nextQuestion(1)
        );

        ImageButton mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(
                v -> {
                    if (mCurrentIndex != 0) {
                        nextQuestion(-1);
                    }
                }
        );

        Button mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(
                v -> {
                    boolean answerIsTrue = mQuestionBank[mCurrentIndex].getAnswerTrue();
                    Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                    startActivityForResult(intent, REQUEST_CODE_CHEAT);
                }
        );
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].getAnswerTrue();
        int messageResId;
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}