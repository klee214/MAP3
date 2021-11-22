package com.example.assignment3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm;
    QuizFragment quizFragment;
    ArrayList<QuizSets> quizSets;
    ArrayList<String> color;
    AlertDialog alertDialog;
    FrameLayout frameLayout;

    ProgressBar progressBar;

    StoreManager storeManager;

    ArrayList<String> number_of_correct;
    int next = 0;
    int max_quiz = 8;
    int correct_quiz = 0;
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storeManager = ((myApp) getApplication()).getStoreManager();
        progressBar = findViewById(R.id.determinateBar);
        frameLayout = findViewById(R.id.quiz_fragment);
        fm = getSupportFragmentManager();

        if (savedInstanceState == null){
            quizSets= new ArrayList<>();
            color= new ArrayList<>();

            color.add("9752EB");
            color.add("C1AAE1");
            color.add("8D81AA");
            color.add("4A4597");
            color.add("89A557");
            color.add("A87F54");
            color.add("CD7FCC");
            color.add("685F3C");

            quizSets.add(new QuizSets(getString(R.string.q1), "False"));
            quizSets.add(new QuizSets(getString(R.string.q2), "True"));
            quizSets.add(new QuizSets(getString(R.string.q3), "True"));
            quizSets.add(new QuizSets(getString(R.string.q4), "True"));
            quizSets.add(new QuizSets(getString(R.string.q5), "False"));
            quizSets.add(new QuizSets(getString(R.string.q6), "True"));
            quizSets.add(new QuizSets(getString(R.string.q7), "True"));
            quizSets.add(new QuizSets(getString(R.string.q8), "True"));

            Collections.shuffle(quizSets);
            Collections.shuffle(color);

            int color_to_int = Integer.parseInt(color.get(0), 16);
            frameLayout.setBackgroundColor(0xff000000 + color_to_int);

            quizFragment = new QuizFragment(quizSets.get(0).quiz, color.get(0));
            fm.beginTransaction().add(R.id.quiz_fragment,quizFragment,"tag").commit();

        } else {
            quizSets = savedInstanceState.getParcelableArrayList("listofquiz");
            next = savedInstanceState.getInt("next_num");
            progress = savedInstanceState.getInt("progress_num");

            int color_to_int = Integer.parseInt(0xff000000 + color.get(next), 16);
            frameLayout.setBackgroundColor(color_to_int);

            quizFragment = new QuizFragment(quizSets.get(next).quiz, color.get(next));
            fm.beginTransaction().add(R.id.quiz_fragment,quizFragment,"tag").commit();
        }
        progressBar.setProgress(progress);
    }

    public void answerButton(View view){
        String answer = ((Button) view).getText().toString();

        if(answer.equals(quizSets.get(next).answer)){
            Toast.makeText(this,"Correct!",Toast.LENGTH_LONG).show();
            correct_quiz++;
        }else{
            Toast.makeText(this,"Incorrect!",Toast.LENGTH_LONG).show();
        }

        int progress_percent = 100/max_quiz;
        next++;

        if(next == max_quiz){
            progressBar.setProgress(100);
        }else{
            progress += progress_percent;
            progressBar.setProgress(progress);
        }

        if(next < max_quiz){
            getSupportFragmentManager().beginTransaction().remove(quizFragment).commit();

            int color_to_int = Integer.parseInt(color.get(next), 16);
            frameLayout.setBackgroundColor(0xff000000 + color_to_int);

            quizFragment = new QuizFragment(quizSets.get(next).quiz, color.get(next));
            fm.beginTransaction().add(R.id.quiz_fragment,quizFragment,"tag").commit();
        }else{
            alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Result")
                    .setMessage("Your score is " + Integer.toString(correct_quiz) + " of " + Integer.toString(max_quiz))
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            storeManager.saveCorrectNumberInternal(MainActivity.this,correct_quiz);

                            next = 0;
                            progress = 0;
                            Collections.shuffle(quizSets);
                            Collections.shuffle(color);

                            getSupportFragmentManager().beginTransaction().remove(quizFragment).commit();

                            int color_to_int = Integer.parseInt(color.get(next), 16);
                            frameLayout.setBackgroundColor(0xff000000 + color_to_int);

                            quizFragment = new QuizFragment(quizSets.get(next).quiz, color.get(next));
                            fm.beginTransaction().add(R.id.quiz_fragment,quizFragment,"tag").commit();
                            progressBar.setProgress(0);
                        }
                    })
                    .setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            next = 0;
                            progress = 0;
                            Collections.shuffle(quizSets);
                            Collections.shuffle(color);

                            getSupportFragmentManager().beginTransaction().remove(quizFragment).commit();

                            int color_to_int = Integer.parseInt(color.get(next), 16);
                            frameLayout.setBackgroundColor(0xff000000 + color_to_int);

                            quizFragment = new QuizFragment(quizSets.get(next).quiz, color.get(next));
                            fm.beginTransaction().add(R.id.quiz_fragment,quizFragment,"tag").commit();
                            progressBar.setProgress(0);
                        }
                    })
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.average:{
                number_of_correct = storeManager.getCorrectNumberInternal(MainActivity.this);
                int av_sum = 0;

                for (int i = 0; i < number_of_correct.size(); i++) {
                    av_sum  += Integer.parseInt(number_of_correct.get(i));
                }

                alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Get Average")
                        .setMessage("Your correct answers are " + Integer.toString(av_sum) + " in " + Integer.toString(number_of_correct.size()) + " attempts")
                        .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("AlertDialog", "onClick: working");
                            }
                        })
                        .setNegativeButton("EXIT Application", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();
                break;
            }
            case R.id.selectNum:{
                break;
            }
            case R.id.reset:{
                storeManager.resetTheStorage(MainActivity.this);
                break;
            }
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("listofquiz", quizSets);
        outState.putInt("next_num", next);
        outState.putInt("progress_num", progress);
    }

}