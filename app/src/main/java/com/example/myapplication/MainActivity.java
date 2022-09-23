package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

    private int mCurrentIndex = 0;
    private TextView mQuestionTextView;

    public void nextQuestion(int number) {
        mCurrentIndex = (mCurrentIndex + number) % mQuestionBank.length;
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQuestionTextView = findViewById(
                R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mQuestionTextView.setOnClickListener(
                v -> nextQuestion(1)
        );

        ImageButton mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(view -> checkAnswer(true));

        ImageButton mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(view -> checkAnswer(false));

        ImageButton mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(
                v -> nextQuestion(1)
        );

        ImageButton mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(
                v -> {
                    if (mCurrentIndex == 0){
                        mCurrentIndex = mQuestionBank.length;
                    }
                    nextQuestion(-1);
                }
        );
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].getAnswerTrue();
        int messageResId;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}