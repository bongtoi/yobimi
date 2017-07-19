package com.example.nvquang.network;

import android.content.Context;

import com.example.nvquang.model.Question;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NVQuang on 11/07/2017.
 */

public class TaskQuestion extends TaskBase<ArrayList<Question>> {
    public TaskQuestion(Context context) {
        super(context);
    }

    @Override
    protected String getBaseUrl() {
        return "http://s3.yobimind.com/d/tmp/en-grammar.json";
    }

    @Override
    protected ArrayList<Question> genDataFromJSON(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Type type = new TypeToken<List<Question>>() {}.getType();

        return gson.fromJson(json, type);
    }
}
