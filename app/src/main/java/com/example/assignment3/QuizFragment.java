package com.example.assignment3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class QuizFragment extends Fragment {

    String text;
    String color;
    TextView textView;
    ConstraintLayout constraintLayout;

    QuizFragment(String text, String color){
        this.text = text;
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.quiz_fragment,container,false);
        textView = (TextView) v.findViewById(R.id.quiz_text);
        constraintLayout = (ConstraintLayout) v.findViewById(R.id.background);

        int color_to_int = Integer.parseInt(color, 16);
        constraintLayout.setBackgroundColor(color_to_int);
        textView.setText(this.text);
        return v;
    }
}
