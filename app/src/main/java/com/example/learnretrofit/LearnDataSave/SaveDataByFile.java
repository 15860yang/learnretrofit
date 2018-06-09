package com.example.learnretrofit.LearnDataSave;

import android.content.Context;
import android.telecom.Call;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by 杨豪 on 2018/6/7.
 */


/**
 *
 * 什么也不管，直接写进文件就行，方便，适合大文本文件之类的存储
 *
 */
public class SaveDataByFile {

    public static boolean saveData(Context context,String fileName,String string){
        FileOutputStream out = null;
        BufferedWriter bufferedWriter = null;
        try {
            out = context.openFileOutput(fileName,Context.MODE_APPEND);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(string);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            if (bufferedWriter != null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static String readData(Context context,String fileName){
        FileInputStream in = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            in = context.openFileInput(fileName);
            br = new BufferedReader(new InputStreamReader(in));
            String s = null;
            while (( s = br.readLine()) != null){
                result.append(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }


}
