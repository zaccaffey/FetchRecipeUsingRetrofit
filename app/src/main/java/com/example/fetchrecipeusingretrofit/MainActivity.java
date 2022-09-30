package com.example.fetchrecipeusingretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    TextView tvId, tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvId = findViewById(R.id.tvId);
        tvTitle = findViewById(R.id.tvTitle);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Recipes>> recipesList = service.getAllRecipes();
        recipesList.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<Recipes> recipesList) {
        Toast.makeText(this, recipesList.get(0).getId().toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, recipesList.get(0).getTitle(), Toast.LENGTH_SHORT).show();
    }
}


