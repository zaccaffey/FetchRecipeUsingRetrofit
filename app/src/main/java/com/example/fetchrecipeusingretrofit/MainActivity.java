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

        // This creates an instance of the retrofit client
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        // Grab the list of recipes from the JSON that was fetched using the GET parameters defined in the GetDataService interface
        Call<List<Recipes>> recipesList = service.getAllRecipes();

        // Asynchronously send the request and notify callback of its response or if an error occurred talking to the server,
        // creating the request, or processing the response.
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


