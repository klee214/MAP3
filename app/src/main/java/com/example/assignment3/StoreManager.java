package com.example.assignment3;

import android.app.Activity;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class StoreManager {
    String filename= "tasks.txt";

    public void resetTheStorage(Activity activity){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = activity.openFileOutput(filename, Context.MODE_PRIVATE); // reset
            fileOutputStream.write("".getBytes());

        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    // 90
    public void saveCorrectNumberInternal(Activity activity, int no_correct){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = activity.openFileOutput(filename, Context.MODE_APPEND); // continue writting
            fileOutputStream.write((Integer.toString(no_correct)+"$").getBytes());

        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        // internal Stream

    }

    public ArrayList<String> getCorrectNumberInternal(Activity activity)  {
        FileInputStream fileInputStream = null;
        int read;
        ArrayList<String> no_correct = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        try {
            fileInputStream = activity.openFileInput(filename);
            while(( read = fileInputStream.read() )!= -1 ){
                buffer.append((char)read);
            }
            no_correct =  fromStringToList(buffer.toString());
        }catch (IOException ex){ex.printStackTrace();}
        finally {
            try {
                fileInputStream.close();
            }catch (IOException ex){ex.printStackTrace();}

        }
        return no_correct;
    }

    // fix the door - Nov 11, 2021 $ fix the window - Nov 12, 2021
    private ArrayList<String> fromStringToList(String str){ // str come from the file
        // there is a $ between tasks
        ArrayList<String> num = new ArrayList<>();

        int index = 0;
        for (int i = 0 ; i < str.toCharArray().length ; i++){
            if (str.toCharArray()[i] == '$'){
                String partial = str.substring(index,i);
                num.add(partial);
                index = i+1;
            }
        }
        return num;
    }

}
