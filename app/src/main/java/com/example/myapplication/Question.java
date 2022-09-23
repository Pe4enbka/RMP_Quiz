package com.example.myapplication;

public class Question {
        private final int mTextResId; //id текста вопроса
        private final boolean mAnswerTrue; //какую кнопку нажать

        public Question(int textResId, boolean answerTrue) {
            mTextResId = textResId;
            mAnswerTrue = answerTrue;
        }

        public int getTextResId(){
            return mTextResId;
        }

        public boolean getAnswerTrue(){
            return mAnswerTrue;
        }

}
