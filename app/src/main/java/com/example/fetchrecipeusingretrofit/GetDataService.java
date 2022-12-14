package com.example.fetchrecipeusingretrofit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/recipes/findByIngredients?apiKey=dc6adff031524c349b8a56a43e63627d&ingredients=tomato,+chicken,+cucumber&number=2")
    Call<List<Recipes>> getAllRecipes();
}
