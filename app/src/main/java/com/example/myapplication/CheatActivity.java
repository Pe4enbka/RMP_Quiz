package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "CheatActivity.answer_is_true";
    private static final String EXTRA_ANSWER_SHOW = "CheatActivity.answer_shown";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShow(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOW, false);
    }

    public void setAnswerTextView() {
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
        setAnswerShowResult();
    }

    @Override
    protected void onCreate(Bundle outState) {
        super.onCreate(outState);
        setContentView(R.layout.activity_cheat);
        mAnswerTextView = findViewById(R.id.answer_text_view);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        if (outState != null) {
            mAnswerIsTrue = outState.getBoolean(EXTRA_ANSWER_IS_TRUE, false);

            setAnswerTextView();

        }
        Button mShowAnswer = findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(view -> setAnswerTextView());
    }

    private void setAnswerShowResult() {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOW, true);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_ANSWER_IS_TRUE, mAnswerIsTrue);
    }
}