package com.example.foodorderingsys;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MySharedPreference {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    // Context
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "pref";
    private static final String SCORES = "scores";

    @SuppressLint("CommitPrefEdits")
    public MySharedPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveHighScoreList(String scoreString) {
        editor.putString(SCORES, scoreString);
        editor.commit();
    }

    public String getHighScoreList() {
        return pref.getString(SCORES, "");
    }

    /*public void removeFavorite(Context context, Product product) {
        ArrayList<Product> favorites = getHighScoreList(context);
        if (favorites != null) {
            favorites.remove(product);
            saveHighScoreList(product);
        }
    }*/


    public ArrayList<Product> getFavorites(Context context) {
        SharedPreferences settings;
        List<Product> favorites;

        settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        if (settings.contains(SCORES)) {
            String jsonFavorites = settings.getString(SCORES, null);
            Gson gson = new Gson();
            Product[] favoriteItems = gson.fromJson(jsonFavorites, Product[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Product>(favorites);
        } else
            return null;

        return (ArrayList<Product>) favorites;
    }

    public void removeFavorite(Context context, Product product) {
        ArrayList<Product> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(product);
            saveHighScoreList(String.valueOf(favorites));
        }
    }
}