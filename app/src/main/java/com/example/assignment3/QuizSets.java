package com.example.assignment3;

import android.os.Parcel;
import android.os.Parcelable;


public class QuizSets implements Parcelable {
    public String quiz;
    public String answer;

    public QuizSets(String q, String a){
        quiz = q;
        answer = a;
    }

    protected QuizSets(Parcel in) {
        quiz = in.readString();
        answer = in.readString();
    }

    public static final Creator<QuizSets> CREATOR = new Creator<QuizSets>() {
        @Override
        public QuizSets createFromParcel(Parcel in) {
            return new QuizSets(in);
        }

        @Override
        public QuizSets[] newArray(int size) {
            return new QuizSets[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quiz);
        parcel.writeString(answer);
    }
}
